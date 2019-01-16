package io.github.mrshoenel.stateMachines.transition;

import io.github.mrshoenel.stateMachines.blackbox.BlackBoxTransition;
import io.github.mrshoenel.stateMachines.exception.IllegalTransitionException;
import io.github.mrshoenel.stateMachines.exception.NoSuchStateException;
import io.github.mrshoenel.stateMachines.exception.NoSuchTransitionException;
import io.github.mrshoenel.stateMachines.state.BaseState;
import io.github.mrshoenel.stateMachines.blackbox.BlackBoxStateMachine;
import io.github.mrshoenel.stateMachines.stateMachine.BaseStateMachine;
import io.github.mrshoenel.stateMachines.transition.dummies.DefaultBaseStateMachine;
import io.github.mrshoenel.stateMachines.transition.dummies.DefaultState;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

public class TestBaseStateMachine {

    /**
     * We are constructing a machine that has two (2) inner states, with the
     * 2nd inner state being another machine with two inner states.
     */
    @Test
    public void testBaseStateMachine() throws IllegalTransitionException {
        var start = new BaseState("start");
        var exit = new BaseState("exit");

        var m1_s1 = new BaseState("s1");

        var m2_s1 = new BaseState("m2_s1");
        var m2_s2 = new BaseState("ms_s2");

        var m1_s2 = new DefaultBaseStateMachine("m1_s2");
        m1_s2.addDefinedState(m2_s1).addDefinedState(m2_s2);

        var m1 = new DefaultBaseStateMachine("m1");
        m1.addDefinedState(m1_s1).addDefinedState(m1_s2);
        var enter = new BaseTransition(start, m1);

        // Let's do the transitions: (m1 -> m1_s1 -> m1_s2 -> m1)
        m1.setTransition(new BaseTransition(m1, m1_s1));
        m1_s1.setTransition(new BaseTransition(m1_s1, m1_s2));
        m1_s2.setTransition(new BaseTransition(m1_s2, m1));

        // nested -> nested_s1 -> nested_s2 -> nested
        m1_s2.setTransition(new BaseTransition(m1_s2, m2_s1));
        m2_s1.setTransition(new BaseTransition(m2_s1, m2_s2));
        m2_s2.setTransition(new BaseTransition(m2_s2, m1_s2));
        m1_s2.setTransition(new BaseTransition(m1_s2, exit));

        enter.transition(); // we're in m1 now, let's go to m2_s2
    }

    public void testUndefineState() {
        var m1 = new BaseStateMachine("m1");
        var s1 = new BaseState("s1");

        m1.defineState(s1);
        assertTrue(m1.getDefinedStates().contains(s1));
        assertSame(m1, s1.getBelongsToMachine());

        assertDoesNotThrow(() -> {
            m1.undefineState(s1);
        });
        assertFalse(m1.getDefinedStates().contains(s1));
        assertSame(null, s1.getBelongsToMachine());

        assertThrows(NoSuchStateException.class, () -> {
            m1.undefineState(new BaseState("foo"));
        });

        var d2 = new DefaultState(); // Does not support belongs-to
        m1.defineState(d2);
        assertTrue(m1.getDefinedStates().contains(d2));
        assertDoesNotThrow(() -> {
            m1.undefineState(d2);
        });
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testBlackBoxStateMachine() throws IllegalTransitionException, NoSuchStateException, NoSuchTransitionException {
        var m1 = new BaseStateMachine("m1");
        var s1 = new BaseState("s1");
        var s2 = new BaseState("s2");

        m1.setTransition(new BaseTransition("tr1", m1, s1));
        s1.setTransition(new BaseTransition("tr2", s1, s2));
        s2.setTransition(new BaseTransition("tr3", s2, m1) {
            @Override
            protected void initialize() {
                super.initialize();
                this.setArgument(new BaseTransitionArgument<>(Integer.class, "int"));
            }
        });

        m1.defineState(s1).defineState(s2);
        assertThrows(IllegalArgumentException.class, () -> {
            m1.defineState(s1);
        });
        assertThrows(NoSuchStateException.class, () -> {
            m1.setCurrentState(new BaseState("foo"));
        });
        assertDoesNotThrow(() -> {
            var m2 = new BaseStateMachine("foo");
            m2.defineState(new DefaultState()); // does not inherit from BaseState
        });

        //////////////////// BlackBox testing below ////////////////////////
        var bb = new BlackBoxStateMachine<>(m1);

        assertSame(m1.getName(), bb.getName());
        var tr = bb.getTransitions();
        assertSame(1, tr.size());
        assertEquals("tr1", tr.get("tr1").getName());

        // go into s1
        bb.transition(bb.getTransitions().get("tr1"));
        tr = bb.getTransitions();
        assertSame(1, tr.size());
        assertEquals("tr2", tr.get("tr2").getName());

        // go into s2
        bb.transition(bb.getTransitions().get("tr2"));
        tr = bb.getTransitions();
        assertSame(1, tr.size());
        assertEquals("tr3", tr.get("tr3").getName());

        // go into m1 again by calling tr3; note that tr3 has argument "int"
        var tr3 = bb.getTransitions().get("tr3");
        assertSame(1, tr3.getTransitionArguments().size());
        assertTrue(tr3.isAllowed());
        assertThrows(IllegalTransitionException.class, () -> {
            bb.transition(tr3);
        });

        ((TransitionArgument<Integer>)tr3.getTransitionArguments().get("int")).setValue(42);
        assertDoesNotThrow(() -> {
            bb.transition(tr3);
        });

        assertSame(m1, m1.getCurrentState());
    }

    @Test
    public void testBlackBoxWithOrphanagedStates() throws NoSuchStateException {
        var m1 = new BaseStateMachine("m1");
        var s1 = new BaseState("s1");

        m1.setTransition(new BaseTransition("tr1", m1, s1)); // note how we're NOT adding s1 to m1

        var bb = new BlackBoxStateMachine<>(m1);
        assertThrows(IllegalTransitionException.class, () -> {
            bb.transition(bb.getTransitions().get("tr1"));
        });
    }

    @Test
    public void testBlackBoxWithNestedMachines() throws NoSuchStateException, IllegalTransitionException, NoSuchTransitionException {
        var m1 = new BaseStateMachine("m1");
        var m2 = new BaseStateMachine("m2");
        var m3 = new BaseStateMachine("m3");

        m1.setTransition(new BaseTransition("m1-m2", m1, m2));
        m2.setTransition(new BaseTransition("m2-m3", m2, m3));

        m1.defineState(m2);
        m2.defineState(m3);

        var bb = new BlackBoxStateMachine<>(m1);
        bb.transition("m1-m2");
        bb.transition("m2-m3");

        assertThrows(NoSuchTransitionException.class, () -> {
            bb.transition("bla");
        });
    }

    @Test
    public void testBlackBoxTransition() throws InstantiationException, IllegalAccessException {
        assertThrows(IllegalArgumentException.class, () -> {
            new BlackBoxTransition(new BaseTransition(
                    new BaseState("s1"),
                    new DefaultState()
            ));
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new BlackBoxTransition(new BaseTransition(
                    new DefaultState(),
                    new BaseState("s1")
            ));
        });

        assertDoesNotThrow(() -> {
            new BlackBoxTransition(new BaseTransition(
                    new BaseState("s1"),
                    new BaseState("s2")
            ));
        });
    }
}

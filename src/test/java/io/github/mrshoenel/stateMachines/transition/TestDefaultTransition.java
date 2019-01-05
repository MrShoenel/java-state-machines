package io.github.mrshoenel.stateMachines.transition;

import io.github.mrshoenel.stateMachines.exception.IllegalTransitionException;
import io.github.mrshoenel.stateMachines.state.BaseState;
import io.github.mrshoenel.stateMachines.transition.dummies.DefaultTransition;
import io.github.mrshoenel.stateMachines.transition.dummies.DefaultTransition2;
import io.github.mrshoenel.stateMachines.transition.dummies.TransitionAllowNull;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TestDefaultTransition {

    @Test
    public void testDefaultTransition() {
        var dt = new DefaultTransition(new BaseState("a"), new BaseState("b"));

        assertTrue(dt.isAllowed()); // the default..

        Map<String, TransitionArgument<?>> map = dt.getTransitionArguments();
        // it's readonly..
        assertThrows(UnsupportedOperationException.class, () -> {
           map.put("asd", null);
        });
        assertThrows(UnsupportedOperationException.class, () -> {
            map.remove("asd");
        });
    }

    @Test
    public void testDefaultTransition2() {
        var dt = new DefaultTransition2(new BaseState("a"), new BaseState("b"));

        assertDoesNotThrow(() -> {
            dt.transition();
        });

        dt.setIsAllowed(false);
        assertThrows(IllegalTransitionException.class, () -> {
           dt.transition();
        });

        dt.setIsAllowed(true);
        // let's fiddle with the args..

        var arg = new BaseTransitionArgument<>(String.class, "arg");
        dt.setArgument(arg);
        assertThrows(IllegalTransitionException.class, () -> {
            dt.transition();
        });
        arg.setValue("asd");
        assertDoesNotThrow(() -> {
            dt.transition();
        });
    }

    @Test
    public void testTransitionAllowNull() {
        var tr = new TransitionAllowNull();

        assertDoesNotThrow(tr::transition);

        var s1 = new BaseState("s1");
        var s2 = new BaseState("s2");

        tr.setFromState(s1);
        assertDoesNotThrow(tr::transition);

        tr.setFromState(null);
        tr.setToState(s2);
        assertDoesNotThrow(tr::transition);

        tr.setFromState(s1);
        assertDoesNotThrow(tr::transition);
    }
}

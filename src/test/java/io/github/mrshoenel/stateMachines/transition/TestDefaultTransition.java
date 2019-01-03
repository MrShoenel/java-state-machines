package io.github.mrshoenel.stateMachines.transition;

import io.github.mrshoenel.stateMachines.exception.IllegalTransitionException;
import io.github.mrshoenel.stateMachines.state.BaseState;
import io.github.mrshoenel.stateMachines.transition.dummies.DefaultTransition;
import io.github.mrshoenel.stateMachines.transition.dummies.DefaultTransition2;
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
}

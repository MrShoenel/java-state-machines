package io.github.mrshoenel.stateMachines.transition;

import io.github.mrshoenel.stateMachines.exception.NoSuchTransitionException;
import io.github.mrshoenel.stateMachines.state.BaseState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestBaseState {
    @Test
    public void testBaseState() {
        var bs1 = new BaseState("bs1");
        var bs2 = new BaseState("bs2");

        var tr1 = new BaseTransition("tr1", bs1, bs2);

        assertThrows(NoSuchTransitionException.class, () -> {
           bs1.unsetTransition(tr1);
        });

        assertFalse(bs1.hasTransition(tr1));
        assertFalse(bs1.hasTransitions());
        assertTrue(bs1.isFinalState());
        assertTrue(bs2.isFinalState());

        bs1.setTransition(tr1);
        assertTrue(bs1.hasTransitions());
        assertTrue(bs1.hasTransition(tr1.getName()));
        assertFalse(bs1.isFinalState());

        assertDoesNotThrow(() -> {
           bs1.unsetTransition(tr1);
        });
    }
}

package io.github.mrshoenel.stateMachines.transition;

import io.github.mrshoenel.stateMachines.state.BaseState;
import io.github.mrshoenel.stateMachines.transition.dummies.DefaultState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestDefaultState {

    @Test
    public void testDefaultState() {
        var ds = new DefaultState();

        assertFalse(ds.isInitialState());
        assertFalse(ds.isFinalState());

        var bs = new BaseState("");
        assertFalse(bs.isInitialState());
        assertTrue(bs.isFinalState()); // NOTE THIS IS DIFFERENT!
    }
}

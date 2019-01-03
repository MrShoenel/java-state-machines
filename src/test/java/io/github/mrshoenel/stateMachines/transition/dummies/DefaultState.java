package io.github.mrshoenel.stateMachines.transition.dummies;

import io.github.mrshoenel.stateMachines.exception.NoSuchTransitionException;
import io.github.mrshoenel.stateMachines.state.State;
import io.github.mrshoenel.stateMachines.transition.Transition;

import java.util.Map;

public class DefaultState implements State {
    @Override
    public State setTransition(Transition transition) {
        return null;
    }

    @Override
    public State unsetTransition(Transition transition) throws NoSuchTransitionException {
        return null;
    }

    @Override
    public Map<String, Transition> getAllDefinedTransitions() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }
}

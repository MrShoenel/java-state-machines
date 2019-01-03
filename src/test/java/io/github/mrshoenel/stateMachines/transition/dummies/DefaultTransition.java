package io.github.mrshoenel.stateMachines.transition.dummies;

import io.github.mrshoenel.stateMachines.state.State;
import io.github.mrshoenel.stateMachines.transition.Transition;

public class DefaultTransition implements Transition {
    private final State from, to;

    public DefaultTransition(State from, State to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public State getFromState() {
        return this.from;
    }

    @Override
    public State getToState() {
        return this.to;
    }

    @Override
    public String getName() {
        return null;
    }
}

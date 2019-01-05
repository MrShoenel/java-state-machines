package io.github.mrshoenel.stateMachines.transition.dummies;

import io.github.mrshoenel.stateMachines.state.State;
import io.github.mrshoenel.stateMachines.transition.BaseTransition;
import io.github.mrshoenel.stateMachines.transition.Transition;
import org.springframework.lang.Nullable;

public class TransitionAllowNull implements Transition {
    private State fromState, toState;

    public TransitionAllowNull() {
        this(null, null);
    }

    public TransitionAllowNull(@Nullable State fromState, @Nullable State toState) {
        this.fromState = fromState;
        this.toState = toState;
    }

    @Override
    public State getFromState() {
        return fromState;
    }

    public TransitionAllowNull setFromState(State fromState) {
        this.fromState = fromState;
        return this;
    }

    @Override
    public State getToState() {
        return toState;
    }

    public TransitionAllowNull setToState(State toState) {
        this.toState = toState;
        return this;
    }

    @Override
    public String getName() {
        return TransitionAllowNull.class.getSimpleName();
    }
}

package io.github.mrshoenel.stateMachines.transition.dummies;

import io.github.mrshoenel.stateMachines.state.State;
import io.github.mrshoenel.stateMachines.transition.BaseTransition;

public class DefaultTransition2 extends BaseTransition {
    private boolean allowed;

    public DefaultTransition2(State from, State to) {
        super(from, to);
        this.allowed = true;
    }

    public void setIsAllowed(boolean allowed) {
        this.allowed = allowed;
    }

    @Override
    public boolean isAllowed() {
        return this.allowed;
    }
}

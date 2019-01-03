package io.github.mrshoenel.stateMachines.transition.simplePoker;

import io.github.mrshoenel.stateMachines.state.BaseState;

public class BetRoundFinishedState extends BaseState {
    public BetRoundFinishedState() {
        super(BetRoundFinishedState.class.getSimpleName());
    }

    @Override
    public boolean isFinalState() {
        return true;
    }
}
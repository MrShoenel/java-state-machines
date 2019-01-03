package io.github.mrshoenel.stateMachines.transition.simplePoker;

import io.github.mrshoenel.stateMachines.state.BaseState;

public class SimplePokerGameInitialState extends BaseState {
    public SimplePokerGameInitialState() {
        super(SimplePokerGameInitialState.class.getSimpleName());
    }

    @Override
    public boolean isInitialState() {
        return true;
    }
}
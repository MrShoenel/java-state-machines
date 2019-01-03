package io.github.mrshoenel.stateMachines.transition.simplePoker.transition;

import io.github.mrshoenel.stateMachines.exception.IllegalTransitionException;
import io.github.mrshoenel.stateMachines.state.State;
import io.github.mrshoenel.stateMachines.transition.simplePoker.SimplePokerGame;

import java.util.function.Consumer;

public class BetRoundTransition extends LoggingTransition {
    private final SimplePokerGame simplePokerGame;

    public BetRoundTransition(Consumer<LoggingTransition> transitionConsumer, State fromState, State toState, SimplePokerGame simplePokerGame) {
        super(transitionConsumer, fromState, toState);
        this.simplePokerGame = simplePokerGame;
    }

    @Override
    public boolean isAllowed() {
        return !this.simplePokerGame.hasBetRoundStarted();
    }

    @Override
    public void transition() throws IllegalTransitionException {
        super.transition();
        this.simplePokerGame.setHasBetRoundStarted(true);
    }
}

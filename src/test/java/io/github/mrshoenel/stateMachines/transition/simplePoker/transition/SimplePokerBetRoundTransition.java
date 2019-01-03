package io.github.mrshoenel.stateMachines.transition.simplePoker.transition;

import io.github.mrshoenel.stateMachines.state.State;
import io.github.mrshoenel.stateMachines.transition.simplePoker.SimplePokerBetRound;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class SimplePokerBetRoundTransition extends LoggingTransition {
    private final Supplier<Boolean> allower;

    public SimplePokerBetRoundTransition(Consumer<LoggingTransition> transitionConsumer, final String name, State fromState, State toState, Supplier<Boolean> allower) {
        super(transitionConsumer, name, fromState, toState);
        this.allower = allower;
    }

    public SimplePokerBetRoundTransition(Consumer<LoggingTransition> transitionConsumer, State fromState, State toState, Supplier<Boolean> allower) {
        super(transitionConsumer, fromState, toState);
        this.allower = allower;
    }

    @Override
    public boolean isAllowed() {
        return this.allower.get();
    }
}

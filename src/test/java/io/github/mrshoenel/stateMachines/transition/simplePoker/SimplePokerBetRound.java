/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package io.github.mrshoenel.stateMachines.transition.simplePoker;

import io.github.mrshoenel.stateMachines.stateMachine.BaseStateMachine;
import io.github.mrshoenel.stateMachines.transition.Transition;
import io.github.mrshoenel.stateMachines.transition.simplePoker.transition.LoggingTransition;
import io.github.mrshoenel.stateMachines.transition.simplePoker.transition.SimplePokerBetRoundTransition;

import java.util.LinkedHashSet;
import java.util.function.Consumer;

/**
 * The simple kind of Bet-round for the above game. Simplifications:
 * - all players from the simple game participate initially
 * - the players can bet, although it is not possible to set an amount
 */
public class SimplePokerBetRound extends BaseStateMachine {

    private boolean isFinished = false;

    private final SimplePokerGame simplePokerGame;

    private final LinkedHashSet<SimplePlayer> players;

    public SimplePokerBetRound(final SimplePokerGame simplePokerGame) {
        super(SimplePokerBetRound.class.getSimpleName());
        this.simplePokerGame = simplePokerGame;
        this.players = new LinkedHashSet<>();
        this.setupStates();
    }

    public LinkedHashSet<SimplePlayer> getPlayers() {
        return players;
    }

    @Override
    public void enter(Transition usingTransition) {
        // Make a copy of the HashSet so that players can fold.
        this.players.addAll(this.simplePokerGame.players);
        this.isFinished = false;
        this.currentState = this;
    }

    protected void setupStates() {
        Consumer<LoggingTransition> logger = lt -> {
            System.out.println(lt);
            this.currentState = lt.getToState();
        };

        var finished = new BetRoundFinishedState();
        var validate = new BetRoundValidateState();
        var iterateP = new BetRoundIteratePlayers(this);

        this.definedStates.add(finished);
        this.definedStates.add(validate);
        this.definedStates.add(iterateP);

        this.setTransition(new SimplePokerBetRoundTransition(logger, this, iterateP, () -> !iterateP.hasIterationStarted()));

        finished.setTransition(new SimplePokerBetRoundTransition(logger, finished, this.simplePokerGame, () -> isFinished));

        validate.setTransition(new SimplePokerBetRoundTransition(logger, validate, finished, () -> iterateP.isIterationFinished() || players.size() < 2));

        validate.setTransition(new SimplePokerBetRoundTransition(logger, validate, iterateP, () -> !iterateP.isIterationFinished() && players.size() > 1));

        iterateP.setTransition(new SimplePokerBetRoundTransition(logger, "check", iterateP, validate, () -> iterateP.getLastAction() == Action.NONE || iterateP.getLastAction() == Action.Check));

        iterateP.setTransition(new SimplePokerBetRoundTransition(logger, "bet", iterateP, validate, () -> iterateP.getLastAction() == Action.NONE || iterateP.getLastAction() == Action.Check));

        iterateP.setTransition(new SimplePokerBetRoundTransition(logger, "raise", iterateP, validate, () -> iterateP.getLastAction() == Action.Bet || iterateP.getLastAction() == Action.Raise || iterateP.getLastAction() == Action.Call));

        iterateP.setTransition(new SimplePokerBetRoundTransition(logger, "call", iterateP, validate, () -> iterateP.getLastAction() == Action.Bet || iterateP.getLastAction() == Action.Raise || iterateP.getLastAction() == Action.Call));

        iterateP.setTransition(new SimplePokerBetRoundTransition(logger, "fold", iterateP, validate, () -> true));
    }
}

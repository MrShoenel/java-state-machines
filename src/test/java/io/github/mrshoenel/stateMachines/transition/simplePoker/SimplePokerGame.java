/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package io.github.mrshoenel.stateMachines.transition.simplePoker;


import io.github.mrshoenel.stateMachines.exception.IllegalTransitionException;
import io.github.mrshoenel.stateMachines.stateMachine.BaseStateMachine;
import io.github.mrshoenel.stateMachines.transition.Transition;
import io.github.mrshoenel.stateMachines.transition.simplePoker.transition.BetRoundTransition;
import io.github.mrshoenel.stateMachines.transition.simplePoker.transition.GameInitTransition;
import io.github.mrshoenel.stateMachines.transition.simplePoker.transition.LoggingTransition;

import java.util.LinkedHashSet;
import java.util.function.Consumer;

/**
 * - variable amount of players
 * - no Ante, starts immediately with Bet-round
 *  - Thus all players are in that bet-round
 * - Raise in bet-round is allowed, up to x times (configurable)
 *  - Actions are check, bet, raise, call and fold in bet-round
 *  - The game ends, when the bet-round is over and the game transitions
 *   into the show-cards state or only one player is left
 */
public class SimplePokerGame extends BaseStateMachine {

    private boolean hasInited = false;
    private boolean hasBetRoundStarted = false;


    public final LinkedHashSet<SimplePlayer> players;

    public SimplePokerGame() {
        super(SimplePokerGame.class.getSimpleName());
        this.players = new LinkedHashSet<>();
        this.setupStates();
    }

    public boolean getHasInited() {
        return hasInited;
    }

    public void setHasInited(boolean hasInited) {
        this.hasInited = hasInited;
    }

    public void setHasBetRoundStarted(boolean hasBetRoundStarted) {
        this.hasBetRoundStarted = hasBetRoundStarted;
    }

    public boolean hasBetRoundStarted() {
        return hasBetRoundStarted;
    }

    @Override
    public void enter(Transition usingTransition) {
        // If entered from end of bet-round..
        if (usingTransition.getFromState() instanceof BetRoundFinishedState) {
            this.finishRound((SimplePokerBetRound) usingTransition.getFromState());
        }
    }

    private void finishRound(SimplePokerBetRound simplePokerBetRound) {
        System.out.println("Round finished! " + simplePokerBetRound);
    }

    protected void setupStates() {
        Consumer<LoggingTransition> logger = lt -> {
            System.out.println(lt);
            this.currentState = lt.getToState();
        };

        var init = new SimplePokerGameInitialState();
        var betRound = new SimplePokerBetRound(this);

        this.definedStates.add(init);
        this.definedStates.add(betRound);

        this.setTransition(new GameInitTransition(logger, this, init, this));
        init.setTransition(new BetRoundTransition(logger, init, betRound, this));

        this.currentState = init;
    }
}

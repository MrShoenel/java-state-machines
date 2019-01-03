package io.github.mrshoenel.stateMachines.transition.simplePoker;

import io.github.mrshoenel.stateMachines.state.BaseState;
import io.github.mrshoenel.stateMachines.transition.Transition;

public class BetRoundIteratePlayers extends BaseState {
    private Action lastAction;

    private boolean hasIterationStarted, isIterationFinished;

    private SimplePlayer currentPlayer;

    private final SimplePokerBetRound simplePokerBetRound;

    public BetRoundIteratePlayers(final SimplePokerBetRound simplePokerBetRound) {
        super(BetRoundIteratePlayers.class.getSimpleName());

        this.simplePokerBetRound = simplePokerBetRound;
        this.lastAction = Action.NONE;
        this.isIterationFinished = false;
        this.hasIterationStarted = false;
    }

    // TODO: this is never properly updated..
    public boolean isIterationFinished() {
        return isIterationFinished;
    }

    public boolean hasIterationStarted() {
        return hasIterationStarted;
    }

    public Action getLastAction() {
        return lastAction;
    }

    @Override
    public void enter(Transition usingTransition) {
        this.currentPlayer = this.simplePokerBetRound.getPlayers().iterator().next();

        if (usingTransition.getFromState() instanceof SimplePokerBetRound) {
            this.hasIterationStarted = true;
        }
    }

    @Override
    public void leave(Transition usingTransition) {
        String trType = usingTransition.getName(); // TODO: find better solution

        if (trType.equals("check")) {
            this.lastAction = Action.Check;
        } else if (trType.equals("bet")) {
            this.lastAction = Action.Bet;
        } else if (trType.equals("call")) {
            this.lastAction = Action.Call;
        } else if (trType.equals("raise")) {
            this.lastAction = Action.Raise;
        } else if (trType.equals("fold")) {
            // Remove current player
            this.simplePokerBetRound.getPlayers().remove(this.currentPlayer);
        }
    }
}
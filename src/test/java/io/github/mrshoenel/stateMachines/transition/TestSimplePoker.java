package io.github.mrshoenel.stateMachines.transition;

import io.github.mrshoenel.stateMachines.exception.IllegalTransitionException;
import io.github.mrshoenel.stateMachines.state.BaseState;
import io.github.mrshoenel.stateMachines.stateMachine.StateMachine;
import io.github.mrshoenel.stateMachines.transition.simplePoker.*;
import org.junit.jupiter.api.Test;

public class TestSimplePoker {

    @Test
    public void testSimpleGame() throws IllegalTransitionException {
        var game = new SimplePokerGame();
        game.players.add(new SimplePlayer("Phil"));
        game.players.add(new SimplePlayer("Chris"));
        game.players.add(new SimplePlayer("Daniel"));

        BaseState deep = game;
        inspectState(deep);

        deep.getTransitions().get("trans_to_[SimplePokerGameInitialState: SimplePokerGameInitialState]").transition();
        deep = (BaseState) game.getCurrentStateDeep();
        inspectState(deep);

        deep.getTransitions().get("trans_to_[SimplePokerBetRound: SimplePokerBetRound]").transition();
        deep = (BaseState) game.getCurrentStateDeep();
        inspectState(deep);

        deep.getTransitions().get("trans_to_[BetRoundIteratePlayers: BetRoundIteratePlayers]").transition();
        deep = (BaseState) game.getCurrentStateDeep();
        inspectState(deep);

        // Now we have 3 players and will do:
        // check - bet - raise
        // raise - call - fold
        // which leaves 2 players (note that the order doesn't work)
        var ip4 = (BetRoundIteratePlayers) deep;
        ip4.getAllDefinedTransitions().get("check").transition();
        ip4.getAllDefinedTransitions().get("bet").transition();
        ip4.getAllDefinedTransitions().get("raise").transition();

        ip4.getAllDefinedTransitions().get("raise").transition();
        ip4.getAllDefinedTransitions().get("call").transition();
        ip4.getAllDefinedTransitions().get("fold").transition();

        deep = (BaseState) game.getCurrentStateDeep();
        inspectState(deep);

        // Now the goal of these tests is to ascertain no Exception is thrown.
    }


    private static void inspectState(BaseState state) {
        var isMachine = state instanceof StateMachine;
        C.p("\n--------------------------------------------");
        C.p("Inspecting state: " + state.getName());
        C.p(" - Is StateMachine: " + (isMachine ? "YES" : "no"));

        if (isMachine) {
            StateMachine machine = (StateMachine) state;
            C.p(" - defined States: " + machine.getDefinedStates());
            C.p(" - currentState: " + machine.getCurrentState());
            C.p(" - currentState (Deep): " + machine.getCurrentStateDeep());
        }

        C.p("");
        C.p(" - Transitions:");
        for (var tr : state.getAllDefinedTransitions().values()) {
            C.p("  `- " + tr.toString());
            C.p("    `- toState: " + tr.getToState());
            C.p("    `- isAllowed: " + (tr.isAllowed() ? "YES" : "NO"));
        }
    }
}

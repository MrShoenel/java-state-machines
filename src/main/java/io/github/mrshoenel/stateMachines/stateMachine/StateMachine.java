package io.github.mrshoenel.stateMachines.stateMachine;

import io.github.mrshoenel.stateMachines.state.State;
import java.util.Set;


/**
 * The StateMachine is an extension of a {@link State}. This library supports the
 * idea of nested state-machines, so that a state which is also a state-machine,
 * can have a set of defined sub-states/state-machines. Therefore it is also aware
 * of the currently selected sub-state/-machine.
 *
 * @author Sebastian Hönel development@hoenel.net
 */
public interface StateMachine extends State {
    /**
     * Returns a set of direct sub-states/-machines, that this state-machine defines.
     *
     * @return Set of states/-machines
     */
    Set<State> getDefinedStates();

    /**
     * Returns the currently selected/active sub-state/-machine.
     *
     * @return State
     */
    State getCurrentState();

    /**
     * Default implementation for obtaining this state-machine's most deeply nested
     * active sub-state/-machine. Uses {@link StateMachine#getCurrentState()} to
     * traverse the hierarchy of currently active states as deep as possible.
     *
     * @return
     */
    default State getCurrentStateDeep() {
        State current = this.getCurrentState();
        if (current != this && current instanceof StateMachine) {
            return ((StateMachine) current).getCurrentStateDeep();
        }
        return current;
    }
}

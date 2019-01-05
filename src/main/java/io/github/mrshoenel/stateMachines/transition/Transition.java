package io.github.mrshoenel.stateMachines.transition;


import io.github.mrshoenel.stateMachines.StateMachineArtifact;
import io.github.mrshoenel.stateMachines.exception.IllegalTransitionException;
import io.github.mrshoenel.stateMachines.state.State;

import java.util.Collections;
import java.util.Map;


/**
 * A transition describes the action of going from one {@link State} to another
 * one. Transitions are directed and always have a 'from'- and 'to'-state. Also,
 * a transition can have zero to many arguments. This makes transitions very
 * powerful, especially in conjunction with the implementation of the method
 * {@link Transition#isAllowed()}.
 *
 * @author Sebastian Hönel development@hoenel.net
 */
public interface Transition extends StateMachineArtifact {
    /**
     * The state this transition is leaving.
     *
     * @return State
     */
    State getFromState();

    /**
     * The state this transition is entering.
     *
     * @return State
     */
    State getToState();

    /**
     * Should return a value indicating whether this transition is currently
     * allowed. Attempting to execute a disallowed transition will result in
     * throwing of a {@link IllegalTransitionException}.
     * The default implementation will always return true (i.e. the transition
     * is always allowed).
     *
     * @return boolean
     */
    default boolean isAllowed() {
        return true;
    }

    /**
     * Default implementation of executing a transition. Will notify the 'from'-
     * state that it is being left (by calling {@link State#leave(Transition)})
     * and the 'to'-state that it is being entered (by calling {@link State#enter(Transition)}.
     * While calling these, this implementation will pass itself in.
     *
     * @throws IllegalTransitionException if this transition is currently dis-
     * allowed of if one or more of its arguments have no value set.
     */
    default void transition() throws IllegalTransitionException {
        if (!this.isAllowed()) {
            throw new IllegalTransitionException(
                "This transition is currently disallowed.");
        }

        for (TransitionArgument<?> arg : this.getTransitionArguments().values()) {
            if (!arg.hasValue()) {
                throw new IllegalTransitionException(
                    "Argument '" + arg.getName() + "' has not been set.");
            }
        }

        State from = this.getFromState();
        if (from instanceof State) {
            from.leave(this);
        }

        State to = this.getToState();
        if (to instanceof State) {
            to.enter(this);
        }
    }

    /**
     * Obtains a map with all defined arguments that are required for this
     * Transition.
     *
     * @return Map of arguments
     */
    default Map<String, TransitionArgument<?>> getTransitionArguments() {
        return Collections.unmodifiableMap(Collections.emptyMap());
    }
}

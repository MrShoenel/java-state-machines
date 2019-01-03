package io.github.mrshoenel.stateMachines.state;


import io.github.mrshoenel.stateMachines.StateMachineArtifact;
import io.github.mrshoenel.stateMachines.exception.NoSuchTransitionException;
import io.github.mrshoenel.stateMachines.transition.Transition;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * The interface that describes a state in a state-machine. A state can
 * be an initial or final state, it can have arbitrary many transitions
 * to other states (including itself) and provides pointcuts for when it
 * is entered or left by a transition.
 *
 * @author Sebastian Hönel development@hoenel.net
 */
public interface State extends StateMachineArtifact {
    /**
     * Called by {@link Transition} when the state is entered. The
     * default implementation does nothing.
     *
     * @param usingTransition The {@link Transition} will pass itself in.
     */
    default void enter(Transition usingTransition) { };

    /**
     * Called by {@link Transition} when the state is left. The default
     * implementation does nothing.
     *
     * @param usingTransition The {@link Transition} will pass itself in.
     */
    default void leave(Transition usingTransition) { };

    /**
     * The default returns false. Override this method to return a value
     * indicating whether this state is an initial state or not.
     *
     * @return java.lang.Boolean True, if this is an initial state.
     */
    default boolean isInitialState() {
        return false;
    }

    /**
     * The default returns false. Override this method to return a value
     * indicating whether this state is a final state or not.
     *
     * @return java.lang.Boolean True, if this is a final state.
     */
    default boolean isFinalState() {
        return false;
    }

    /**
     * Sets a transition on this state towards another state. Note that
     * transitions are always only set on the 'from'-state, as they are
     * directed. Setting means that any previous transition with the same
     * name is being replaced.
     *
     * @param transition The Transition to use.
     * @return State this for chaining
     */
    State setTransition(Transition transition);

    /**
     * Unsets a previously set transition, thus entirely removing the
     * possibility to go from this state to the transition's target-state
     * using the transition being removed.
     *
     * @param transition The transition to remove.
     * @return State this for chaining
     * @throws NoSuchTransitionException
     */
    State unsetTransition(Transition transition) throws NoSuchTransitionException;

    /**
     * A map with all currently defined transitions.
     *
     * @return Map with all defined transitions
     */
    Map<String, Transition> getAllDefinedTransitions();

    /**
     * Returns a map with all currently defined and allowed transitions. An allowed
     * transition is one that is currently applicable {@link Transition#isAllowed()}.
     *
     * @return Map with all allowed transitions (i.e. a subset of {@link State#getAllDefinedTransitions()}
     */
    default Map<String, Transition> getTransitions() {
        return Collections.unmodifiableMap(
            this.getAllDefinedTransitions().values()
                .stream().filter(Transition::isAllowed)
                .collect(Collectors.toMap(Transition::getName, kv -> kv)));
    }
}

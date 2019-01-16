package io.github.mrshoenel.stateMachines.blackbox;

import io.github.mrshoenel.stateMachines.StateMachineArtifact;
import io.github.mrshoenel.stateMachines.state.BaseState;
import io.github.mrshoenel.stateMachines.transition.Transition;
import io.github.mrshoenel.stateMachines.transition.TransitionArgument;
import org.springframework.lang.NonNull;

import java.util.Map;
import java.util.Objects;


/**
 * A special kind of transition only used by the {@link BlackBoxStateMachine}.
 * This transition hides the from- and to-states and only exposes the required
 * arguments. Also, it does not have a transition-method and can thus only be
 * activated by the {@link BlackBoxStateMachine}.
 *
 * @author Sebastian Hönel development@hoenel.net
 */
public class BlackBoxTransition implements StateMachineArtifact {

    private final Transition transition;

    /**
     * Constructs a new {@link BlackBoxTransition} based on an existing transition.
     *
     * @param transition A non-null {@link Transition} that would be used to go
     *                   from one state to another. This transition is then en-
     *                   capsulated.
     */
    public BlackBoxTransition(@NonNull final Transition transition) {
        Objects.requireNonNull(transition);

        if (!(transition.getFromState() instanceof BaseState)
            || !(transition.getToState() instanceof BaseState)) {
            throw new IllegalArgumentException("From- and/or To-state is not of type BaseState.");
        }

        this.transition = transition;
    }

    /**
     * This is only called by the {@link BlackBoxStateMachine#transition(String)}.
     * This method is only accessible within the package and to sub-classes.
     *
     * @return {@link Transition} the encapsulated and wrapped transition.
     */
    protected Transition getTransition() {
        return this.transition;
    }

    /**
     * {@inheritDoc}
     * @return {@link String}
     */
    @Override
    public String getName() {
        return this.transition.getName();
    }

    /**
     * Obtains a map of required arguments for this transition.
     *
     * @return {@link Map} with named parameters
     */
    public Map<String, TransitionArgument<?>> getTransitionArguments() {
        return this.transition.getTransitionArguments();
    }

    /**
     * Returns the wrapped transition's value for {@link Transition#isAllowed()}.
     *
     * @return {@link Boolean}
     */
    public boolean isAllowed() {
        return this.transition.isAllowed();
    }

    /**
     * Returns the hash-code of the wrapped {@link Transition} XOR'd with 31.
     *
     * @return {@link Integer}
     */
    @Override
    public int hashCode() {
        return this.transition.hashCode() ^ 31;
    }

    /**
     * Returns true, iff the other object is also a {@link BlackBoxTransition}
     * and iff this transition equals its transition (using {@link Transition#equals(Object)}.
     *
     * @param obj The other object to check for equality
     * @return {@link Boolean} true, if the other object is a {@link BlackBoxTransition}
     * and if its wrapped transition equals this' wrapped transition.
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof BlackBoxTransition
            && this.transition.equals(((BlackBoxTransition) obj).transition);
    }
}

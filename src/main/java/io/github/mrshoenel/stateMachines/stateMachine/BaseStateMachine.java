package io.github.mrshoenel.stateMachines.stateMachine;

import io.github.mrshoenel.stateMachines.exception.NoSuchStateException;
import io.github.mrshoenel.stateMachines.state.BaseState;
import io.github.mrshoenel.stateMachines.state.State;
import io.github.mrshoenel.stateMachines.transition.Transition;
import org.springframework.lang.NonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


/**
 * A basic implementation of a {@link StateMachine} that comes with a standard set
 * of features, such as defined sub-states and being aware of the currently active
 * sub-state. This implementation extends the {@link BaseState}, since a state-
 * machine is also a state, hence a state with sub-states.
 *
 * @author Sebastian Hönel development@hoenel.net
 */
public class BaseStateMachine extends BaseState implements StateMachine {
    protected final Set<State> definedStates;

    private final Set<State> definedStatesUnmod;

    protected State currentState;

    /**
     * Creates a new {@link BaseStateMachine} with the given name.
     *
     * @param name The name of the Machine. Like for states, this name should
     *             uniquely identify this machine, if it is part of a larger
     *             machine.
     */
    public BaseStateMachine(@NonNull final String name) {
        super(name);
        this.definedStates = new HashSet<>();
        this.definedStatesUnmod = Collections.unmodifiableSet(this.definedStates);
        this.currentState = this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public State getCurrentState() {
        return this.currentState;
    }

    /**
     * Used to set the current state this machine is pointing to. Note that initially, right
     * after the machine was constructed, it points to itself. The state for this method must
     * be one of the defined states ({@link BaseStateMachine#defineState(State)}).
     *
     * @param currentState The state the machine shall point at. Must be one of the previously
     *                     defined states/state-machines or the machine itself.
     * @return {@link BaseStateMachine} this for chaining
     * @throws NoSuchStateException if the given state is not this machine and not a state that
     * was previously registered using {@link BaseStateMachine#defineState(State)}.
     */
    public BaseStateMachine setCurrentState(@NonNull final State currentState) throws NoSuchStateException {
        Objects.requireNonNull(currentState);
        if (!this.getDefinedStates().contains(currentState)) {
            throw new NoSuchStateException("Not a known/defined state: " + currentState.getName());
        }

        this.currentState = currentState;
        return this;
    }

    /**
     * Obtains an unmodifiable set of all defined states.
     *
     * {@inheritDoc}
     */
    @Override
    public Set<State> getDefinedStates() {
        return this.definedStatesUnmod;
    }
}

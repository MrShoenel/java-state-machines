package io.github.mrshoenel.stateMachines.stateMachine;

import io.github.mrshoenel.stateMachines.state.BaseState;
import io.github.mrshoenel.stateMachines.state.State;
import org.springframework.lang.NonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


/**
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
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public State getCurrentState() {
        return this.currentState;
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

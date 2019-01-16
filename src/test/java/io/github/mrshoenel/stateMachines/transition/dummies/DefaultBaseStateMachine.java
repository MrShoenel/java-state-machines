package io.github.mrshoenel.stateMachines.transition.dummies;

import io.github.mrshoenel.stateMachines.state.BaseState;
import io.github.mrshoenel.stateMachines.stateMachine.BaseStateMachine;
import org.springframework.lang.NonNull;

import java.util.Objects;

public class DefaultBaseStateMachine extends BaseStateMachine {
    /**
     * Creates a new {@link BaseStateMachine} with the given name.
     *
     * @param name The name of the Machine. Like for states, this name should
     *             uniquely identify this machine, if it is part of a larger
     *             machine.
     */
    public DefaultBaseStateMachine(String name) {
        super(name);
    }

    public DefaultBaseStateMachine addDefinedState(@NonNull final BaseState state) {
        this.definedStates.add(Objects.requireNonNull(state));
        return this;
    }
}

package io.github.mrshoenel.stateMachines.blackbox;

import io.github.mrshoenel.stateMachines.StateMachineArtifact;
import io.github.mrshoenel.stateMachines.exception.IllegalTransitionException;
import io.github.mrshoenel.stateMachines.exception.NoSuchStateException;
import io.github.mrshoenel.stateMachines.exception.NoSuchTransitionException;
import io.github.mrshoenel.stateMachines.state.BaseState;
import io.github.mrshoenel.stateMachines.stateMachine.BaseStateMachine;
import io.github.mrshoenel.stateMachines.stateMachine.StateMachine;
import io.github.mrshoenel.stateMachines.transition.Transition;
import org.springframework.lang.NonNull;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * A {@link BlackBoxStateMachine} is a wrapper around an actual state-machine (an
 * instance of a {@link BaseStateMachine}). It does expressly not allow to inspect
 * the defined states or the current state and only exposes the transitions. The
 * important mechanics of a {@link BlackBoxStateMachine} are to manage relations
 * between machines and nested states without exposing them. The exposed transitions
 * are of type {@link BlackBoxTransition}. Those do not allow to get the from- and
 * to-states, but rather only the required arguments. A {@link BlackBoxStateMachine}
 * is useful for when the underlying {@link BaseStateMachine} should not be exposed
 * to the user and the only requirements are obtaining and executing allowed tran-
 * sitions. Also, transitions can only be carried out by the machine, and no other
 * way exists. The {@link BlackBoxStateMachine} enforces the usage of implementations
 * (or subclasses) of {@link BaseState} and {@link BaseStateMachine} as well as
 * {@link Transition} using only {@link BaseState} (or more derived) objects, so that
 * its mechanics can work. The beauty of a {@link BlackBoxStateMachine} is that it is
 * always aware of its ever-so-nested state/-machine and that the user thus not need
 * to know or use methods such as {@link StateMachine#getCurrentStateDeep()}.
 *
 * @author Sebastian Hönel development@hoenel.net
 * @param <T> A type sub-classing (or being) {@link BaseStateMachine}.
 */
public class BlackBoxStateMachine<T extends BaseStateMachine> implements StateMachineArtifact {

    private final T machine;

    /**
     * Creates a new {@link BlackBoxStateMachine} with the given name.
     *
     * @param machine The actual
     */
    public BlackBoxStateMachine(@NonNull final T machine) {
        this.machine = Objects.requireNonNull(machine);
    }

    @Override
    public String getName() {
        return this.machine.getName();
    }

    /**
     * Executes a transition by its name. Check for available transitions using
     * {@link #getTransitions()}. If the 'to'-state is a state machine, then its
     * current state will be set to itself after the transition. This method re-
     * quires that all states are belonging to another machine or that they are
     * machines as well (i.e. no orphaned states). The machine of the 'to'-state
     * of the transition will be made to point at it afterwards by the BlackBox.
     * Note that all transitions require the usage of {@link BaseState} or more
     * derived classes.
     *
     * @param name the name of the transiton to execute.
     * @return {@link BlackBoxStateMachine} this for chaining
     * @throws IllegalTransitionException if the 'to'-state is not a machine or a
     * state that does not belong to a machine (an orphan).
     * @throws NoSuchTransitionException If the transition identified by the given
     * name cannot be found.
     * @throws NoSuchStateException if the 'to'-state is a {@link BaseStateMachine}
     * but cannot made to point at itself.
     */
    public BlackBoxStateMachine<T> transition(@NonNull final String name)
        throws IllegalTransitionException, NoSuchTransitionException, NoSuchStateException
    {
        Objects.requireNonNull(name);
        // Note that BlackBoxTransitions always guarantee usage of BaseState
        final var trs = this.getTransitions();
        if (!trs.containsKey(name)) {
            throw new NoSuchTransitionException("No transition with name " + name);
        }

        final var tr = trs.get(name).getTransition();
        final var to = (BaseState) tr.getToState();

        tr.transition();

        if (to instanceof BaseStateMachine) {
            ((BaseStateMachine) to).setCurrentState(to);
        }

        final var machine = this.machine.getDefinedStates().contains(to) ?
            this.machine :
            // Else: the 'to'-state is a state of/or a nested state-machine
            to.getBelongsToMachine();

        if (machine == null) {
            throw new IllegalTransitionException("Orphan state: " + to.getName());
        }

        machine.setCurrentState(to);

        return this;
    }

    /**
     * Run a {@link BlackBoxTransition} by supplying the instance of it. This method
     * uses the transition's name to call {@link #transition(String)}.
     *
     * @param transition A non-null transition used to get the name from
     * @return {@link BlackBoxStateMachine} this for chaining
     * @throws IllegalTransitionException if the 'to'-state is not a machine or a
     * state that does not belong to a machine (an orphan).
     * @throws NoSuchTransitionException If the transition identified by the given
     * name cannot be found.
     * @throws NoSuchStateException if the 'to'-state is a {@link BaseStateMachine}
     * but cannot made to point at itself.
     */
    public BlackBoxStateMachine<T> transition(@NonNull final BlackBoxTransition transition)
        throws IllegalTransitionException, NoSuchTransitionException, NoSuchStateException
    {
        return this.transition(Objects.requireNonNull(transition).getName());
    }

    /**
     * Get a map of all currently available transitions. These depend on the current
     * latent underlying state. Note that for every call to this method, a new instance
     * of a {@link Map} containing new instances of {@link BlackBoxTransition}s is
     * returned.
     *
     * @return {@link Map}
     */
    public Map<String, BlackBoxTransition> getTransitions() {
        return Collections.unmodifiableMap(this.machine.getCurrentStateDeep().getTransitions().entrySet()
            .stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                kv -> new BlackBoxTransition(kv.getValue())
            )));
    }
}

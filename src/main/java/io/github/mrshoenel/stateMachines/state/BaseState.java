package io.github.mrshoenel.stateMachines.state;

import io.github.mrshoenel.stateMachines.exception.NoSuchStateException;
import io.github.mrshoenel.stateMachines.exception.NoSuchTransitionException;
import io.github.mrshoenel.stateMachines.stateMachine.BaseStateMachine;
import io.github.mrshoenel.stateMachines.stateMachine.StateMachine;
import io.github.mrshoenel.stateMachines.transition.Transition;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * A basic implementation of {@link State} that comes with a standard set
 * of features, such as adding/removing transitions, allow initialization
 * (useful for anonymous classes) and a meaningful {@link BaseState#toString()} method.
 *
 * @author Sebastian Hönel development@hoenel.net
 */
public class BaseState implements State {
    protected final String name;

    protected final Map<String, Transition> transitions;

    private final Map<String, Transition> transitionsUnmod;

    protected BaseStateMachine belongsToMachine;

    /**
     * Initializes a new instance of a {@link BaseState}.
     *
     * @param name A name that should uniquely identify this state across
     *             the entire machine it is part of.
     * @param machine A {@link BaseStateMachine} this state belongs to. This
     *                machine can be null and also be assigned later. States
     *                that belong to a machine will make it aware if there is
     *                a transition activating them.
     */
    public BaseState(@NonNull final String name, @Nullable final BaseStateMachine machine) {
        this.name = Objects.requireNonNull(name);
        this.setBelongsToMachine(machine);
        this.transitions = new HashMap<>();
        this.transitionsUnmod = Collections.unmodifiableMap(this.transitions);
        this.initialize();
    }

    /**
     * Calls {@link BaseState#BaseState(String, BaseStateMachine)} with the
     * argument for the machine being null (i.e. this state will not belong to
     * a machine initially).
     *
     * @param name A name that should uniquely identify this state across
     *             the entire machine it is part of.
     */
    public BaseState(@NonNull final String name) {
        this(name, null);
    }

    /**
     * Returns the {@link BaseStateMachine} this state belongs to, i.e. it
     * was defined on the machine using {@link BaseStateMachine#defineState(State)}.
     * When a state is defined using {@link BaseStateMachine#defineState(State)},
     * then that method will call this method to register the machine.
     *
     * @return {@link BaseStateMachine} or {@code null}, if this state does not
     * belong to a machine (yet).
     */
    public BaseStateMachine getBelongsToMachine() {
        return belongsToMachine;
    }

    /**
     * Setter for the {@link BaseStateMachine} this state belongs to. Note that
     * the machine is nullable and this can be used to unset the relation. If a
     * state is being removed/undefined from a machine using the method
     * {@link BaseStateMachine#undefineState(State)}, then that method will also
     * call this method with an argument of null, to remove the association.
     *
     * @param stateMachine
     * @return
     */
    public BaseState setBelongsToMachine(@Nullable final BaseStateMachine stateMachine) {
        this.belongsToMachine = stateMachine;
        return this;
    }

    /**
     * Does nothing. Should be overridden when initialization (such as
     * adding transitions on the fly) is required. This method is called
     * by the constructor.
     */
    protected void initialize() { }

    /**
     * @return java.lang.String the name of this state.
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * {@inheritDoc}
     * {@link BaseState#hasTransition(String)}.
     */
    @Override
    public State setTransition(@NonNull Transition transition) {
        Objects.requireNonNull(transition);

        this.transitions.put(transition.getName(), transition);
        return this;
    }

    /**
     * Uses a state's name to ultimately call {@link BaseState#unsetTransition(String)}.
     *
     * {@inheritDoc}
     * {@link BaseState#hasTransition(String)}.
     * @throws NoSuchTransitionException
     */
    @Override
    public State unsetTransition(@NonNull final Transition transition) throws NoSuchTransitionException {
        Objects.requireNonNull(transition);
        return this.unsetTransition(transition.getName());
    }

    /**
     * Uses the name of a transition to remove it.
     *
     * {@link State#unsetTransition(Transition)}
     * @throws NoSuchTransitionException
     * @return BaseState this for chaining.
     */
    public BaseState unsetTransition(@NonNull final String transitionName) throws NoSuchTransitionException {
        Objects.requireNonNull(transitionName);

        if (this.transitions.containsKey(transitionName)) {
            this.transitions.remove(transitionName);
            return this;
        }
        throw new NoSuchTransitionException("There is no transition with the name '" + transitionName + "'.");
    }

    /**
     * Used to determine if this state has a defined transition with the given name.
     * Calls {@link BaseState#hasTransition(String)}.
     *
     * @param transition the {@link Transition} to check for
     * @return boolean true, iff this state has a transition with the given name.
     */
    public boolean hasTransition(@NonNull final Transition transition) {
        Objects.requireNonNull(transition);
        return this.hasTransition(transition.getName());
    }

    /**
     * Used to determine if this state has a defined transition with the given name.
     *
     * @param name The name of the transition to check
     * @return boolean true, iff this state has a transition with the given name.
     */
    public boolean hasTransition(@NonNull final String name) {
        Objects.requireNonNull(name);
        return this.transitions.containsKey(name);
    }

    /**
     * Returns a value indicating whether this state has any (outgoing) transitions
     * defined or not.
     *
     * @return boolean
     */
    public boolean hasTransitions() {
        return this.transitions.size() > 0;
    }

    /**
     * Overridden to return true for when there are no transitions defined for this
     * state. In that case, the state cannot be left.
     *
     * @return boolean true, iff this state does not have any (outgoing) transitions.
     */
    @Override
    public boolean isFinalState() {
        return !this.hasTransitions();
    }

    /**
     * Returns an unmodifiable map.
     *
     * {@inheritDoc}
     */
    @Override
    public Map<String, Transition> getAllDefinedTransitions() {
        return this.transitionsUnmod;
    }

    /**
     * Returns the concrete sub-class' name and the given name.
     *
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "[" + this.getClass().getSimpleName() + ": " + this.getName() + "]";
    }
}

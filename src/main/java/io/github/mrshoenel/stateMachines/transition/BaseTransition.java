package io.github.mrshoenel.stateMachines.transition;

import io.github.mrshoenel.stateMachines.exception.NoSuchArgumentException;
import io.github.mrshoenel.stateMachines.state.State;
import org.springframework.lang.NonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * An base-class for transitions, the only method not implemented is
 * {@link Transition#isAllowed()}, as it should always be specific to the
 * transition or the states it transits between.
 *
 * @author Sebastian Hönel development@hoenel.net
 */
public class BaseTransition implements Transition {

    protected final String name;

    protected final State fromState, toState;

    protected final Map<String, BaseTransitionArgument<?>> transitionArgs;

    private final Map<String, TransitionArgument<?>> transitionArgsUnmod;

    public BaseTransition(final String name, @NonNull final State fromState, @NonNull final State toState) {
        Objects.requireNonNull(fromState);
        Objects.requireNonNull(toState);

        this.name = name;
        this.fromState = fromState;
        this.toState = toState;
        this.transitionArgs = new HashMap<>();
        this.transitionArgsUnmod = Collections.unmodifiableMap(this.transitionArgs);
        this.initialize();
    }

    /**
     * Initializes a new {@link BaseTransition} and generates a name of the
     * scheme "trans_to_" + 'to'-state's toString()-method.
     *
     * @param fromState
     * @param toState
     */
    public BaseTransition(final State fromState, final State toState) {
        this("trans_to_" + toState.toString(), fromState, toState);
    }

    /**
     * Does nothing. Should be overridden when initialization (such as
     * adding arguments on the fly) is required. This method is called
     * by the constructors.
     */
    protected void initialize() { }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public State getFromState() {
        return this.fromState;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public State getToState() {
        return this.toState;
    }

    /**
     * Sets/replaces an argument.
     *
     * @param argument the argument to set.
     * @return BaseTransition this for chaining
     */
    public BaseTransition setArgument(@NonNull BaseTransitionArgument<?> argument) {
        Objects.requireNonNull(argument);

        this.transitionArgs.put(argument.getName(), argument);
        return this;
    }

    /**
     * Removes an argument identified by the given name.
     *
     * @param argName the argument's name to remove
     * @return BaseTransition this for chaining
     */
    public BaseTransition removeArgument(@NonNull final String argName) throws NoSuchArgumentException {
        Objects.requireNonNull(argName);

        if (this.transitionArgs.containsKey(argName)) {
            this.transitionArgs.remove(argName);
            return this;
        }
        throw new NoSuchArgumentException("There is no argument with the name '" + argName + "'.");
    }

    /**
     * Removes an argument by using its name and calling {@link BaseTransition#removeArgument(String)}.
     *
     * @param argument the argument to remove.
     * @return BaseTransition this for chaining
     */
    public BaseTransition removeArgument(@NonNull final BaseTransitionArgument<?> argument) throws NoSuchArgumentException {
        Objects.requireNonNull(argument);
        return this.removeArgument(argument.getName());
    }


    /**
     * Returns an unmodifiable map of the arguments of this transition.
     *
     * {@inheritDoc}
     */
    @Override
    public Map<String, TransitionArgument<?>> getTransitionArguments() {
        return this.transitionArgsUnmod;
    }


    /**
     * Returns a meaningful string-representation with the type of the arguments and the
     * value (if present).
     *
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "[" + this.getClass().getSimpleName() + ": " + this.getName() + "]";
    }
}

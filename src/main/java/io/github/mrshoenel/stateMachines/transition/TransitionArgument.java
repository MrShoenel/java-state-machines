package io.github.mrshoenel.stateMachines.transition;


import io.github.mrshoenel.stateMachines.StateMachineArtifact;
import io.github.mrshoenel.stateMachines.exception.NoValueSetException;


/**
 * A {@link Transition} can have arbitrary many arguments, each of its own type,
 * pretty similar to functions and their arguments.
 *
 * @param <T> The type of the argument being used in the transition.
 *
 * @author Sebastian Hönel development@hoenel.net
 */
public interface TransitionArgument<T> extends StateMachineArtifact {
    /**
     * @return The type of the argument.
     */
    Class<T> getType();

    /**
     * Used to set the value of an argument.
     *
     * @param t The value.
     */
    void setValue(T t);

    /**
     * Returns a value indicating whether this argument has been set
     * to a value.
     *
     * @return boolean
     */
    boolean hasValue();

    /**
     * Gets the value that was set previously. If a value was previously set can
     * be checked using {@link TransitionArgument#hasValue()}.
     *
     * @throws NoValueSetException if it is attempted to obtain the value for this
     * argument. Use {@link TransitionArgument#hasValue()} to check for that.
     * @return the value of type T
     */
    T getValue() throws NoValueSetException;
}

package io.github.mrshoenel.stateMachines.transition;


import io.github.mrshoenel.stateMachines.exception.NoValueSetException;

/**
 * @param <T> The type of the argument being used in the transition.
 *
 * @author Sebastian Hönel development@hoenel.net
 */
public class BaseTransitionArgument<T> implements TransitionArgument<T> {
    protected boolean hasValue;

    protected final Class<T> type;

    protected final String name;

    protected T value;

    /**
     * Initializes a new {@link BaseTransitionArgument} with a value already
     * present.
     *
     * @param value the value for this argument
     * @param name the name of this argument; should be unique within all
     *             arguments for a transition.
     */
    public BaseTransitionArgument(final T value, final String name) {
        this((Class<T>) value.getClass(), name);
        this.setValue(value);
    }

    /**
     * Initializes a new {@link BaseTransitionArgument} without a value, but
     * with its known type, so that a value may be set later.
     *
     * @param type the type of the value for this argument
     * @param name the name of this argument; should be unique within all
     *             arguments for a transition.
     */
    public BaseTransitionArgument(final Class<T> type, final String name) {
        this.type = type;
        this.name = name;
        this.unsetValue();
    }

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
    public Class<T> getType() {
        return this.type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(T t) {
        this.value = t;
        this.hasValue = true;
    }

    /**
     * Used to unset a may previously set value, thereby 'emptying' this argument.
     */
    public void unsetValue() {
        this.value = null;
        this.hasValue = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasValue() {
        return this.hasValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T getValue() throws NoValueSetException {
        if (!this.hasValue()) {
            throw new NoValueSetException(
                "No value has been set for argument'" + this.name + "'.");
        }
        return this.value;
    }

    /**
     * Returns a meaningful string-representation with the type of the arguments and the
     * value (if present).
     *
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        String value = null;
        if (this.hasValue()) {
            try {
                value = this.getValue().toString();
            } catch (NoValueSetException nvse) { }
        }

        return "[" + this.getClass().getSimpleName() + "<" + this.getType().getSimpleName() + ">: " + (this.hasValue() ? value : "<no value was set>") + "]";
    }
}

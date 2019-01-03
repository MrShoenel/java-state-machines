package io.github.mrshoenel.stateMachines.exception;


/**
 * An exception to be thrown when an attempt is made to remove a non-
 * existing transition from a state.
 *
 * @author Sebastian Hönel development@hoenel.net
 */
public class NoSuchTransitionException extends StateMachineArtifactException {
    public NoSuchTransitionException(final String message) {
        super(message);
    }
}

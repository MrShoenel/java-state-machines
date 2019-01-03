package io.github.mrshoenel.stateMachines.exception;


/**
 * An exception to be thrown when no value has been set for an
 * argument and it is being attempted to access.
 *
 * @author Sebastian Hönel development@hoenel.net
 */
public class NoValueSetException extends StateMachineArtifactException {
    public NoValueSetException(final String message) {
        super(message);
    }
}

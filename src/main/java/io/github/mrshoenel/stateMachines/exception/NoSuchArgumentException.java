package io.github.mrshoenel.stateMachines.exception;


/**
 * An exception to be thrown when an attempt is made to remove a non-
 * existing argument from a transition.
 *
 * @author Sebastian Hönel development@hoenel.net
 */
public class NoSuchArgumentException extends StateMachineArtifactException {
    public NoSuchArgumentException(final String message) {
        super(message);
    }
}

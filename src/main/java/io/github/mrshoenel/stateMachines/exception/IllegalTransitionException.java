package io.github.mrshoenel.stateMachines.exception;


/**
 * An exception to be thrown when an illegal transition is being
 * executed on a state(-machine).
 *
 * @author Sebastian Hönel development@hoenel.net
 */
public class IllegalTransitionException extends StateMachineArtifactException {
    public IllegalTransitionException(final String message) {
        super(message);
    }
}
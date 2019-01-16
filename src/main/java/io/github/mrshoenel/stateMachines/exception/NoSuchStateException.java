package io.github.mrshoenel.stateMachines.exception;


/**
 * To be thrown whenever a certain state does not exist or cannot be found.
 *
 * @author Sebastian Hönel development@hoenel.net
 */
public class NoSuchStateException extends StateMachineArtifactException {
    public NoSuchStateException(String message) {
        super(message);
    }
}

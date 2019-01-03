package io.github.mrshoenel.stateMachines.exception;


/**
 * An exception super-class for all exceptions as being emitted
 * by artifacts used in state-machines.
 *
 * @author Sebastian Hönel development@hoenel.net
 */
public class StateMachineArtifactException extends Exception {
    public StateMachineArtifactException(final String message) {
        super(message);
    }
}
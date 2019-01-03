package io.github.mrshoenel.stateMachines;


/**
 * Base interface for all artifacts involved in state machines,
 * such as transitions, states or state-machines themselves.
 */
public interface StateMachineArtifact {
    /**
     * Used to obtain a string that uniquely identifies each
     * artifact.
     *
     * @return the name or ID as String.
     */
    String getName();
}

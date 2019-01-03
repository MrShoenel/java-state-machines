/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package io.github.mrshoenel.stateMachines.transition.simplePoker.transition;

import io.github.mrshoenel.stateMachines.exception.IllegalTransitionException;
import io.github.mrshoenel.stateMachines.state.State;
import io.github.mrshoenel.stateMachines.transition.BaseTransition;

import java.util.function.Consumer;

public class LoggingTransition extends BaseTransition {

    protected final Consumer<LoggingTransition> transitionConsumer;

    public LoggingTransition(final Consumer<LoggingTransition> transitionConsumer, String name, State fromState, State toState) {
        super(name, fromState, toState);
        this.transitionConsumer = transitionConsumer;
    }

    public LoggingTransition(final Consumer<LoggingTransition> transitionConsumer, State fromState, State toState) {
        super(fromState, toState);
        this.transitionConsumer = transitionConsumer;
    }

    @Override
    public void transition() throws IllegalTransitionException {
        super.transition();
        this.transitionConsumer.accept(this);
    }
}

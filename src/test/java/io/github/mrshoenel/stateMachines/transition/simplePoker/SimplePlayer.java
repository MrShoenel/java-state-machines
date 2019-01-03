/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package io.github.mrshoenel.stateMachines.transition.simplePoker;

public class SimplePlayer implements IPlayer {
    protected final String name;

    public SimplePlayer(final String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int hashCode() {
        return this.getName().hashCode() * 31;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof SimplePlayer
                && this.getName().equals(((SimplePlayer) obj).getName());
    }
}

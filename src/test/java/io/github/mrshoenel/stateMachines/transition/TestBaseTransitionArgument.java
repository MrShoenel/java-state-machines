package io.github.mrshoenel.stateMachines.transition;

import io.github.mrshoenel.stateMachines.exception.NoValueSetException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestBaseTransitionArgument {
    @Test
    public void testArgumentSemantics() {
        var arg = new BaseTransitionArgument<>(String.class, "arg1");

        assertFalse(arg.hasValue());
        assertThrows(NoValueSetException.class, arg::getValue);

        assertEquals("[BaseTransitionArgument<String>: <no value was set>]", arg.toString());
        arg.setValue("foo");
        assertEquals("[BaseTransitionArgument<String>: foo]", arg.toString());
        try {
            assertEquals("foo", arg.getValue());
        } catch (Throwable t) {
            throw new Error(t);
        }
    }
}

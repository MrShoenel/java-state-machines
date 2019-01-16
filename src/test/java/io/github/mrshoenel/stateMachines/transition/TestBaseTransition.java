package io.github.mrshoenel.stateMachines.transition;

import io.github.mrshoenel.stateMachines.blackbox.BlackBoxTransition;
import io.github.mrshoenel.stateMachines.exception.NoSuchArgumentException;
import io.github.mrshoenel.stateMachines.state.BaseState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestBaseTransition {

    @Test
    public void testCreateTransitionWithArgs() {
        var s1 = new BaseState("s1");
        var s2 = new BaseState("s2");
        var tr = new BaseTransition(s1, s2);

        assertSame(s1, tr.getFromState());
        assertSame(s2, tr.getToState());

        assertEquals("trans_to_[BaseState: s2]", tr.getName());
        assertEquals("[BaseTransition: trans_to_[BaseState: s2]]", tr.toString());
    }

    @Test
    public void testThrowsRemoveArg() {
        var s1 = new BaseState("s1");
        var s2 = new BaseState("s2");
        var tr = new BaseTransition(s1, s2);

        assertThrows(NoSuchArgumentException.class, () -> {
            tr.removeArgument("bla");
        });

        assertDoesNotThrow(() -> {
            tr.setArgument(new BaseTransitionArgument<>(
                "myVal", "arg1"));

            assertEquals("[BaseTransitionArgument<String>: myVal]",
                tr.getTransitionArguments().get("arg1").toString());

            tr.removeArgument("arg1");

            var arg1 = new BaseTransitionArgument<>(Integer.class, "arg2");
            tr.setArgument(arg1);
            tr.removeArgument(arg1);
        });
    }

    @Test
    public void testBlackBoxTransition() {
        var tr = new BaseTransition(new BaseState("s1"), new BaseState("s2"));
        var tr2 = new BaseTransition(tr.getFromState(), tr.getToState());

        var bb1 = new BlackBoxTransition(tr);

        assertEquals(tr.hashCode() ^ 31, bb1.hashCode());
        assertTrue(new BlackBoxTransition(tr).equals(bb1));
        // Covers transition with same states, but it's not the same transition
        assertFalse(new BlackBoxTransition(tr2).equals(bb1));

        assertFalse(bb1.equals(new Object()));
    }
}

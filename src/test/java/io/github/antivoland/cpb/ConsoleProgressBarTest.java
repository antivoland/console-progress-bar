package io.github.antivoland.cpb;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SuppressWarnings("resource")
public class ConsoleProgressBarTest {
    private static final PrintStream DUMMY = new PrintStream(new ByteArrayOutputStream());

    @Test
    public void testInvalidMax() {
        assertThatThrownBy(() -> new ConsoleProgressBar(-1, DUMMY, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Max value must be non-negative");
    }

    @Test
    public void testNullStream() {
        assertThatThrownBy(() -> new ConsoleProgressBar(0, null, 1))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Stream must not be null");
    }

    @Test
    public void testInvalidTickMillis() {
        assertThatThrownBy(() -> new ConsoleProgressBar(0, DUMMY, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Tick millis must be positive");
    }

    @Test
    public void testInvalidStepDelta() {
        assertThatThrownBy(() -> new ConsoleProgressBar(0, DUMMY, 1).stepBy(-1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Delta must be non-negative");
    }
}
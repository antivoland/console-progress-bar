package io.github.antivoland.cpb;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

import static io.github.antivoland.cpb.ConsoleProgressBar.DEFAULT_TICK_MILLIS;
import static org.assertj.core.api.Assertions.assertThat;

public class DrunkPiratesTest {
    private static final int PIRATES = 999;
    private static final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(PIRATES);

    @AfterAll
    public static void destroy() {
        THREAD_POOL.shutdownNow();
    }

    @Test
    public void testLesser() throws Exception {
        test(PIRATES - 1);
        test(PIRATES / 2);
    }

    @Test
    public void testExact() throws Exception {
        test(PIRATES);
    }

    @Test
    public void testGreater() throws Exception {
        test(PIRATES + 1);
        test(PIRATES * 2);
    }

    public void test(int bottles) throws Exception {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (ConsoleProgressBar bar = new ConsoleProgressBar(bottles, new PrintStream(buffer), DEFAULT_TICK_MILLIS)) {
            CountDownLatch start = new CountDownLatch(1);
            CountDownLatch finish = new CountDownLatch(PIRATES);
            Stream.generate(() -> drink(start, finish, bar)).limit(PIRATES).forEach(THREAD_POOL::submit);
            start.countDown();
            finish.await();
        }

        ProgressState expected = new ProgressState(bottles);
        expected.stepBy(PIRATES);
        String expectedPercentage = ConsoleDrawer.PERCENTAGE_FORMAT.apply(expected.max(), expected.current());
        String expectedAbsoluteRatio = ConsoleDrawer.ABSOLUTE_RATIO_FORMAT.apply(expected.max(), expected.current());

        String actual = buffer.toString();
        assertThat(actual).contains(expectedPercentage);
        assertThat(actual).contains(expectedAbsoluteRatio);
    }

    private Runnable drink(CountDownLatch start, CountDownLatch finish, ConsoleProgressBar bar) {
        return () -> {
            try {
                start.await();
                bar.step();
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                finish.countDown();
            }
        };
    }
}
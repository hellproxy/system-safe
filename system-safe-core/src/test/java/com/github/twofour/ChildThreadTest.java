package com.github.twofour;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.String.format;
import static java.util.concurrent.TimeUnit.SECONDS;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.IntStream.range;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author harrydent
 */
@ExtendWith(SystemSafeExtension.class)
@DisplayName("Test how properties are passed to spawned child threads")
class ChildThreadTest {

    @Test
    @DisplayName("test that spawned child threads inherit their parent's properties")
    void test_spawnedChildThreads_inheritTheirParentProperties() throws InterruptedException {
        var key = "key";
        var value = "value";

        System.setProperty(key, value);

        AtomicReference<String> threadLookupRef = new AtomicReference<>();
        var childThread = new Thread(() -> threadLookupRef.set(System.getProperty("key")));

        childThread.start();
        childThread.join(1000);

        assertThat(threadLookupRef).hasValue(value);
    }

    @Test
    @DisplayName("test that spawned child threads do not interact with each other's or their parent's properties")
    void test_spawnedChildThreads_doNotInteract() throws InterruptedException, ExecutionException {
        var key = "key";
        var value = "value";
        var valueFormatter = "value-%d";
        var totalRuns = 100;

        System.setProperty(key, value);

        AtomicInteger idFountain = new AtomicInteger();

        List<Callable<String>> runnables = range(0, totalRuns)
                .<Callable<String>>mapToObj(i -> () -> {
                    System.setProperty(key, format(valueFormatter, idFountain.getAndIncrement()));
                    return System.getProperty(key);
                })
                .collect(toList());

        ExecutorService executor = Executors.newWorkStealingPool();
        List<Future<String>> futures = executor.invokeAll(runnables, 1, SECONDS);

        List<String> values = new ArrayList<>();
        for (var future : futures) {
            values.add(future.get());
        }

        var expected = range(0, totalRuns)
                .mapToObj(i -> format(valueFormatter, i))
                .collect(toSet());

        assertThat(System.getProperty(key)).isEqualTo(value);
        assertThat(values).containsExactlyInAnyOrderElementsOf(expected);
    }
}

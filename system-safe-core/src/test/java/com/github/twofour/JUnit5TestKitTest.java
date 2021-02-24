package com.github.twofour;

import jdk.jfr.Description;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.testkit.engine.EngineTestKit;

import java.util.HashMap;

import static com.github.twofour.RepetitionTest.TOTAL_REPETITIONS;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

/**
 * @author harrydent
 */
@Slf4j
@DisplayName("Test using JUnit5 test kit")
class JUnit5TestKitTest {

    @Test
    @Description("test that RepetitionTest still passes in the JUnit5 test kit context")
    void test_canRunRepetitionTest_withJUnit5TestKit() {
        var propertiesBefore = new HashMap<>(System.getProperties());

        EngineTestKit
                .engine("junit-jupiter")
                .configurationParameter("junit.jupiter.execution.parallel.enabled", "true")
                .configurationParameter("junit.jupiter.execution.parallel.mode.default", "concurrent")
                .selectors(selectClass(RepetitionTest.class))
                .execute()
                .testEvents()
                .debug()
                .assertStatistics(stats -> stats.started(TOTAL_REPETITIONS).succeeded(TOTAL_REPETITIONS));

        var propertiesAfter = new HashMap<>(System.getProperties());

        Assertions.assertThat(propertiesBefore).isNotEmpty().isEqualTo(propertiesAfter);
    }
}

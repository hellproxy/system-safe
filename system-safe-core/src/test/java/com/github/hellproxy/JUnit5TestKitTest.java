package com.github.hellproxy;

import jdk.jfr.Description;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.testkit.engine.EngineTestKit;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

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
                .assertStatistics(stats -> stats.started(RepetitionTest.TOTAL_REPETITIONS).succeeded(RepetitionTest.TOTAL_REPETITIONS));

        var propertiesAfter = new HashMap<>(System.getProperties());

        assertThat(propertiesBefore).isNotEmpty().isEqualTo(propertiesAfter);
    }
}

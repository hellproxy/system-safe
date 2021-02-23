package com.github.twofour;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.platform.testkit.engine.EngineTestKit;

import java.util.HashMap;

import static com.github.twofour.SystemSafeExtensionTest.TOTAL_REPETITIONS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

/**
 * @author harrydent
 */
@Slf4j
class SystemSafeExtensionDemo {

    @Test
    void test() {
        var propertiesBefore = new HashMap<>(System.getProperties());

        EngineTestKit
            .engine("junit-jupiter")
            .configurationParameter("junit.jupiter.execution.parallel.enabled", "true")
            .configurationParameter("junit.jupiter.execution.parallel.mode.default", "concurrent")
            .selectors(selectClass(SystemSafeExtensionTest.class))
            .execute()
            .testEvents()
            .debug()
            .assertStatistics(stats -> stats.started(TOTAL_REPETITIONS).succeeded(TOTAL_REPETITIONS));

        var propertiesAfter = new HashMap<>(System.getProperties());

        assertThat(propertiesBefore).isNotEmpty().isEqualTo(propertiesAfter);
    }
}

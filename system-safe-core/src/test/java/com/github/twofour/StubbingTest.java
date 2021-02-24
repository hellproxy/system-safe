package com.github.twofour;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author harrydent
 */
@ExtendWith(SystemSafeExtension.class)
@DisplayName("Test System properties have been stubbed")
class StubbingTest {

    @Test
    @DisplayName("check that System properties are a PropertiesAdapter instance")
    void test_systemProperties_isStubbed() {
        assertThat(System.getProperties()).isInstanceOf(PropertiesAdapter.class);
    }
}

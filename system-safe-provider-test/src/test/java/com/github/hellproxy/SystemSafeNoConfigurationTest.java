package com.github.hellproxy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author harrydent
 */
@DisplayName("Test SystemSafeExtension is automatically configured using junit-platform.properties")
class SystemSafeNoConfigurationTest {

    private static final String PROPERTY_NAME = "foo";
    private static final String PROPERTY_VALUE = "bar";

    @Test
    @Order(1)
    @DisplayName("check that System properties are a PropertiesAdapter instance")
    void test_systemProperties_isStubbed() {
        assertThat(System.getProperties()).isInstanceOf(PropertiesAdapter.class);
    }

    @Test
    @Order(2)
    @DisplayName("check that property 'foo' is unset")
    void test_systemProperty_isUnset() {
        assertThat(System.getProperty(PROPERTY_NAME)).isNull();

        System.setProperty(PROPERTY_NAME, PROPERTY_VALUE);
    }

    @Test
    @Order(3)
    @DisplayName("check that property 'foo' is unset after being set in previous test")
    void test_systemProperty_isUnsetAfterBeingPreviouslySet() {
        assertThat(System.getProperty(PROPERTY_NAME)).isNull();
    }
}

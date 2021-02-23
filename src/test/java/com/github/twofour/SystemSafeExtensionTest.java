package com.github.twofour;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static java.lang.String.format;
import static java.util.stream.IntStream.range;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author harrydent
 */
@DisplayName("Test SafeSystem extension safely manages System properties")
@ExtendWith(SystemSafeExtension.class)
class SystemSafeExtensionTest {

    static final int TOTAL_REPETITIONS = 100;

    @BeforeAll
    public static void beforeAll() {
        System.setProperty("beforeAll", "true");
    }

    @BeforeEach
    public void beforeEach(final RepetitionInfo repetitionInfo) {
        int currentRepetition = repetitionInfo.getCurrentRepetition();

        String beforeEachProperty = getBeforeEachProperty(currentRepetition);

        assertThat(System.getProperty("beforeAll")).isEqualTo("true");
        assertThat(System.getProperty(beforeEachProperty)).isNull();

        System.setProperty("beforeAll", "false");
        System.setProperty(beforeEachProperty, "true");

        range(1, TOTAL_REPETITIONS + 1)
            .filter(i -> i != repetitionInfo.getCurrentRepetition())
            .forEach(i -> System.setProperty(getBeforeEachProperty(i), "false"));
    }

    @RepeatedTest(value = TOTAL_REPETITIONS, name = "Call [{currentRepetition}/{totalRepetitions}]")
    void test(final RepetitionInfo repetitionInfo) {
        int currentRepetition = repetitionInfo.getCurrentRepetition();

        String beforeEachProperty = getBeforeEachProperty(currentRepetition);
        String insideTestProperty = getInsideTestProperty(currentRepetition);

        assertThat(System.getProperty("beforeAll")).isEqualTo("false");
        assertThat(System.getProperty(beforeEachProperty)).isEqualTo("true");
        assertThat(System.getProperty(insideTestProperty)).isNull();

        System.setProperty(beforeEachProperty, "false");
        System.setProperty(insideTestProperty, "true");

        range(1, TOTAL_REPETITIONS + 1)
            .filter(i -> i != currentRepetition)
            .forEach(i -> System.setProperty(getInsideTestProperty(i), "false"));
    }

    @AfterEach
    public void afterEach(final RepetitionInfo repetitionInfo) {
        int currentRepetition = repetitionInfo.getCurrentRepetition();

        String beforeEachProperty = getBeforeEachProperty(currentRepetition);

        assertThat(System.getProperty("beforeAll")).isEqualTo("false");
        assertThat(System.getProperty(beforeEachProperty)).isEqualTo("true");

        assertThat(range(1, TOTAL_REPETITIONS + 1))
            .filteredOn(i -> i != currentRepetition)
            .extracting(i -> System.getProperty(getBeforeEachProperty(i)))
            .hasSize(TOTAL_REPETITIONS - 1)
            .containsOnly("false");

        assertThat(range(1, TOTAL_REPETITIONS + 1))
            .extracting(i -> System.getProperty(getInsideTestProperty(i)))
            .containsOnlyNulls();
    }

    @AfterAll
    public static void afterAll() {
        assertThat(System.getProperty("beforeAll")).isEqualTo("true");

        assertThat(range(1, TOTAL_REPETITIONS + 1))
            .extracting(i -> System.getProperty(getBeforeEachProperty(i)))
            .containsOnlyNulls();
    }

    private static String getBeforeEachProperty(final int currentRepetition) {
        return format("beforeEach-%d", currentRepetition);
    }

    private static String getInsideTestProperty(final int currentRepetition) {
        return format("insideTest-%d", currentRepetition);
    }
}
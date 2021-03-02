package com.github.hellproxy;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static java.lang.String.format;
import static java.util.stream.IntStream.range;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@ExtendWith(SystemSafeExtension.class)
@DisplayName("Test System Properties don't interact across repeated tests")
class RepetitionTest {

    private static final String CLASS_SCOPE = "classScope";

    private static final String CLASS_STATE_1 = "CLASS_STATE_1";
    private static final String CLASS_STATE_2 = "CLASS_STATE_2";
    private static final String CLASS_STATE_3 = "CLASS_STATE_3";

    private static final String METHOD_STATE_1 = "METHOD_STATE_1";
    private static final String METHOD_STATE_2 = "METHOD_STATE_2";

    public static final int TOTAL_REPETITIONS = 100;

    @BeforeAll
    public static void beforeAll() {
        log.info("{} - beforeAll", CLASS_SCOPE);

        System.setProperty(CLASS_SCOPE, CLASS_STATE_1);
    }

    @BeforeEach
    public void beforeEach(final RepetitionInfo repetitionInfo) {
        int currentRepetition = repetitionInfo.getCurrentRepetition();
        String methodScope = getMethodScope(currentRepetition);

        log.info("{} - beforeEach", methodScope);

        // check that no other methods have managed to alert the properties set in either the class or method scope for
        // this invocation
        assertThat(System.getProperty(CLASS_SCOPE)).isEqualTo(CLASS_STATE_1);
        assertThat(System.getProperty(methodScope)).isNull();

        // set the class scope and method scope for this invocation
        System.setProperty(CLASS_SCOPE, CLASS_STATE_2);
        System.setProperty(methodScope, METHOD_STATE_1);
    }

    @RepeatedTest(value = TOTAL_REPETITIONS, name = "Call [{currentRepetition}/{totalRepetitions}]")
    void test(final RepetitionInfo repetitionInfo) {
        int currentRepetition = repetitionInfo.getCurrentRepetition();
        String methodScope = getMethodScope(currentRepetition);

        log.info("{} - test", methodScope);

        // check that no other methods have managed to alert the properties set in either the class or method scope for
        // this invocation, and that the class and method states are persisted from beforeEach()
        assertThat(System.getProperty(CLASS_SCOPE)).isEqualTo(CLASS_STATE_2);
        assertThat(System.getProperty(methodScope)).isEqualTo(METHOD_STATE_1);

        // set the class scope and method scope for this invocation
        System.setProperty(CLASS_SCOPE, CLASS_STATE_3);
        System.setProperty(methodScope, METHOD_STATE_2);
    }

    @AfterEach
    public void afterEach(final RepetitionInfo repetitionInfo) {
        int currentRepetition = repetitionInfo.getCurrentRepetition();
        String methodScope = getMethodScope(currentRepetition);

        log.info("afterEach: {}", methodScope);

        // check that no other methods have managed to alert the properties set in either the class or method scope for
        // this invocation, and that the class and method states are persisted from test()
        assertThat(System.getProperty(CLASS_SCOPE)).isEqualTo(CLASS_STATE_3);
        assertThat(System.getProperty(methodScope)).isEqualTo(METHOD_STATE_2);

        // check that no other method states have been picked up from other method scopes
        assertThat(range(1, TOTAL_REPETITIONS + 1))
                .filteredOn(i -> i != currentRepetition)
                .extracting(i -> System.getProperty(getMethodScope(i)))
                .hasSize(TOTAL_REPETITIONS - 1)
                .containsOnlyNulls();
    }

    @AfterAll
    public static void afterAll() {
        log.info("afterAll: {}", CLASS_SCOPE);

        // check that the class state has been reset to its value at the end of beforeAll()
        assertThat(System.getProperty(CLASS_SCOPE)).isEqualTo(CLASS_STATE_1);

        // check that all the method states have been cleared
        assertThat(range(1, TOTAL_REPETITIONS + 1))
                .extracting(i -> System.getProperty(getMethodScope(i)))
                .containsOnlyNulls();
    }

    private static String getMethodScope(final int currentRepetition) {
        return format("methodScope-%d", currentRepetition);
    }
}
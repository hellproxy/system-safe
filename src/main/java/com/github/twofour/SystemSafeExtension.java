package com.github.twofour;

import org.junit.jupiter.api.extension.*;

import java.util.Properties;

/**
 * @author harrydent
 */
public class SystemSafeExtension implements BeforeAllCallback,
                                            BeforeEachCallback,
                                            BeforeTestExecutionCallback,
                                            AfterTestExecutionCallback,
                                            AfterEachCallback,
                                            AfterAllCallback {

    private static final Properties INITIAL_SYSTEM_PROPERTIES;

    static {
        INITIAL_SYSTEM_PROPERTIES = System.getProperties();
        System.setProperties(new PropertiesAdapter());
    }

    static Properties getInitialSystemProperties() {
        return INITIAL_SYSTEM_PROPERTIES;
    }

    @Override
    public void beforeAll(ExtensionContext context) {
        PropertiesAdapter.enterContext();
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        PropertiesAdapter.enterContext();
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) {
        PropertiesAdapter.enterContext();
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        PropertiesAdapter.exitContext();
    }

    @Override
    public void afterEach(ExtensionContext context) {
        PropertiesAdapter.exitContext();
    }

    @Override
    public void afterAll(ExtensionContext context) {
        PropertiesAdapter.exitContext();
    }
}

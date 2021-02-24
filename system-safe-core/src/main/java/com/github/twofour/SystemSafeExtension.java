package com.github.twofour;

import org.junit.jupiter.api.extension.*;

import java.util.Properties;

import static org.junit.jupiter.api.extension.ExtensionContext.Namespace.GLOBAL;

/**
 * @author harrydent
 */
public class SystemSafeExtension implements BeforeAllCallback,
        BeforeEachCallback,
        AfterEachCallback,
        AfterAllCallback {

    private static final String PROPERTIES_KEY = "properties";
    private static final Properties INITIAL_SYSTEM_PROPERTIES;

    static {
        INITIAL_SYSTEM_PROPERTIES = System.getProperties();
        System.setProperties(new PropertiesAdapter());
    }

    static Properties getInitialSystemProperties() {
        return INITIAL_SYSTEM_PROPERTIES;
    }

    @Override
    public void beforeAll(final ExtensionContext context) {
        enterContext(context);
    }

    @Override
    public void beforeEach(final ExtensionContext context) {
        enterContext(context);
    }

    private void enterContext(final ExtensionContext context) {
        var store = context.getStore(GLOBAL);
        var parentProperties = store.getOrDefault(PROPERTIES_KEY, Properties.class, INITIAL_SYSTEM_PROPERTIES);
        var clonedProperties = (Properties) parentProperties.clone();

        store.put(PROPERTIES_KEY, clonedProperties);
        PropertiesAdapter.setContext(clonedProperties);
    }

    @Override
    public void afterEach(final ExtensionContext context) {
        exitContext(context);
    }

    @Override
    public void afterAll(final ExtensionContext context) {
        exitContext(context);
    }

    private void exitContext(final ExtensionContext context) {
        var store = context.getStore(GLOBAL);
        store.remove(PROPERTIES_KEY);

        var parentProperties = store.getOrDefault(PROPERTIES_KEY, Properties.class, INITIAL_SYSTEM_PROPERTIES);
        PropertiesAdapter.setContext(parentProperties);
    }
}

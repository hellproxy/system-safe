package com.github.twofour;

import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;

import java.util.Properties;

import static org.junit.jupiter.api.extension.ExtensionContext.Namespace.create;

/**
 * @author harrydent
 */
public class SystemSafeExtension implements BeforeAllCallback,
                                            BeforeEachCallback,
                                            AfterEachCallback,
                                            AfterAllCallback {

    private static final Namespace NAMESPACE = create(SystemSafeExtension.class);
    private static final String PROPERTIES_KEY = "properties";
    private static final Properties INITIAL_SYSTEM_PROPERTIES;

    static {
        INITIAL_SYSTEM_PROPERTIES = System.getProperties();
        System.setProperties(new PropertiesAdapter());
    }

    static Properties getInitialSystemProperties() {
        return INITIAL_SYSTEM_PROPERTIES;
    }

    static Properties cloneInitialSystemProperties() {
        return (Properties) INITIAL_SYSTEM_PROPERTIES.clone();
    }

    @Override
    public void beforeAll(final ExtensionContext context) {
        var clonedProperties = cloneInitialSystemProperties();

        context.getStore(NAMESPACE).put(PROPERTIES_KEY, clonedProperties);
        PropertiesAdapter.addProperties(clonedProperties);
    }

    @Override
    public void beforeEach(final ExtensionContext context) {
        var store = context.getStore(NAMESPACE);
        var parentProperties = store.get(PROPERTIES_KEY, Properties.class);
        var clonedProperties = (Properties) parentProperties.clone();

        store.put(PROPERTIES_KEY, clonedProperties);
        PropertiesAdapter.addProperties(clonedProperties);
    }

    @Override
    public void afterEach(final ExtensionContext context) {
        context.getStore(NAMESPACE).remove(PROPERTIES_KEY);
        PropertiesAdapter.removeProperties();
    }

    @Override
    public void afterAll(final ExtensionContext context) {
        context.getStore(NAMESPACE).remove(PROPERTIES_KEY);
        PropertiesAdapter.removeProperties();
    }
}

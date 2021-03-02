package com.github.hellproxy;

import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;

import java.util.Properties;

import static org.junit.jupiter.api.extension.ExtensionContext.Namespace.create;

/**
 * {@code SystemSafeExtension} is a JUnit 5 Extension that prevents unintended side-effects of getting and setting
 * System Properties in tests that are executed concurrently.
 *
 * @author Harry Dent
 * @since 1.0
 */
public class SystemSafeExtension implements BeforeAllCallback,
                                            BeforeEachCallback,
                                            AfterEachCallback,
                                            AfterAllCallback {

    /**
     * The namespace used to store properties under in the {@code ExtensionContext} store.
     *
     * @see ExtensionContext#getStore(Namespace)
     */
    private static final Namespace NAMESPACE = create(SystemSafeExtension.class);


    /**
     * The key to store properties under in the {@code ExtensionContext} store.
     *
     * @see ExtensionContext#getStore(Namespace)
     */
    private static final String PROPERTIES_KEY = "properties";

    /**
     * The original JVM System Properties instance.
     */
    private static final Properties INITIAL_SYSTEM_PROPERTIES;

    static {
        INITIAL_SYSTEM_PROPERTIES = System.getProperties();
        System.setProperties(new PropertiesAdapter());
    }

    /**
     * Gets the saved initial JVM System Properties. These are still mutable and shared among all threads, so beware.
     *
     * @return an {@link Properties} instance of the System Properties that were present when this Extension was first
     * referenced.
     */
    static Properties getInitialSystemProperties() {
        return INITIAL_SYSTEM_PROPERTIES;
    }

    /**
     * Gets a copy of the saved initial JVM System Properties.
     *
     * @return a freshly copied {@link Properties} instance.
     */
    static Properties cloneInitialSystemProperties() {
        return (Properties) INITIAL_SYSTEM_PROPERTIES.clone();
    }

    /**
     * {@inheritDoc}
     * Adds a copy of the initial System Properties to the test context and the {@link PropertiesAdapter}.
     */
    @Override
    public void beforeAll(final ExtensionContext context) {
        var clonedProperties = cloneInitialSystemProperties();

        context.getStore(NAMESPACE).put(PROPERTIES_KEY, clonedProperties);
        PropertiesAdapter.addProperties(clonedProperties);
    }

    /**
     * {@inheritDoc}
     * Copies the properties provided by the outer test context and add them back to the test context. Also adds them
     * to the {@link PropertiesAdapter}.
     */
    @Override
    public void beforeEach(final ExtensionContext context) {
        var store = context.getStore(NAMESPACE);
        var parentProperties = store.get(PROPERTIES_KEY, Properties.class);
        var clonedProperties = (Properties) parentProperties.clone();

        store.put(PROPERTIES_KEY, clonedProperties);
        PropertiesAdapter.addProperties(clonedProperties);
    }

    /**
     * {@inheritDoc}
     * Removes the current head properties of the test context and the {@link PropertiesAdapter}.
     */
    @Override
    public void afterEach(final ExtensionContext context) {
        context.getStore(NAMESPACE).remove(PROPERTIES_KEY);
        PropertiesAdapter.removeProperties();
    }

    /**
     * {@inheritDoc}
     * Removes the current head properties of the test context and the {@link PropertiesAdapter}.
     */
    @Override
    public void afterAll(final ExtensionContext context) {
        context.getStore(NAMESPACE).remove(PROPERTIES_KEY);
        PropertiesAdapter.removeProperties();
    }
}

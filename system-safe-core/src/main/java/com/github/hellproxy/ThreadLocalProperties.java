package com.github.hellproxy;

import java.util.Properties;

/**
 * This class maintains a reference to a {@link PropertiesNode}, the head of a properties tree that is used to supply
 * contextual System Properties.
 *
 * @author Harry Dent
 * @since 1.0
 */
public class ThreadLocalProperties extends InheritableThreadLocal<PropertiesNode> {

    /**
     * Supplies a new, clean properties node. The result of this call is supplied to each new thread, when this object's
     * {@link ThreadLocalProperties#get()} method is first called.
     *
     * @return the initial properties node for this thread-local.
     */
    @Override
    protected PropertiesNode initialValue() {
        return new PropertiesNode();
    }

    /**
     * Deep clones the parent's properties. This is to prevent changes made to the properties by child threads leaking
     * out to the parent thread.
     *
     * @param parentValue instance from the parent thread.
     * @return a new {@link PropertiesNode} with that refers to {@code parentValue} as its parent.
     */
    @Override
    protected PropertiesNode childValue(final PropertiesNode parentValue) {
        return new PropertiesNode(parentValue);
    }

    /**
     * Gets the properties that are at the head of the current thread's properties tree.
     *
     * @return a {@link Properties} instance local to the current thread.
     */
    public Properties getProperties() {
        return get().getProperties();
    }

    /**
     * Sets the supplied properties as the new head of the current thread's properties tree.
     *
     * @param properties the properties to be added to the properties tree.
     */
    public void addProperties(final Properties properties) {
        var head = get().getHead();
        set(new PropertiesNode(head, properties));
    }

    /**
     * Removes the head of the current thread's properties tree.
     */
    public void removeProperties() {
        get().remove();
    }
}

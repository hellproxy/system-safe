package com.github.hellproxy;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

import static com.github.hellproxy.SystemSafeExtension.getInitialSystemProperties;

/**
 * A node of a thread-local properties tree. Has a link to its parent, and holds a nullable reference to a
 * {@link Properties} instance.
 *
 * @author Harry Dent
 * @see ThreadLocalProperties
 * @since 1.0
 */
public class PropertiesNode {

    private final PropertiesNode parent;
    private final AtomicReference<Properties> properties;

    /**
     * Used when initializing a new thread-local properties tree.
     */
    public PropertiesNode() {
        this(null, null);
    }

    /**
     * Used when a child thread inherits a place in its parents properties tree.
     *
     * @param parent the {@code PropertiesNode} instance held by the parent thread to whichever thread constructs this
     *               instance. Also the current head of the properties tree.
     */
    public PropertiesNode(final PropertiesNode parent) {
        this(parent, (Properties) parent.getProperties().clone());
    }

    /**
     * Used when a specific set of properties is added to the properties tree.
     *
     * @param parent     the current head of the properties tree.
     * @param properties the properties that will be held by this tree node.
     */
    public PropertiesNode(final PropertiesNode parent, final Properties properties) {
        this.parent = parent;
        this.properties = new AtomicReference<>(properties);
    }

    /**
     * Gets the first non-null properties held by this node, or by one of its ancestors, traversing up the tree. If the
     * root of the tree is reached, the original System Properties are returned.
     *
     * @return a non-null {@code Properties} instance.
     */
    public Properties getProperties() {
        var head = getHead();
        return head != null ? head.properties.get() : getInitialSystemProperties();
    }

    /**
     * Gets the first node to have a non-null properties reference, starting with this node and traversing up through
     * its ancestors.
     *
     * @return a {@code PropertiesNode} instance whose {@link #properties} reference is non-null, or  {@code null} if
     * one could not be found.
     */
    public PropertiesNode getHead() {
        if (properties.get() != null) return this;
        if (parent != null) return parent.getHead();
        return null;
    }

    /**
     * Sets the current {@link #properties} reference to {@code null}, effectively removing this node from the tree.
     */
    public void remove() {
        properties.set(null);
    }
}

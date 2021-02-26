package com.github.twofour;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

import static com.github.twofour.SystemSafeExtension.getInitialSystemProperties;

/**
 * @author harrydent
 */
public class PropertiesNode {

    private final PropertiesNode parent;
    private final AtomicReference<Properties> properties;

    public PropertiesNode() {
        this(null, null);
    }

    public PropertiesNode(final PropertiesNode parent) {
        this(parent, (Properties) parent.getProperties().clone());
    }

    public PropertiesNode(final PropertiesNode parent, final Properties properties) {
        this.parent = parent;
        this.properties = new AtomicReference<>(properties);
    }

    public Properties getProperties() {
        var head = getHead();
        return head != null ? head.properties.get() : getInitialSystemProperties();
    }

    public PropertiesNode getHead() {
        if (properties.get() != null) return this;
        if (parent != null) return parent.getHead();
        return null;
    }

    public void remove() {
        properties.set(null);
    }
}

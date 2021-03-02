package com.github.hellproxy;

import java.util.Properties;

/**
 * @author harrydent
 */
public class ThreadLocalProperties extends InheritableThreadLocal<PropertiesNode> {

    /**
     * Supplies a new, clean properties stack based off the System Properties that were set when
     * {@link SystemSafeExtension} was loaded. The result of this call is supplied to each new thread, when this
     * object's {@link ThreadLocalProperties#get()} method is first called.
     *
     * @return the initial properties stack for this thread-local.
     */
    @Override
    protected PropertiesNode initialValue() {
        return new PropertiesNode();
    }

    /**
     * Deep clones the parent's stack. This is to prevent changes made to the properties by child threads leaking out
     * to the parent thread.
     *
     * @param parentValue the current properties stack of the parent thread
     * @return a deep clone of the parent's properties stack
     */
    @Override
    protected PropertiesNode childValue(PropertiesNode parentValue) {
        return new PropertiesNode(parentValue);
    }

    public Properties getProperties() {
        return get().getProperties();
    }

    public void addProperties(final Properties properties) {
        var head = get().getHead();
        set(new PropertiesNode(head, properties));
    }

    public void removeProperties() {
        get().remove();
    }
}

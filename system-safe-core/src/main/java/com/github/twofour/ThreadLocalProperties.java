package com.github.twofour;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

import static com.github.twofour.SystemSafeExtension.getInitialSystemProperties;

/**
 * @author harrydent
 */
public class ThreadLocalProperties extends InheritableThreadLocal<AtomicReference<Properties>> {

    /**
     * Supplies a new, clean properties stack based off the System Properties that were set when
     * {@link SystemSafeExtension} was loaded. The result of this call is supplied to each new thread, when this
     * object's {@link ThreadLocalProperties#get()} method is first called.
     *
     * @return the initial properties stack for this thread-local.
     */
    @Override
    protected AtomicReference<Properties> initialValue() {
        var properties = (Properties) getInitialSystemProperties().clone();
        return new AtomicReference<>(properties);
    }

    /**
     * Deep clones the parent's stack. This is to prevent changes made to the properties by child threads leaking out
     * to the parent thread.
     *
     * @param parentValue the current properties stack of the parent thread
     * @return a deep clone of the parent's properties stack
     */
    @Override
    protected AtomicReference<Properties> childValue(AtomicReference<Properties> parentValue) {
        var propertiesClone = (Properties) parentValue.get().clone();
        return new AtomicReference<>(propertiesClone);
    }
}

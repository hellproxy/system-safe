package com.github.twofour;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Properties;

import static com.github.twofour.SystemSafeExtension.getInitialSystemProperties;
import static java.util.stream.Collectors.toCollection;

/**
 * @author harrydent
 */
public class ThreadLocalPropertiesStack extends InheritableThreadLocal<Deque<Properties>> {

    /**
     * Supplies a new, clean properties stack based off the System Properties that were set when
     * {@link SystemSafeExtension} was loaded. The result of this call is supplied to each new thread, when this
     * object's {@link ThreadLocalPropertiesStack#get()} method is first called.
     *
     * @return the initial properties stack for this thread-local.
     */
    @Override
    protected Deque<Properties> initialValue() {
        Properties properties = (Properties) getInitialSystemProperties().clone();

        Deque<Properties> stack = new LinkedList<>();
        stack.add(properties);

        return stack;
    }

    /**
     * Deep clones the parent's stack. This is to prevent changes made to the properties by child threads leaking out
     * to the parent thread.
     *
     * @param parentStack the current properties stack of the parent thread
     * @return a deep clone of the parent's properties stack
     */
    @Override
    protected Deque<Properties> childValue(Deque<Properties> parentStack) {
        return parentStack.stream()
            .map(Properties::clone)
            .map(Properties.class::cast)
            .collect(toCollection(LinkedList::new));
    }
}

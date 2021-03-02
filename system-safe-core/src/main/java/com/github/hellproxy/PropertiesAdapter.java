package com.github.hellproxy;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

/**
 * {@code @PropertiesAdapter} replaces the {@link Properties} instance in System Properties. It delegates interactions
 * with System Properties to a different {@link ThreadLocalProperties} object, depending on what thread the interaction
 * happened in.
 *
 * @author Harry Dent
 * @since 1.0
 */
public class PropertiesAdapter extends Properties {

    private static final ThreadLocalProperties THREAD_LOCAL_PROPERTIES = new ThreadLocalProperties();

    /**
     * Adds the provided properties to the thread-local properties tree. The added properties will be returned by
     * {@code System.getProperties()} until further interactions with the same properties tree instance.
     *
     * @param properties the {@link Properties} to add to the properties tree.
     */
    public static void addProperties(final Properties properties) {
        THREAD_LOCAL_PROPERTIES.addProperties(properties);
    }

    /**
     * Clears the current thread-local properties tree head. The next deepest {@link Properties} instance on the tree
     * will be returned by {@code System.getProperties()} going forwards.
     */
    public static void removeProperties() {
        THREAD_LOCAL_PROPERTIES.removeProperties();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized Object setProperty(String key, String value) {
        return getLocalProperties().setProperty(key, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void load(Reader reader) throws IOException {
        getLocalProperties().load(reader);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void load(InputStream inStream) throws IOException {
        getLocalProperties().load(inStream);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(OutputStream out, String comments) {
        getLocalProperties().save(out, comments);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void store(Writer writer, String comments) throws IOException {
        getLocalProperties().store(writer, comments);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void store(OutputStream out, String comments) throws IOException {
        getLocalProperties().store(out, comments);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void loadFromXML(InputStream in) throws IOException {
        getLocalProperties().loadFromXML(in);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void storeToXML(OutputStream os, String comment) throws IOException {
        getLocalProperties().storeToXML(os, comment);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void storeToXML(OutputStream os, String comment, String encoding) throws IOException {
        getLocalProperties().storeToXML(os, comment, encoding);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void storeToXML(OutputStream os, String comment, Charset charset) throws IOException {
        getLocalProperties().storeToXML(os, comment, charset);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getProperty(String key) {
        return getLocalProperties().getProperty(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getProperty(String key, String defaultValue) {
        return getLocalProperties().getProperty(key, defaultValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Enumeration<?> propertyNames() {
        return getLocalProperties().propertyNames();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<String> stringPropertyNames() {
        return getLocalProperties().stringPropertyNames();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void list(PrintStream out) {
        getLocalProperties().list(out);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void list(PrintWriter out) {
        getLocalProperties().list(out);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return getLocalProperties().size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return getLocalProperties().isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Enumeration<Object> keys() {
        return getLocalProperties().keys();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Enumeration<Object> elements() {
        return getLocalProperties().elements();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(Object value) {
        return getLocalProperties().contains(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsValue(Object value) {
        return getLocalProperties().containsValue(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsKey(Object key) {
        return getLocalProperties().containsKey(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object get(Object key) {
        return getLocalProperties().get(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized Object put(Object key, Object value) {
        return getLocalProperties().put(key, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized Object remove(Object key) {
        return getLocalProperties().remove(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void putAll(Map<?, ?> t) {
        getLocalProperties().putAll(t);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void clear() {
        getLocalProperties().clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized String toString() {
        return getLocalProperties().toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Object> keySet() {
        return getLocalProperties().keySet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Object> values() {
        return getLocalProperties().values();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Map.Entry<Object, Object>> entrySet() {
        return getLocalProperties().entrySet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized boolean equals(Object o) {
        return getLocalProperties().equals(o);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized int hashCode() {
        return getLocalProperties().hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getOrDefault(Object key, Object defaultValue) {
        return getLocalProperties().getOrDefault(key, defaultValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void forEach(BiConsumer<? super Object, ? super Object> action) {
        getLocalProperties().forEach(action);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void replaceAll(BiFunction<? super Object, ? super Object, ?> function) {
        getLocalProperties().replaceAll(function);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized Object putIfAbsent(Object key, Object value) {
        return getLocalProperties().putIfAbsent(key, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized boolean remove(Object key, Object value) {
        return getLocalProperties().remove(key, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized boolean replace(Object key, Object oldValue, Object newValue) {
        return getLocalProperties().replace(key, oldValue, newValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized Object replace(Object key, Object value) {
        return getLocalProperties().replace(key, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized Object computeIfAbsent(Object key, Function<? super Object, ?> mappingFunction) {
        return getLocalProperties().computeIfAbsent(key, mappingFunction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized Object computeIfPresent(Object key,
                                                BiFunction<? super Object, ? super Object, ?> remappingFunction) {
        return getLocalProperties().computeIfPresent(key, remappingFunction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized Object compute(Object key, BiFunction<? super Object, ? super Object, ?> remappingFunction) {
        return getLocalProperties().compute(key, remappingFunction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized Object merge(Object key,
                                     Object value,
                                     BiFunction<? super Object, ? super Object, ?> remappingFunction) {
        return getLocalProperties().merge(key, value, remappingFunction);
    }

    /**
     * {@inheritDoc}
     * Super implementation is a no-op, so no need to delegate this call to a thread-local {@code Properties} instance
     */
    @Override
    protected void rehash() {
        /* no-op */
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized Object clone() {
        return getLocalProperties().clone();
    }

    private Properties getLocalProperties() {
        return requireNonNull(THREAD_LOCAL_PROPERTIES.getProperties());
    }
}

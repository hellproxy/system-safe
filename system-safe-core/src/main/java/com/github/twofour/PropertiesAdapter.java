package com.github.twofour;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

/**
 * @author harrydent
 */
public class PropertiesAdapter extends Properties {

    private static final ThreadLocalProperties THREAD_LOCAL_PROPERTIES = new ThreadLocalProperties();

    public static void addProperties(final Properties properties) {
        THREAD_LOCAL_PROPERTIES.addProperties(properties);
    }

    public static void removeProperties() {
        THREAD_LOCAL_PROPERTIES.removeProperties();
    }

    @Override
    public synchronized Object setProperty(String key, String value) {
        return getLocalProperties().setProperty(key, value);
    }

    @Override
    public synchronized void load(Reader reader) throws IOException {
        getLocalProperties().load(reader);
    }

    @Override
    public synchronized void load(InputStream inStream) throws IOException {
        getLocalProperties().load(inStream);
    }

    @Override
    public void save(OutputStream out, String comments) {
        getLocalProperties().save(out, comments);
    }

    @Override
    public void store(Writer writer, String comments) throws IOException {
        getLocalProperties().store(writer, comments);
    }

    @Override
    public void store(OutputStream out, String comments) throws IOException {
        getLocalProperties().store(out, comments);
    }

    @Override
    public synchronized void loadFromXML(InputStream in) throws IOException {
        getLocalProperties().loadFromXML(in);
    }

    @Override
    public void storeToXML(OutputStream os, String comment) throws IOException {
        getLocalProperties().storeToXML(os, comment);
    }

    @Override
    public void storeToXML(OutputStream os, String comment, String encoding) throws IOException {
        getLocalProperties().storeToXML(os, comment, encoding);
    }

    @Override
    public void storeToXML(OutputStream os, String comment, Charset charset) throws IOException {
        getLocalProperties().storeToXML(os, comment, charset);
    }

    @Override
    public String getProperty(String key) {
        return getLocalProperties().getProperty(key);
    }

    @Override
    public String getProperty(String key, String defaultValue) {
        return getLocalProperties().getProperty(key, defaultValue);
    }

    @Override
    public Enumeration<?> propertyNames() {
        return getLocalProperties().propertyNames();
    }

    @Override
    public Set<String> stringPropertyNames() {
        return getLocalProperties().stringPropertyNames();
    }

    @Override
    public void list(PrintStream out) {
        getLocalProperties().list(out);
    }

    @Override
    public void list(PrintWriter out) {
        getLocalProperties().list(out);
    }

    @Override
    public int size() {
        return getLocalProperties().size();
    }

    @Override
    public boolean isEmpty() {
        return getLocalProperties().isEmpty();
    }

    @Override
    public Enumeration<Object> keys() {
        return getLocalProperties().keys();
    }

    @Override
    public Enumeration<Object> elements() {
        return getLocalProperties().elements();
    }

    @Override
    public boolean contains(Object value) {
        return getLocalProperties().contains(value);
    }

    @Override
    public boolean containsValue(Object value) {
        return getLocalProperties().containsValue(value);
    }

    @Override
    public boolean containsKey(Object key) {
        return getLocalProperties().containsKey(key);
    }

    @Override
    public Object get(Object key) {
        return getLocalProperties().get(key);
    }

    @Override
    public synchronized Object put(Object key, Object value) {
        return getLocalProperties().put(key, value);
    }

    @Override
    public synchronized Object remove(Object key) {
        return getLocalProperties().remove(key);
    }

    @Override
    public synchronized void putAll(Map<?, ?> t) {
        getLocalProperties().putAll(t);
    }

    @Override
    public synchronized void clear() {
        getLocalProperties().clear();
    }

    @Override
    public synchronized String toString() {
        return getLocalProperties().toString();
    }

    @Override
    public Set<Object> keySet() {
        return getLocalProperties().keySet();
    }

    @Override
    public Collection<Object> values() {
        return getLocalProperties().values();
    }

    @Override
    public Set<Map.Entry<Object, Object>> entrySet() {
        return getLocalProperties().entrySet();
    }

    @Override
    public synchronized boolean equals(Object o) {
        return getLocalProperties().equals(o);
    }

    @Override
    public synchronized int hashCode() {
        return getLocalProperties().hashCode();
    }

    @Override
    public Object getOrDefault(Object key, Object defaultValue) {
        return getLocalProperties().getOrDefault(key, defaultValue);
    }

    @Override
    public synchronized void forEach(BiConsumer<? super Object, ? super Object> action) {
        getLocalProperties().forEach(action);
    }

    @Override
    public synchronized void replaceAll(BiFunction<? super Object, ? super Object, ?> function) {
        getLocalProperties().replaceAll(function);
    }

    @Override
    public synchronized Object putIfAbsent(Object key, Object value) {
        return getLocalProperties().putIfAbsent(key, value);
    }

    @Override
    public synchronized boolean remove(Object key, Object value) {
        return getLocalProperties().remove(key, value);
    }

    @Override
    public synchronized boolean replace(Object key, Object oldValue, Object newValue) {
        return getLocalProperties().replace(key, oldValue, newValue);
    }

    @Override
    public synchronized Object replace(Object key, Object value) {
        return getLocalProperties().replace(key, value);
    }

    @Override
    public synchronized Object computeIfAbsent(Object key, Function<? super Object, ?> mappingFunction) {
        return getLocalProperties().computeIfAbsent(key, mappingFunction);
    }

    @Override
    public synchronized Object computeIfPresent(Object key,
                                                BiFunction<? super Object, ? super Object, ?> remappingFunction) {
        return getLocalProperties().computeIfPresent(key, remappingFunction);
    }

    @Override
    public synchronized Object compute(Object key, BiFunction<? super Object, ? super Object, ?> remappingFunction) {
        return getLocalProperties().compute(key, remappingFunction);
    }

    @Override
    public synchronized Object merge(Object key,
                                     Object value,
                                     BiFunction<? super Object, ? super Object, ?> remappingFunction) {
        return getLocalProperties().merge(key, value, remappingFunction);
    }

    @Override
    protected void rehash() {
        /* no-op */
    }

    @Override
    public synchronized Object clone() {
        return getLocalProperties().clone();
    }

    private Properties getLocalProperties() {
        return requireNonNull(THREAD_LOCAL_PROPERTIES.getProperties());
    }
}

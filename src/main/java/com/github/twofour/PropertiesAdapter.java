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

    private static final ThreadLocalPropertiesStack STACK = new ThreadLocalPropertiesStack();

    public static void enterContext() {
        var local = STACK.get();
        local.push((Properties) requireNonNull(local.peek()).clone());
    }

    public static void exitContext() {
        var local = STACK.get();
        local.pop();
    }

    @Override
    public synchronized Object setProperty(String key, String value) {
        return getStackHead().setProperty(key, value);
    }

    @Override
    public synchronized void load(Reader reader) throws IOException {
        getStackHead().load(reader);
    }

    @Override
    public synchronized void load(InputStream inStream) throws IOException {
        getStackHead().load(inStream);
    }

    @Override
    public void save(OutputStream out, String comments) {
        getStackHead().save(out, comments);
    }

    @Override
    public void store(Writer writer, String comments) throws IOException {
        getStackHead().store(writer, comments);
    }

    @Override
    public void store(OutputStream out, String comments) throws IOException {
        getStackHead().store(out, comments);
    }

    @Override
    public synchronized void loadFromXML(InputStream in) throws IOException, InvalidPropertiesFormatException {
        getStackHead().loadFromXML(in);
    }

    @Override
    public void storeToXML(OutputStream os, String comment) throws IOException {
        getStackHead().storeToXML(os, comment);
    }

    @Override
    public void storeToXML(OutputStream os, String comment, String encoding) throws IOException {
        getStackHead().storeToXML(os, comment, encoding);
    }

    @Override
    public void storeToXML(OutputStream os, String comment, Charset charset) throws IOException {
        getStackHead().storeToXML(os, comment, charset);
    }

    @Override
    public String getProperty(String key) {
        return getStackHead().getProperty(key);
    }

    @Override
    public String getProperty(String key, String defaultValue) {
        return getStackHead().getProperty(key, defaultValue);
    }

    @Override
    public Enumeration<?> propertyNames() {
        return getStackHead().propertyNames();
    }

    @Override
    public Set<String> stringPropertyNames() {
        return getStackHead().stringPropertyNames();
    }

    @Override
    public void list(PrintStream out) {
        getStackHead().list(out);
    }

    @Override
    public void list(PrintWriter out) {
        getStackHead().list(out);
    }

    @Override
    public int size() {
        return getStackHead().size();
    }

    @Override
    public boolean isEmpty() {
        return getStackHead().isEmpty();
    }

    @Override
    public Enumeration<Object> keys() {
        return getStackHead().keys();
    }

    @Override
    public Enumeration<Object> elements() {
        return getStackHead().elements();
    }

    @Override
    public boolean contains(Object value) {
        return getStackHead().contains(value);
    }

    @Override
    public boolean containsValue(Object value) {
        return getStackHead().containsValue(value);
    }

    @Override
    public boolean containsKey(Object key) {
        return getStackHead().containsKey(key);
    }

    @Override
    public Object get(Object key) {
        return getStackHead().get(key);
    }

    @Override
    public synchronized Object put(Object key, Object value) {
        return getStackHead().put(key, value);
    }

    @Override
    public synchronized Object remove(Object key) {
        return getStackHead().remove(key);
    }

    @Override
    public synchronized void putAll(Map<?, ?> t) {
        getStackHead().putAll(t);
    }

    @Override
    public synchronized void clear() {
        getStackHead().clear();
    }

    @Override
    public synchronized String toString() {
        return getStackHead().toString();
    }

    @Override
    public Set<Object> keySet() {
        return getStackHead().keySet();
    }

    @Override
    public Collection<Object> values() {
        return getStackHead().values();
    }

    @Override
    public Set<Map.Entry<Object, Object>> entrySet() {
        return getStackHead().entrySet();
    }

    @Override
    public synchronized boolean equals(Object o) {
        return getStackHead().equals(o);
    }

    @Override
    public synchronized int hashCode() {
        return getStackHead().hashCode();
    }

    @Override
    public Object getOrDefault(Object key, Object defaultValue) {
        return getStackHead().getOrDefault(key, defaultValue);
    }

    @Override
    public synchronized void forEach(BiConsumer<? super Object, ? super Object> action) {
        getStackHead().forEach(action);
    }

    @Override
    public synchronized void replaceAll(BiFunction<? super Object, ? super Object, ?> function) {
        getStackHead().replaceAll(function);
    }

    @Override
    public synchronized Object putIfAbsent(Object key, Object value) {
        return getStackHead().putIfAbsent(key, value);
    }

    @Override
    public synchronized boolean remove(Object key, Object value) {
        return getStackHead().remove(key, value);
    }

    @Override
    public synchronized boolean replace(Object key, Object oldValue, Object newValue) {
        return getStackHead().replace(key, oldValue, newValue);
    }

    @Override
    public synchronized Object replace(Object key, Object value) {
        return getStackHead().replace(key, value);
    }

    @Override
    public synchronized Object computeIfAbsent(Object key, Function<? super Object, ?> mappingFunction) {
        return getStackHead().computeIfAbsent(key, mappingFunction);
    }

    @Override
    public synchronized Object computeIfPresent(Object key,
                                                BiFunction<? super Object, ? super Object, ?> remappingFunction) {
        return getStackHead().computeIfPresent(key, remappingFunction);
    }

    @Override
    public synchronized Object compute(Object key, BiFunction<? super Object, ? super Object, ?> remappingFunction) {
        return getStackHead().compute(key, remappingFunction);
    }

    @Override
    public synchronized Object merge(Object key,
                                     Object value,
                                     BiFunction<? super Object, ? super Object, ?> remappingFunction) {
        return getStackHead().merge(key, value, remappingFunction);
    }

    @Override
    protected void rehash() {
        /* no-op */
    }

    @Override
    public synchronized Object clone() {
        return getStackHead().clone();
    }

    private Properties getStackHead() {
        return requireNonNull(STACK.get().peek());
    }
}

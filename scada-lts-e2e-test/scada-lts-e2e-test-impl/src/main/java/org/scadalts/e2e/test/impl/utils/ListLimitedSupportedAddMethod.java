package org.scadalts.e2e.test.impl.utils;

import java.util.*;

public class ListLimitedSupportedAddMethod<T> implements List<T> {

    private final LinkedList<T> data;
    private final LinkedList<String> addingIndicators;
    private final int limit;

    public ListLimitedSupportedAddMethod(int limit) {
        this.data = new LinkedList<>();
        this.addingIndicators = new LinkedList<>();
        this.limit = limit;
    }

    public boolean addUnique(T o) {
        return addUnique(o, String.valueOf(o));
    }

    @Override
    public boolean add(T o) {
        addingIndicators.add(String.valueOf(o));
        return data.add(o);
    }

    public boolean addUnique(T o, String addingIndicator) {
        if(addingIndicators.isEmpty()) {
            _addFirst(o, addingIndicator);
            return true;
        }
        if(!addingIndicators.getFirst().equals(addingIndicator)) {
            _addFirst(o, addingIndicator);
            _clearToLimit();
            return true;
        }
        return false;
    }

    public boolean addAllUnique(Collection<? extends T> c) {
        boolean added = true;
        for(T value: c) {
            added = added && addUnique(value);
            _clearToLimit();
        }
        return added;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean added = true;
        for(T value: c) {
            added = added && add(value);
            _clearToLimit();
        }
        return added;
    }

    public boolean addAll(List<? extends T> c, List<String> indicatiors) {
        if(c.size() != indicatiors.size())
            throw new IllegalArgumentException();
        addingIndicators.addAll(indicatiors);
        boolean added = data.addAll(c);
        _clearToLimit();
        return added;
    }

    public boolean addAllUnique(List<? extends T> c, List<String> indicatiors) {
        if(c.size() != indicatiors.size())
            throw new IllegalArgumentException();
        boolean added = true;
        for(int i = 0; i < c.size(); i++) {
            added = added && addUnique(c.get(i), indicatiors.get(i));
            _clearToLimit();
        }
        return added;
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return data.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return data.iterator();
    }

    @Override
    public Object[] toArray() {
        return data.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return data.toArray(a);
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return data.containsAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T get(int index) {
        return data.get(index);
    }

    @Override
    public T remove(int index) {
        return _remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return data.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return data.lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        return data.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return data.listIterator(index);
    }

    @Override
    public boolean equals(Object o) {
        return data.equals(o);
    }

    @Override
    public void clear() {
        _clear();
    }

    @Override
    public int hashCode() {
        return data.hashCode();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T set(int index, T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return data.toString();
    }

    public T getLast() {
        return data.getLast();
    }

    public T getFirst() {
        return data.getFirst();
    }


    private void _clearToLimit() {
        if(data.size() > limit) {
            do {
                _removeLast();
            } while (data.size() > limit);
        }
    }

    private void _clear() {
        data.clear();
        addingIndicators.clear();
    }

    private T _removeLast() {
        addingIndicators.removeLast();
        return data.removeLast();
    }

    private T _remove(int index) {
        addingIndicators.remove(index);
        return data.remove(index);
    }

    private void _addFirst(T o, String addingIndicator) {
        data.addFirst(o);
        addingIndicators.addFirst(addingIndicator);
    }

}

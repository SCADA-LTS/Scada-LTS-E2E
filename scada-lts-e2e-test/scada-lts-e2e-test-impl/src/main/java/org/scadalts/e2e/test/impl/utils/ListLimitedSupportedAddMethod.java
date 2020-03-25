package org.scadalts.e2e.test.impl.utils;

import java.util.*;

public class ListLimitedSupportedAddMethod<T> implements List<T> {

    private final LinkedList<T> linkedList;
    private final int limit;

    public ListLimitedSupportedAddMethod(int limit) {
        this.linkedList = new LinkedList<>();
        this.limit = limit;
    }

    @Override
    public boolean add(T o) {
        linkedList.addFirst(o);
        _clear();
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        linkedList.addAll(c);
        _clear();
        return true;
    }

    private void _clear() {
        if(linkedList.size() > limit) {
            do {
                linkedList.removeLast();
            } while (linkedList.size() > limit);
        }
    }

    @Override
    public int size() {
        return linkedList.size();
    }

    @Override
    public boolean isEmpty() {
        return linkedList.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return linkedList.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return linkedList.iterator();
    }

    @Override
    public Object[] toArray() {
        return linkedList.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return linkedList.toArray(a);
    }

    @Override
    public boolean remove(Object o) {
        return linkedList.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return linkedList.containsAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return linkedList.removeAll(c);
    }

    @Override
    public T get(int index) {
        return linkedList.get(index);
    }

    @Override
    public T remove(int index) {
        return linkedList.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return linkedList.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return linkedList.lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        return linkedList.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return linkedList.listIterator(index);
    }

    @Override
    public boolean equals(Object o) {
        return linkedList.equals(o);
    }

    @Override
    public void clear() {
        linkedList.clear();
    }

    @Override
    public int hashCode() {
        return linkedList.hashCode();
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
        return linkedList.toString();
    }
}

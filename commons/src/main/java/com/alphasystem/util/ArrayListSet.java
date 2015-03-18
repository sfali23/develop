/**
 * 
 */
package com.alphasystem.util;

import static java.lang.String.format;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import java.util.Set;

/**
 * @author sali
 * 
 */
public class ArrayListSet<E> implements ListSet<E>, Serializable, Cloneable,
		RandomAccess {

	private static final long serialVersionUID = -8712721271816220298L;

	private List<E> internalList;

	private Set<E> internalSet;

	public ArrayListSet() {
		this(10);
	}

	public ArrayListSet(int initialCapacity) {
		internalList = new ArrayList<E>(initialCapacity);
		internalSet = new LinkedHashSet<E>(initialCapacity);
	}

	public ArrayListSet(Collection<? extends E> c) {
		internalList = new ArrayList<E>(c);
		internalSet = new LinkedHashSet<E>(c);
	}

	@Override
	public boolean add(E e) {
		boolean result = internalSet.add(e);
		if (result) {
			internalList.add(e);
		}
		return result;
	}

	@Override
	public void add(int index, E element) {
		if (index < 0 || index > size()) {
			throw new IndexOutOfBoundsException(format(
					"Index out of range {%s}", index));
		}
		boolean result = internalSet.add(element);
		if (result) {
			internalList.add(index, element);
		}
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		if (c == null) {
			return false;
		}
		boolean result = false;
		for (E e : c) {
			if (internalSet.add(e)) {
				result = true;
				internalList.add(e);
			}
		}
		return result;
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		if (index < 0 || index > size()) {
			throw new IndexOutOfBoundsException(format(
					"Index out of range {%s}", index));
		}
		boolean result = false;
		int i = index;
		for (E e : c) {
			if (internalSet.add(e)) {
				result = true;
				internalList.add(i, e);
				i++;
			}
		}
		return result;
	}

	@Override
	public void clear() {
		internalSet.clear();
		internalList.clear();
	}

	@Override
	public boolean contains(Object o) {
		return internalSet.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return internalSet.containsAll(c);
	}

	@Override
	public E get(int index) {
		return internalList.get(index);
	}

	@Override
	public int indexOf(Object o) {
		return internalList.indexOf(o);
	}

	@Override
	public boolean isEmpty() {
		return internalList.isEmpty();
	}

	@Override
	public Iterator<E> iterator() {
		return internalList.iterator();
	}

	@Override
	public int lastIndexOf(Object o) {
		return internalList.lastIndexOf(o);
	}

	@Override
	public ListIterator<E> listIterator() {
		return internalList.listIterator();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return internalList.listIterator(index);
	}

	@Override
	public E remove(int index) {
		E e = internalList.remove(index);
		if (e != null) {
			internalSet.remove(e);
		}
		return e;
	}

	@Override
	public boolean remove(Object o) {
		boolean result = internalSet.remove(o);
		if (result) {
			internalList.remove(o);
		}
		return result;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean result = internalSet.removeAll(c);
		if (result) {
			internalList.retainAll(c);
		}
		return result;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		boolean result = internalSet.retainAll(c);
		if (result) {
			internalList.retainAll(c);
		}
		return false;
	}

	@Override
	public E set(int index, E element) {
		if (index < 0 || index > size()) {
			throw new IndexOutOfBoundsException(format(
					"Index out of range {%s}", index));
		}
		E e = null;
		if (internalSet.add(e)) {
			e = internalList.set(index, element);
		}
		return e;
	}

	@Override
	public int size() {
		return internalList.size();
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		return internalList.subList(fromIndex, toIndex);
	}

	@Override
	public Object[] toArray() {
		return internalList.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return internalList.toArray(a);
	}

}

/**
 * 
 */
package com.alphasystem.util;

import static java.util.Collections.sort;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * @author sali
 * 
 */
public class SortedList<E> extends ArrayList<E> {

	private static final long serialVersionUID = -7168342928389588019L;

	public static void main(String[] args) {
		List<Integer> list = new SortedList<Integer>(new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}
		});

		list.add(1);
		list.add(3);
		list.add(2);
		System.out.println(list);
		list.remove(1);
		System.out.println(list);
	}

	private Comparator<E> comparator;

	/**
	 * @param c
	 */
	public SortedList(Collection<? extends E> c, Comparator<E> comparator) {
		super(c);
		this.comparator = comparator;
		sort(this, comparator);
	}

	public SortedList(Comparator<E> comparator) {
		this.comparator = comparator;
	}

	public SortedList(int initialCapacity, Comparator<E> comparator) {
		super(initialCapacity);
		this.comparator = comparator;
	}
	
	public boolean add(E e) {
		boolean added = super.add(e);
		sort(this, comparator);
		return added;
	}
	
	public void add(int index, E element) {
		add(element);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		boolean added= super.addAll(c);
		sort(this, comparator);
		return added;
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		return addAll(c);
	}

}

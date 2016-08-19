/**
 * 
 */
package com.flatironschool.javacs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Provides sorting algorithms.
 *
 */
public class ListSorter<T> {

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void insertionSort(List<T> list, Comparator<T> comparator) {

		for (int i = 1; i < list.size(); i++) {
			T elt_i = list.get(i);
			int j = i;
			while (j > 0) {
				T elt_j = list.get(j - 1);
				if (comparator.compare(elt_i, elt_j) >= 0) {
					break;
				}
				list.set(j, elt_j);
				j--;
			}
			list.set(j, elt_i);
		}
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void mergeSortInPlace(List<T> list, Comparator<T> comparator) {
		List<T> sorted = mergeSort(list, comparator);
		list.clear();
		list.addAll(sorted);
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * Returns a list that might be new.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public List<T> mergeSort(List<T> list, Comparator<T> comparator) {
	
		List<T> mergedList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			mergedList.add (list.get(i));
		} 
		doMergeSort(list, mergedList, 0, list.size() - 1, comparator);
		
		List<T> sortedList = new ArrayList<>();
		sortedList.addAll(list);
		return sortedList;
	}

	private void doMergeSort(List<T> list, List<T> mergedList, int lowerIndex, int higherIndex, Comparator<T> comparator) {

		if (lowerIndex < higherIndex) {
			int middle = lowerIndex + (higherIndex - lowerIndex) / 2;
			// Below step sorts the left side of the array
			doMergeSort(list, mergedList, lowerIndex, middle, comparator);
			// Below step sorts the right side of the array
			doMergeSort(list, mergedList, middle + 1, higherIndex, comparator);
			// Now merge both sides
			mergeParts(list, lowerIndex, middle, higherIndex, comparator);
		}
	}

	private void mergeParts(List<T> list, int lowerIndex, int middle, int higherIndex, Comparator<T> comparator) {

		
		int i = lowerIndex;
		int j = middle + 1;
		int k = lowerIndex;
		
		List<T> mergedList = new ArrayList<>();
		for (int xx = 0; xx < list.size(); xx++) {
			mergedList.add (list.get(xx));
		} 
		while (i <= middle && j <= higherIndex) {
			if (comparator.compare(mergedList.get(i), mergedList.get(j)) <= 0) {
				list.set(k, mergedList.get(i));
				//array[k] = tempMergArr[i];
				i++;
			} else {
				list.set(k, mergedList.get(j));
			//	array[k] = tempMergArr[j];
				j++;
			}
			k++;
		}
		while (i <= middle) {
			list.set(k, mergedList.get(i));
		//	array[k] = tempMergArr[i];
			k++;
			i++;
		}

	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void heapSort(List<T> list, Comparator<T> comparator) {
		PriorityQueue<T> heap = new PriorityQueue<T>(list.size(), comparator);
		heap.addAll(list);
		list.clear();
		while (!heap.isEmpty()) {
			list.add(heap.poll());
		}
	}

	/**
	 * Returns the largest `k` elements in `list` in ascending order.
	 * 
	 * @param k
	 * @param list
	 * @param comparator
	 * @return
	 * @return
	 */
	public List<T> topK(int k, List<T> list, Comparator<T> comparator) {
		PriorityQueue<T> heap = new PriorityQueue<T>(list.size(), comparator);
		for (T obj: list) {
			if (heap.size() < k) {
				heap.offer(obj);
				continue;
			}

			int c = comparator.compare(obj, heap.peek());
			if (c > 0) {
				heap.poll();
				heap.offer(obj);
			}
		}

		List<T> retVal = new ArrayList<T>();
		while (!heap.isEmpty()) {
			retVal.add(heap.poll());
		}
		return retVal;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));

		Comparator<Integer> comparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer n, Integer m) {
				return n.compareTo(m);
			}
		};

		ListSorter<Integer> sorter = new ListSorter<Integer>();
		sorter.insertionSort(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.mergeSortInPlace(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.heapSort(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(6, 3, 5, 8, 1, 4, 2, 7));
		List<Integer> queue = sorter.topK(4, list, comparator);
		System.out.println(queue);
	}
}

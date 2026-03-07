package com.rta.engine.algorithms.sorting;

/**
 * Bubble Sort Algorithm Implementation
 *
 * Time Complexity: O(n²) - worst and average case
 * Space Complexity: O(1) - in-place sorting
 *
 * Bubble sort repeatedly steps through the list, compares adjacent elements,
 * and swaps them if they are in the wrong order. The largest element "bubbles"
 * to the end of the list in each pass.
 */
public class BubbleSort {

    /**
     * Sorts an array using the bubble sort algorithm
     *
     * @param arr the array to be sorted
     */
    public void sort(int[] arr) {
        // TODO: Implement bubble sort
        // 1. Handle null/empty array
        // 2. Use nested loops to compare adjacent elements
        // 3. Swap if elements are in wrong order
        // 4. Optimize with a swapped flag to detect already sorted arrays
    }

    /**
     * Gets the description of the algorithm
     *
     * @return algorithm description
     */
    public String getDescription() {
        return "Bubble Sort: A simple sorting algorithm that repeatedly steps through the list, " +
               "compares adjacent elements and swaps them if they are in wrong order.";
    }
}



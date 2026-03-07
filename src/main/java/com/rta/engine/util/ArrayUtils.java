package com.rta.engine.util;

import java.util.Random;

/**
 * ArrayUtils - Utility class for array operations
 *
 * Provides common utility methods for working with arrays
 * such as generating random arrays, printing, and verification.
 */
public class ArrayUtils {

    private static final Random random = new Random();

    /**
     * Generates a random integer array
     *
     * @param size size of the array
     * @return random array
     */
    public static int[] generateRandomArray(int size) {
        // TODO: Generate random array with default range
        return generateRandomArray(size, 0, 1000);
    }

    /**
     * Generates a random integer array within a specific range
     *
     * @param size size of the array
     * @param min minimum value (inclusive)
     * @param max maximum value (exclusive)
     * @return random array
     */
    public static int[] generateRandomArray(int size, int min, int max) {
        // TODO: Implement random array generation
        // 1. Create array of given size
        // 2. Fill with random values between min and max
        return new int[size];
    }

    /**
     * Checks if an array is sorted in ascending order
     *
     * @param arr array to check
     * @return true if sorted, false otherwise
     */
    public static boolean isSorted(int[] arr) {
        // TODO: Check if array is sorted in ascending order
        // 1. Handle null/empty array
        // 2. Compare each element with next
        // 3. Return false if any element is greater than next
        return true;
    }

    /**
     * Prints array elements
     *
     * @param arr array to print
     */
    public static void printArray(int[] arr) {
        // TODO: Print array in format: [elem1, elem2, elem3, ...]
        // Handle null case
    }

    /**
     * Creates a copy of the array
     *
     * @param arr array to copy
     * @return copy of the array
     */
    public static int[] copyArray(int[] arr) {
        // TODO: Create and return a copy of the array
        return null;
    }
}



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
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(max - min + 1) + min;
        }
        return array;
    }

    /**
     * Checks if an array is sorted in ascending order
     *
     * @param arr array to check
     * @return true if sorted, false otherwise
     */
    public static boolean isSorted(int[] arr) {
        if(arr == null || arr.length <= 1) {
            return true; // An empty array or single element is considered sorted
        }
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) {
                return false; // Found an element that is smaller than the previous one
            }
        }
        return true;
    }

    /**
     * Prints array elements
     *
     * @param arr array to print
     */
    public static void printArray(int[] arr) {
        if (arr == null) {return;}
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) {
                System.out.print(", ");
            }
        }
    }

    /**
     * Creates a copy of the array
     *
     * @param arr array to copy
     * @return copy of the array
     */
    public static int[] copyArray(int[] arr) {
        int[] copy = new int[arr.length];
        System.arraycopy(arr,0,copy,0,arr.length);
        return copy;
    }
}



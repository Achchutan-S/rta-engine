package com.rta.engine.model;

import java.util.List;

/**
 * SortRequest - Model for sorting algorithm requests
 */
public class SortRequest {
    private int[] array;
    private String algorithm;

    // Constructors
    public SortRequest() {}

    public SortRequest(int[] array, String algorithm) {
        this.array = array;
        this.algorithm = algorithm;
    }

    // Getters and Setters
    public int[] getArray() {
        return array;
    }

    public void setArray(int[] array) {
        this.array = array;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public String toString() {
        return "SortRequest{" +
                "array=" + java.util.Arrays.toString(array) +
                ", algorithm='" + algorithm + '\'' +
                '}';
    }
}


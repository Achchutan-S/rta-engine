package com.rta.engine.model;

import java.util.Arrays;

/**
 * SortResponse - Model for sorting algorithm responses
 */
public class SortResponse {
    private int[] sortedArray;
    private String algorithm;
    private long executionTimeMs;
    private boolean success;
    private String message;

    // Constructors
    public SortResponse() {}

    public SortResponse(int[] sortedArray, String algorithm, long executionTimeMs) {
        this.sortedArray = sortedArray;
        this.algorithm = algorithm;
        this.executionTimeMs = executionTimeMs;
        this.success = true;
        this.message = "Sorting completed successfully";
    }

    // Getters and Setters
    public int[] getSortedArray() {
        return sortedArray;
    }

    public void setSortedArray(int[] sortedArray) {
        this.sortedArray = sortedArray;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public long getExecutionTimeMs() {
        return executionTimeMs;
    }

    public void setExecutionTimeMs(long executionTimeMs) {
        this.executionTimeMs = executionTimeMs;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "SortResponse{" +
                "sortedArray=" + Arrays.toString(sortedArray) +
                ", algorithm='" + algorithm + '\'' +
                ", executionTimeMs=" + executionTimeMs +
                ", success=" + success +
                ", message='" + message + '\'' +
                '}';
    }
}


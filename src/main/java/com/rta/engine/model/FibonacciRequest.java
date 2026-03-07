package com.rta.engine.model;

/**
 * FibonacciRequest - Model for Fibonacci calculation requests
 */
public class FibonacciRequest {
    private int n;

    // Constructors
    public FibonacciRequest() {}

    public FibonacciRequest(int n) {
        this.n = n;
    }

    // Getters and Setters
    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    @Override
    public String toString() {
        return "FibonacciRequest{" +
                "n=" + n +
                '}';
    }
}


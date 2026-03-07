package com.rta.engine.model;

/**
 * FibonacciResponse - Model for Fibonacci calculation responses
 */
public class FibonacciResponse {
    private int n;
    private long result;
    private long executionTimeMs;
    private boolean success;
    private String message;

    // Constructors
    public FibonacciResponse() {}

    public FibonacciResponse(int n, long result, long executionTimeMs) {
        this.n = n;
        this.result = result;
        this.executionTimeMs = executionTimeMs;
        this.success = true;
        this.message = "Fibonacci calculation completed successfully";
    }

    // Getters and Setters
    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public long getResult() {
        return result;
    }

    public void setResult(long result) {
        this.result = result;
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
        return "FibonacciResponse{" +
                "n=" + n +
                ", result=" + result +
                ", executionTimeMs=" + executionTimeMs +
                ", success=" + success +
                ", message='" + message + '\'' +
                '}';
    }
}


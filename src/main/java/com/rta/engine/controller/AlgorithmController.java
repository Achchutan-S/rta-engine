package com.rta.engine.controller;

import com.rta.engine.model.SortRequest;
import com.rta.engine.model.SortResponse;
import com.rta.engine.model.FibonacciRequest;
import com.rta.engine.model.FibonacciResponse;
import com.rta.engine.service.AlgorithmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * AlgorithmController - REST API endpoints for algorithm operations
 *
 * This controller exposes various algorithm operations as REST endpoints,
 * allowing users to interact with sorting, graph, and DP algorithms
 * through HTTP requests.
 */
@RestController
@RequestMapping("/algorithm")
@CrossOrigin(origins = "*")
public class AlgorithmController {

    @Autowired
    private AlgorithmService algorithmService;

    /**
     * Health check endpoint
     *
     * @return health status
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("message", "RTA Engine Algorithm Service is running");
        response.put("version", "1.0.0");
        return ResponseEntity.ok(response);
    }

    /**
     * Get information about available algorithms
     *
     * @return map of algorithm names to descriptions
     */
    @GetMapping("/info")
    public ResponseEntity<Map<String, String>> getAlgorithmInfo() {
        Map<String, String> info = algorithmService.getAlgorithmInfo();
        return ResponseEntity.ok(info);
    }

    /**
     * Perform merge sort on the given array
     *
     * @param request sort request containing array and algorithm type
     * @return sorted array wrapped in response
     */
    @PostMapping("/sort/merge")
    public ResponseEntity<SortResponse> mergeSort(@RequestBody SortRequest request) {
        try {
            if (request.getArray() == null || request.getArray().length == 0) {
                SortResponse response = new SortResponse();
                response.setSuccess(false);
                response.setMessage("Array cannot be null or empty");
                return ResponseEntity.badRequest().body(response);
            }

            long startTime = System.currentTimeMillis();
            int[] result = algorithmService.performMergeSort(request.getArray());
            long endTime = System.currentTimeMillis();

            SortResponse response = new SortResponse(result, "MergeSort", endTime - startTime);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            SortResponse response = new SortResponse();
            response.setSuccess(false);
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Perform bubble sort on the given array
     *
     * @param request sort request containing array
     * @return sorted array wrapped in response
     */
    @PostMapping("/sort/bubble")
    public ResponseEntity<SortResponse> bubbleSort(@RequestBody SortRequest request) {
        try {
            if (request.getArray() == null || request.getArray().length == 0) {
                SortResponse response = new SortResponse();
                response.setSuccess(false);
                response.setMessage("Array cannot be null or empty");
                return ResponseEntity.badRequest().body(response);
            }

            long startTime = System.currentTimeMillis();
            int[] result = algorithmService.performBubbleSort(request.getArray());
            long endTime = System.currentTimeMillis();

            SortResponse response = new SortResponse(result, "BubbleSort", endTime - startTime);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            SortResponse response = new SortResponse();
            response.setSuccess(false);
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Calculate Fibonacci number
     *
     * @param request Fibonacci request containing n
     * @return Fibonacci number wrapped in response
     */
    @PostMapping("/dp/fibonacci")
    public ResponseEntity<FibonacciResponse> fibonacci(@RequestBody FibonacciRequest request) {
        try {
            if (request.getN() < 0) {
                FibonacciResponse response = new FibonacciResponse();
                response.setSuccess(false);
                response.setMessage("N cannot be negative");
                return ResponseEntity.badRequest().body(response);
            }

            long startTime = System.currentTimeMillis();
            long result = algorithmService.calculateFibonacci(request.getN());
            long endTime = System.currentTimeMillis();

            FibonacciResponse response = new FibonacciResponse(request.getN(), result, endTime - startTime);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            FibonacciResponse response = new FibonacciResponse();
            response.setSuccess(false);
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Get Fibonacci number with a GET endpoint
     *
     * @param n position in Fibonacci sequence
     * @return Fibonacci number wrapped in response
     */
    @GetMapping("/dp/fibonacci/{n}")
    public ResponseEntity<FibonacciResponse> getFibonacci(@PathVariable int n) {
        try {
            if (n < 0) {
                FibonacciResponse response = new FibonacciResponse();
                response.setSuccess(false);
                response.setMessage("N cannot be negative");
                return ResponseEntity.badRequest().body(response);
            }

            long startTime = System.currentTimeMillis();
            long result = algorithmService.calculateFibonacci(n);
            long endTime = System.currentTimeMillis();

            FibonacciResponse response = new FibonacciResponse(n, result, endTime - startTime);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            FibonacciResponse response = new FibonacciResponse();
            response.setSuccess(false);
            response.setMessage("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Solve N-Queens problem
     *
     * @param n board size
     * @return all valid solutions
     */
    @GetMapping("/recursion/nqueens/{n}")
    public ResponseEntity<?> solveNQueens(@PathVariable int n) {
        try {
            if (n <= 0 || n > 10) {
                Map<String, String> response = new HashMap<>();
                response.put("error", "N must be between 1 and 10");
                return ResponseEntity.badRequest().body(response);
            }

            long startTime = System.currentTimeMillis();
            algorithmService.solveNQueens(n);
            int solutionCount = algorithmService.countNQueensSolutions(n);
            long endTime = System.currentTimeMillis();

            Map<String, Object> response = new HashMap<>();
            response.put("boardSize", n);
            response.put("totalSolutions", solutionCount);
            response.put("executionTimeMs", endTime - startTime);
            response.put("success", true);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Get general information about the service
     *
     * @return service metadata
     */
    @GetMapping("/about")
    public ResponseEntity<Map<String, String>> about() {
        Map<String, String> info = new HashMap<>();
        info.put("name", "RTA Engine - Algorithm Service");
        info.put("description", "A backend learning lab for Spring Boot and Data Structure Algorithms");
        info.put("version", "1.0.0");
        info.put("author", "Learning Repository");
        return ResponseEntity.ok(info);
    }
}



package com.rta.engine.service;

import com.rta.engine.algorithms.sorting.BubbleSort;
import com.rta.engine.algorithms.sorting.MergeSort;
import com.rta.engine.algorithms.graphs.BFS;
import com.rta.engine.algorithms.graphs.DFS;
import com.rta.engine.algorithms.dp.FibonacciDP;
import com.rta.engine.algorithms.recursion.NQueens;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * AlgorithmService - Service layer for algorithm operations
 *
 * This service provides high-level methods to execute various algorithms
 * and manage algorithm operations. It acts as an intermediary between
 * the controller and the algorithm implementations.
 */
@Service
public class AlgorithmService {

    private final BubbleSort bubbleSort;
    private final MergeSort mergeSort;
    private final BFS bfs;
    private final DFS dfs;
    private final FibonacciDP fibonacciDP;
    private final NQueens nQueens;

    public AlgorithmService() {
        this.bubbleSort = new BubbleSort();
        this.mergeSort = new MergeSort();
        this.bfs = new BFS();
        this.dfs = new DFS();
        this.fibonacciDP = new FibonacciDP();
        this.nQueens = new NQueens();
    }

    /**
     * Performs merge sort on the given array
     *
     * @param arr array to be sorted
     * @return sorted array
     */
    public int[] performMergeSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return arr;
        }
        int[] copy = arr.clone();
        mergeSort.sort(copy);
        return copy;
    }

    /**
     * Performs bubble sort on the given array
     *
     * @param arr array to be sorted
     * @return sorted array
     */
    public int[] performBubbleSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return arr;
        }
        int[] copy = arr.clone();
        bubbleSort.sort(copy);
        return copy;
    }

    /**
     * Performs BFS traversal on the graph
     *
     * @param graph adjacency list representation
     * @param source starting vertex
     * @return list of vertices in BFS order
     */


    /**
     * Performs DFS traversal on the graph
     *
     * @param graph adjacency list representation
     * @param source starting vertex
     * @return list of vertices in DFS order
     */


    /**
     * Checks if path exists between two vertices using BFS
     *
     * @param graph adjacency list representation
     * @param source starting vertex
     * @param target destination vertex
     * @return true if path exists, false otherwise
     */


    /**
     * Detects if graph contains a cycle
     *
     * @param graph adjacency list representation
     * @return true if cycle exists, false otherwise
     */


    /**
     * Calculates nth Fibonacci number using optimized DP
     *
     * @param n position in Fibonacci sequence
     * @return nth Fibonacci number
     */
    public long calculateFibonacci(int n) {
        return fibonacciDP.fibonacciOptimized(n);
    }

    /**
     * Solves N-Queens problem
     *
     * @param n board size
     * @return all valid solutions
     */
    public List<List<String>> solveNQueens(int n) {
        return nQueens.solveNQueens(n);
    }

    /**
     * Counts total number of N-Queens solutions
     *
     * @param n board size
     * @return total number of valid solutions
     */
    public int countNQueensSolutions(int n) {
        return nQueens.countSolutions(n);
    }

    /**
     * Gets algorithm information
     *
     * @return map of algorithm names to descriptions
     */
    public Map<String, String> getAlgorithmInfo() {
        Map<String, String> info = new LinkedHashMap<>();
        info.put("BubbleSort", bubbleSort.getDescription());
        info.put("MergeSort", mergeSort.getDescription());
        info.put("FibonacciDP", fibonacciDP.getDescription());
        info.put("NQueens", nQueens.getDescription());
        return info;
    }
}


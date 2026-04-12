package com.rta.engine.algorithms.graphs;

import com.rta.engine.model.graph.Edge;

import java.util.*;

/**
 * Depth-First Search (DFS) Algorithm Implementation
 *
 * Time Complexity: O(V + E) - where V is vertices and E is edges
 * Space Complexity: O(V) - for the recursion stack or explicit stack
 *
 * DFS is a graph traversal algorithm that explores as far as possible
 * along each branch before backtracking. It can be implemented recursively
 * or iteratively using a stack.
 */
public class DFS {

    public static void dfs(ArrayList<Edge>[] graph, int curr, boolean[] visited){
//        System.out.println("DFS Traversal starting from vertex:"+curr);
        visited[curr] = true;
        System.out.print(curr +" ");
        for(Edge e: graph[curr]){
            if(!visited[e.dest]){
                dfs(graph,e.dest,visited);
            }
        }
        System.out.println();
//        System.out.println("DFS Traversal finishing from vertex:"+curr);
    }
}



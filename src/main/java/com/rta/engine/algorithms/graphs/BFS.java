package com.rta.engine.algorithms.graphs;

import com.rta.engine.model.graph.Edge;

import java.util.*;

/**
 * Breadth-First Search (BFS) Algorithm Implementation
 *
 * Time Complexity: O(V + E) - where V is vertices and E is edges
 * Space Complexity: O(V) - for the queue and visited set
 *
 * BFS is a graph traversal algorithm that explores vertices in layers.
 * It starts from a source vertex and explores all adjacent vertices
 * before moving to the next level.
 */
public class BFS {


    public static void bfs(ArrayList<Edge>[] graph,int start ,boolean[] visited) {
        System.out.println("BFS Traversal starting from vertex:"+start);
        Queue<Integer> queue = new LinkedList<>();

        queue.add(start); // Starting from vertex start in case of multiple connected components
        while(!queue.isEmpty()){
            int curr = queue.poll();
            if(!visited[curr]){
                System.out.print(curr +" ");
                visited[curr] = true;
                //add all neighbors to the queue
                for(Edge e: graph[curr]){
                     queue.add(e.dest);
                }
            }
        }
        System.out.println();
        System.out.println("BFS Traversal finishing from vertex:"+start);
    }

    public static void traverseAll(ArrayList<Edge>[] graph) {
        boolean[] visited = new boolean[graph.length];
        System.out.println("Performing BFS Traversal (All Components):");
        for(int i=0; i<graph.length; i++){
            if(!visited[i]){
                bfs(graph, i, visited);
            }
        }
    }
}

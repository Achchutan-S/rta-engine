package com.rta.engine.algorithms.graphs;

import com.rta.engine.model.graph.Edge;

import java.util.ArrayList;


public class BasicGraph {

    public static void createGraph(ArrayList<Edge> graph[]) {

        for(int i=0;i<graph.length;i++) {
            graph[i] = new ArrayList<Edge>();
        }

        graph[0].add(new Edge(0, 1,2));
        graph[0].add(new Edge(0, 2,2));


        graph[1].add(new Edge(1, 0,10));
        graph[1].add(new Edge(1, 3,0));

        graph[2].add(new Edge(2, 0,2));
        graph[2].add(new Edge(2, 4,10));

        graph[3].add(new Edge(3, 1,0));
        graph[3].add(new Edge(3, 4,-1));
        graph[3].add(new Edge(3, 5,-1));

        graph[4].add(new Edge(4, 2,0));
        graph[4].add(new Edge(4, 3,0));
        graph[4].add(new Edge(4, 5,0));

        graph[5].add(new Edge(5, 3,0));
        graph[5].add(new Edge(5, 4,0));
        graph[5].add(new Edge(5, 6,-1));

        graph[6].add(new Edge(6, 5,0));
    }
    public static void main(String[] args) {
        int V = 7;
        ArrayList<Edge> graph[] = new ArrayList[V];

        createGraph(graph);

        //print vertex 2's neighbours
        System.out.println("Printing 2's neighbours with weights");
        for(int i=0; i<graph[2].size();i++){
            Edge e = graph[2].get(i);
            System.out.println(e.dest + " -> " + e.weight);
        }

        BFS.traverseAll(graph); // O(V+E) time complexity, O(V) space complexity
        DFS.traverseAll(graph); // O(V+E)

        // O(V^V) in worst case when all vertices are connected to each other (Complete graph),
        // and we have to explore all paths from src to target
        AllPathSrcToTarget.printAllPathsFromStartToEnd(graph, V);

    }
}

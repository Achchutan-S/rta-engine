package com.rta.engine.algorithms.graphs;

import com.rta.engine.model.graph.Edge;

import java.util.ArrayList;

import static com.rta.engine.algorithms.graphs.BFS.bfs;
import static com.rta.engine.algorithms.graphs.DFS.dfs;


public class BasicGraph {

    public static void createGraph(ArrayList<Edge> graph[]) {

        for(int i=0;i<graph.length;i++) {
            graph[i] = new ArrayList<Edge>();
        }

        graph[0].add(new Edge(0, 2,2));

        graph[1].add(new Edge(1, 2,10));
        graph[1].add(new Edge(1, 3,0));

        graph[2].add(new Edge(2, 0,2));
        graph[2].add(new Edge(2, 1,10));
        graph[2].add(new Edge(2, 3,-1));

        graph[3].add(new Edge(3, 1,0));
        graph[3].add(new Edge(3, 2,-1));
    }
    public static void main(String[] args) {
        int V = 4;
        ArrayList<Edge> graph[] = new ArrayList[V];

        createGraph(graph);

        //print vertex 2's neighbours
        System.out.println("Printing 2's neighbours with weights");
        for(int i=0; i<graph[2].size();i++){
            Edge e = graph[2].get(i);
            System.out.println(e.dest + " -> " + e.weight);
        }
        System.out.println();
        boolean[] visited = new boolean[V];
//        System.out.println("Performing BFS Traversal (All Components):");
//        for(int i=0;i<graph.length;i++){
//            if(!visited[i]){
//                bfs(graph,i,visited);
//            }
//        }
        System.out.println();
        System.out.println("Performing DFS Traversal:");
        for(int i=0;i<graph.length;i++){
            if(!visited[i]){
                dfs(graph,i,visited);
            }
        }


    }
}

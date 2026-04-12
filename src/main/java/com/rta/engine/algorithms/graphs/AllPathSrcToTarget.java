package com.rta.engine.algorithms.graphs;

import com.rta.engine.model.graph.Edge;

import java.util.ArrayList;

public class AllPathSrcToTarget {

    public static void printAllPaths(ArrayList<Edge>[] graph, boolean [] visited,int curr,String path,int target){
        //base case
        if(curr == target){
            System.out.println(path);
            return;
        }
        for(Edge e: graph[curr]){
            if(!visited[e.dest]){
                visited[curr]=true;
                printAllPaths(graph, visited, e.dest, path+"->"+e.dest, target);
                visited[curr]=false; // backtrack so that its not a permanent visit

            }
        }
    }

    public static void printAllPathsFromStartToEnd(ArrayList<Edge>[] graph, int V) {
        int src = graph[0].get(0).src;
        int target = graph[graph.length-1].get(0).dest;
        System.out.println("Printing all paths from "+src+" to "+target);
        printAllPaths(graph, new boolean[V], src, src+"", target);
    }
}

package com.rta.engine.model.graph;

public class Edge {
    public int src;
    public int dest;
    public int weight;

    public Edge(int src, int dest) {
        this.src = src;
        this.dest = dest;
    }
    public Edge(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }
}

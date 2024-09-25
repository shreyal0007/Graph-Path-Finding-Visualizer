package com.miracle.pathfinding.models;

public class Node {
    private final int x;
    private final int y;
    private boolean isWall;
    private boolean isVisited;
    private Node parent;
    private double F;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.isWall = false;
        isVisited = false;
        this.parent = null;
        this.F = 0.0;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public boolean isWall() {
        return isWall;
    }

    public void setWall(boolean wall) {
        isWall = wall;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public double getF() {
        return F;
    }

    public void setF(double f) {
        F = f;
    }
}

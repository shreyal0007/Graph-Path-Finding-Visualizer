package com.miracle.pathfinding.models;

public class Grid {
    private int rows, cols;
    private Node[][] grid;

    public Grid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        grid = new Node[rows][cols];

        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                grid[i][j] = new Node(i, j);
            }
        }
    }

    public Node getNode(int x, int y) {
        if(x >= 0 && x< rows && y >= 0 && y < cols) {
            return grid[x][y];
        }
        return null;
    }

    public void setWall(int x, int y, boolean isWall) {
        Node node = getNode(x, y);
        if(node != null) {
            node.setWall(isWall);
        }
    }

    public Node[][] getGrid() {
        return grid;
    }

    public Node[] getNeighbourNodes(Node node) {
        int x = node.getX();
        int y = node.getY();
        return new Node[]{
                this.getNode(x - 1, y),
                this.getNode(x + 1, y),
                this.getNode(x, y - 1),
                this.getNode(x, y + 1)
        };
    }
}

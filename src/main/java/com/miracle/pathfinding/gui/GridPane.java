package com.miracle.pathfinding.gui;

import com.miracle.pathfinding.algorithms.*;
import com.miracle.pathfinding.models.Grid;
import com.miracle.pathfinding.models.Node;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GridPane extends Pane implements AlgorithmListener {
    private static final int SIZE = 20;
    private static final int ROWS = 20;
    private static final int COLS = 20;
    private Grid grid;
    private Rectangle[][] cells;
    private Node startNode, endNode;
    private String selectedAlgo;

    public GridPane() {
        grid = new Grid(ROWS, COLS);
        cells = new Rectangle[ROWS][COLS];
        initialiseGrid();
    }

    private void initialiseGrid() {
        getChildren().clear();
        startNode = null;
        endNode = null;

        for(int i = 0; i < ROWS; i++) {
            for(int j = 0; j < COLS; j++) {
                Node node = grid.getNode(i, j);
                node.setWall(false);
                node.setParent(null);

                Rectangle cell = new Rectangle(SIZE, SIZE);
                cell.setStroke(Color.BLACK);
                cell.setFill(Color.WHITE);
                cell.setX(j * SIZE);
                cell.setY(i * SIZE);
                cell.setOnMouseClicked(
                        event -> {
                            if(event.isShiftDown()) {
                                if(startNode == null) {
                                    startNode = node;
                                    cell.setFill(Color.GREEN);
                                } else if(endNode == null) {
                                    endNode = node;
                                    cell.setFill(Color.RED);
                                }
                            } else {
                                node.setWall(true);
                                cell.setFill(Color.GOLD);
                            }
                        }
                );
                cells[i][j] = cell;
                getChildren().add(cell);
            }
        }
    }

    public void resetGrid() {
        grid = new Grid(ROWS, COLS);
        initialiseGrid();
    }

    public void runAlgorithm() {
        if(startNode != null && endNode != null) {
            switch (selectedAlgo) {
                case "BFS":
                    Algorithm bfs = new BFS(grid, startNode, endNode, this);
                    bfs.findPath();
                    break;
                case "DFS":
                    Algorithm dfs = new DFS(grid, startNode, endNode, this);
                    dfs.findPath();
                    break;
                case "A-Star":
                    Algorithm aStar = new AStar(grid, startNode, endNode, this);
                    aStar.findPath();
                    break;
                case "Dijkstra":
                    Algorithm dij = new Dijkstra(grid, startNode, endNode, this);
                    dij.findPath();
                    break;
            }
        }
    }


    @Override
    public void onNodeVisited(Node node) {
        Platform.runLater(() -> {
            if(node != startNode && node != endNode) {
                cells[node.getX()][node.getY()].setFill(Color.BROWN);
            }
        });
    }

    @Override
    public void onPathFound() {
        Platform.runLater(this::highlightPath);
    }

    private void highlightPath() {
        Node current = endNode;
        while(current != null && current != startNode) {
            Node parent = current.getParent();
            if(parent != null && parent != startNode) {
                cells[parent.getX()][parent.getY()].setFill(Color.SKYBLUE);
            }
            current = parent;
        }
    }

    public void setSelectedAlgo(String selectedAlgo) {
        this.selectedAlgo = selectedAlgo;
    }
}

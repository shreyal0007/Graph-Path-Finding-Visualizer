package com.miracle.pathfinding.algorithms;

import com.miracle.pathfinding.models.Grid;
import com.miracle.pathfinding.models.Node;
import javafx.application.Platform;

import java.util.Stack;


public class DFS implements Algorithm{
    private Grid grid;
    private Node startNode, endNode;
    private AlgorithmListener listener;

    public DFS(Grid grid, Node startNode, Node endNode, AlgorithmListener listener) {
        this.grid = grid;
        this.startNode = startNode;
        this.endNode = endNode;
        this.listener = listener;
    }

    public void findPath() {
        new Thread(() -> {
            Stack<Node> stack = new Stack<>();
            stack.push(startNode);
            startNode.setVisited(true);

            while (!stack.isEmpty()) {
                Node current = stack.pop();
                Platform.runLater(() -> listener.onNodeVisited(current));

                if (current.equals(endNode)) {
                    Platform.runLater(listener::onPathFound);
                    return;
                }

                for (Node neighbor : grid.getNeighbourNodes(current)) {
                    if (neighbor != null && !neighbor.isVisited() && !neighbor.isWall()) {
                        neighbor.setVisited(true);
                        neighbor.setParent(current);
                        stack.push(neighbor);
                    }
                }

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}

package com.miracle.pathfinding.algorithms;

import com.miracle.pathfinding.models.Grid;
import com.miracle.pathfinding.models.Node;
import javafx.application.Platform;

import java.util.LinkedList;
import java.util.Queue;

public class BFS implements Algorithm{
    private Grid grid;
    private Node startNode, endNode;
    private AlgorithmListener bfsListener;


    public BFS(Grid grid, Node startNode, Node endNode, AlgorithmListener listener) {
        this.grid = grid;
        this.startNode = startNode;
        this.endNode = endNode;
        this.bfsListener = listener;
    }

    public void findPath() {
        new Thread(() -> {
            Queue<Node> queue = new LinkedList<>();
            queue.offer(startNode);
            startNode.setVisited(true);

            while (!queue.isEmpty()) {
                Node curr = queue.poll();
                Platform.runLater(() -> bfsListener.onNodeVisited(curr));
                if(curr.equals(endNode)) {
                    Platform.runLater(() -> bfsListener.onPathFound());
                    return;
                }

                for(Node neighbor : grid.getNeighbourNodes(curr)) {
                    if(neighbor != null && !neighbor.isVisited() && !neighbor.isWall() ) {
                        neighbor.setParent(curr);
                        queue.offer(neighbor);
                        neighbor.setVisited(true);
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

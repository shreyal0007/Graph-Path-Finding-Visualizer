package com.miracle.pathfinding.algorithms;

import com.miracle.pathfinding.models.Grid;
import com.miracle.pathfinding.models.Node;
import javafx.application.Platform;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class AStar implements Algorithm{
    private Grid grid;
    private Node startNode, endNode;
    private AlgorithmListener listener;

    public AStar(Grid grid, Node startNode, Node endNode, AlgorithmListener listener) {
        this.grid = grid;
        this.startNode = startNode;
        this.endNode = endNode;
        this.listener = listener;
    }

    private double heuristic(Node a, Node b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }


    @Override
    public void findPath() {
        new Thread(() -> {
            PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingDouble(Node::getF));
            Map<Node, Double> gscore = new HashMap<>();
            gscore.put(startNode, 0.0);
            startNode.setF(heuristic(startNode, endNode));
            openSet.add(startNode);

            while (!openSet.isEmpty()) {
                Node current = openSet.poll();
                Platform.runLater(() -> listener.onNodeVisited(current));

                if (current.equals(endNode)) {
                    Platform.runLater(listener::onPathFound);
                    return;
                }

                for(Node neighbor : grid.getNeighbourNodes(current)) {
                    if (neighbor == null || neighbor.isWall()) continue;
                    double tentativeScore = gscore.getOrDefault(current, Double.MAX_VALUE) + 1;
                    if(tentativeScore < gscore.getOrDefault(neighbor, Double.MAX_VALUE)) {
                        neighbor.setParent(current);
                        gscore.put(neighbor, tentativeScore);
                        neighbor.setF(tentativeScore + heuristic(neighbor, endNode));
                        openSet.add(neighbor);
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

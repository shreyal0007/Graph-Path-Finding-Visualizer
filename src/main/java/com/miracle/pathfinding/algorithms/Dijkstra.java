package com.miracle.pathfinding.algorithms;

import com.miracle.pathfinding.models.Grid;
import com.miracle.pathfinding.models.Node;
import javafx.application.Platform;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Dijkstra implements Algorithm{
    private Grid grid;
    private Node startNode, endNode;
    private AlgorithmListener listener;


    public Dijkstra(Grid grid, Node startNode, Node endNode, AlgorithmListener listener) {
        this.grid = grid;
        this.startNode = startNode;
        this.endNode = endNode;
        this.listener = listener;
    }

    @Override
    public void findPath() {
        new Thread(() -> {
            PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingDouble(Node::getF));
            Map<Node, Double> gScore = new HashMap<>();
            gScore.put(startNode, 0.0);
            startNode.setF(0.0);
            openSet.add(startNode);

            while (!openSet.isEmpty()) {
                Node current = openSet.poll();
                Platform.runLater(() -> listener.onNodeVisited(current));

                if (current.equals(endNode)) {
                    Platform.runLater(listener::onPathFound);
                    return;
                }

                for(Node neighbor : grid.getNeighbourNodes(current)) {
                    if(neighbor == null || neighbor.isWall()) {
                        continue;
                    }
                    double tentativeScore = gScore.getOrDefault(current, Double.MAX_VALUE) + 1;
                    if (tentativeScore < gScore.getOrDefault(neighbor, Double.MAX_VALUE)) {
                        neighbor.setParent(current);
                        gScore.put(neighbor, tentativeScore);
                        neighbor.setF(tentativeScore);
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

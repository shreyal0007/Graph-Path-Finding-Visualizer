package com.miracle.pathfinding.algorithms;

import com.miracle.pathfinding.models.Node;

public interface AlgorithmListener {
    void onNodeVisited(Node node);
    void onPathFound();
}

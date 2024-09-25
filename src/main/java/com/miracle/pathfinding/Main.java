package com.miracle.pathfinding;

import com.jpro.webapi.JProApplication;
import com.miracle.pathfinding.gui.GridPane;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends JProApplication {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Pathfinding Algorithm Visualizer");

        GridPane pane = new GridPane();

        Button submitButton = new Button("Start Algorithm");
        submitButton.setOnAction(e -> pane.runAlgorithm());

        Button resetButton = new Button("Reset Grid");
        resetButton.setOnAction(e -> pane.resetGrid());

        ComboBox<String> algoSelect = new ComboBox<>();
        algoSelect.getItems().addAll("BFS", "DFS", "A-Star", "Dijkstra");
        algoSelect.setValue("BFS");
        pane.setSelectedAlgo("BFS");
        algoSelect.setOnAction(e-> pane.setSelectedAlgo(algoSelect.getValue()));

        HBox row = new HBox(10, submitButton, resetButton, algoSelect);
        row.setPadding(new Insets(10));

        StackPane gridContainer = new StackPane(pane);
        gridContainer.setPadding(new Insets(20));

        BorderPane root = new BorderPane();
        root.setCenter(gridContainer);
        root.setTop(row);


        Scene scene = new Scene(root, 440, 490);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

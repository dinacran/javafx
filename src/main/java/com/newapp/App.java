package com.newapp;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {

        Button visualizeResponse = new Button("Visulaize Response");
        Button openXmind = new Button("Open Xmind");
        Button searchCode = new Button("Search Code");
        Button testRule = new Button("Test Rule");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(visualizeResponse, 0, 0);
        grid.add(openXmind, 1, 0);
        grid.add(searchCode, 0, 1);
        grid.add(testRule, 1, 1);

        Scene scene = new Scene(grid, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
        

    }

    public static void main(String[] args) {

        launch(args);

    }
}
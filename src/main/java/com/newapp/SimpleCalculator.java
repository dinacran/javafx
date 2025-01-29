package com.newapp;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SimpleCalculator extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create UI components
        Label label1 = new Label("Number 1:");
        Label label2 = new Label("Number 2:");
        Label resultLabel = new Label("Result: ");
        
        TextField num1Field = new TextField();
        TextField num2Field = new TextField();
        Label resultField = new Label();
        
        Button addButton = new Button("Add");
        
        // Set button action
        addButton.setOnAction(e -> {
            try {
                double num1 = Double.parseDouble(num1Field.getText());
                double num2 = Double.parseDouble(num2Field.getText());
                double result = num1 + num2;
                resultField.setText(String.valueOf(result));
            } catch (NumberFormatException ex) {
                resultField.setText("Invalid Input");
            }
        });

        // Arrange components in a grid
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setHgap(10);
        grid.setVgap(10);
        
        grid.add(label1, 0, 0);
        grid.add(num1Field, 1, 0);
        grid.add(label2, 0, 1);
        grid.add(num2Field, 1, 1);
        grid.add(addButton, 1, 2);
        grid.add(resultLabel, 0, 3);
        grid.add(resultField, 1, 3);

        // Create and set the scene
        Scene scene = new Scene(grid, 1920 , 1080);
        primaryStage.setTitle("Simple Calculator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

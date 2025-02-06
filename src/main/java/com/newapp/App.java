package com.newapp;

import org.scenicview.ScenicView;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;


public class App extends Application {

    @Override
    public void start(Stage homeStage) {

        VBox visualizeResponse = createTile("/api.png","Visualize Response");

        VBox openXmind = createTile("/xmind.png", "Open Xmind");
        VBox searchCode = createTile("/search.png", "Search Code");
        VBox testRule = createTile("/test.png", "Test Rule");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10,10,10,10));
        GridPane.setConstraints(visualizeResponse, 0, 0);
        GridPane.setConstraints(openXmind, 1, 0);
        GridPane.setConstraints(searchCode, 0, 1);
        GridPane.setConstraints(testRule, 1, 1);
        gridPane.getChildren().addAll(visualizeResponse, openXmind, searchCode, testRule);
        gridPane.setHgap(100);
        gridPane.setVgap(100);
        gridPane.setPrefSize(800, 600);
        gridPane.setAlignment(Pos.CENTER);

        Scene scene = new Scene(gridPane, 800, 600);
        homeStage.setMaximized(true);
        
        homeStage.setScene(scene);
        homeStage.setTitle("JavaFX Application");
        ScenicView.show(scene);
        homeStage.show();

    }

    public VBox createTile(String imagePath, String text) {
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(imagePath)));
        imageView.setFitWidth(64);
        imageView.setFitHeight(64);
        imageView.setPreserveRatio(true);
        
        Label label = new Label(text);

        VBox vbox = new VBox(imageView, label);
        vbox.setMinSize(150, 120);
        vbox.setMaxSize(150, 120);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(5);
        vbox.setStyle("-fx-border-color: #4377cb; -fx-border-width: 1; -fx-border-radius: 5; -fx-background-color: white; -fx-padding: 10;");
        return vbox;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
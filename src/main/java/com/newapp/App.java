package com.newapp;

import org.scenicview.ScenicView;

import com.newapp.scenes.VisulaizeResponse;

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

    Stage homeStage;

    VisulaizeResponse visulaizeResponse = new VisulaizeResponse();

    @Override
    public void start(Stage homeStage) {

        this.homeStage = homeStage;

        VBox visualizeResponseBox = createTile("/img/api.png", "Visualize Response");
        VBox openXmindBox = createTile("/img/xmind.png", "Open Xmind");
        VBox searchCodeBox = createTile("/img/search.png", "Search Code");
        VBox testRuleBox = createTile("/img/test.png", "Test Rule");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        GridPane.setConstraints(visualizeResponseBox, 0, 0);
        GridPane.setConstraints(openXmindBox, 1, 0);
        GridPane.setConstraints(searchCodeBox, 0, 1);
        GridPane.setConstraints(testRuleBox, 1, 1);
        gridPane.getChildren().addAll(visualizeResponseBox, openXmindBox, searchCodeBox, testRuleBox);
        gridPane.setHgap(100);
        gridPane.setVgap(100);
        gridPane.setPrefSize(800, 600);
        gridPane.setAlignment(Pos.CENTER);

        Scene scene = new Scene(gridPane, 800, 600);
        scene.getStylesheets().add("/css/dark-theme.css");
        homeStage.setMaximized(true);

        homeStage.setScene(scene);
        homeStage.setTitle("JavaFX Application");
        ScenicView.show(scene);
        homeStage.show();

        visualizeResponseBox.setOnMouseClicked(e -> {

            homeStage.setScene(visulaizeResponse.getScene());
            
        });
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
        vbox.getStyleClass().add("tile");
        
        return vbox;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
package com.newapp.scenes;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class VisualizeResponse {

    private Scene visualizeResponseScene;

    public VisualizeResponse() {
        visualizeResponseScene = build();
    }

    public Scene build() {

        Parent root = null;

        try {
            root = FXMLLoader.load(getClass().getResource("/visualizeResponse.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root, 800, 600);

        scene.getStylesheets().add("/css/dark-theme.css");

        return scene;

    }

    public Scene getScene() {
        return visualizeResponseScene;
    }

}

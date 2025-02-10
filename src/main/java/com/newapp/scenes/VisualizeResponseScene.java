package com.newapp.scenes;

import java.io.IOException;

import com.newapp.App;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class VisualizeResponseScene {

    private static Scene visualizeResponseScene;

    public static void build() {

        try {
            visualizeResponseScene = new Scene(FXMLLoader.load(HomeScene.class.getResource("/visualizeResponse.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void switchScene() {
        App.primaryStage.setScene(visualizeResponseScene);
        App.primaryStage.setMaximized(true);
        App.primaryStage.show();
    }

}

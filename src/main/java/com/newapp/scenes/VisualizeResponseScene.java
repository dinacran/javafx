package com.newapp.scenes;

import java.io.IOException;

import com.newapp.App;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class VisualizeResponseScene {


    private static Scene visualizeResponseScene;

    public static void build() {

        Parent root = null;

        try {
            root = FXMLLoader.load(HomeScene.class.getResource("/visualizeResponse.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        visualizeResponseScene = new Scene(root);

    }

    public static void switchScene() {
        App.primaryStage.setScene(visualizeResponseScene);
        App.primaryStage.show();
    }

}

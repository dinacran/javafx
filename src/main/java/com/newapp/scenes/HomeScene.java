package com.newapp.scenes;

import java.io.IOException;

import com.newapp.App;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class HomeScene {


    private static Scene homeScene;

    public static void build() {


        Parent root = null;

        try {
            root = FXMLLoader.load(HomeScene.class.getResource("/home.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        homeScene = new Scene(root);

    }

    public static void switchScene() {
        App.primaryStage.setScene(homeScene);
    }
}

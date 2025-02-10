package com.newapp.scenes;

import java.io.IOException;

import com.newapp.App;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class HomeScene {

    private static Scene homeScene;

    public static void build() {


        try {
            homeScene = new Scene(FXMLLoader.load(HomeScene.class.getResource("/home.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void switchScene() {
        App.primaryStage.setScene(homeScene);
    }
}

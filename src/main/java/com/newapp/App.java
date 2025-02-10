package com.newapp;

// import org.scenicview.ScenicView;

import com.newapp.scenes.HomeScene;
import com.newapp.scenes.VisualizeResponseScene;

import javafx.application.Application;

import javafx.stage.Stage;

public class App extends Application {

    public static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        App.primaryStage = primaryStage;

        HomeScene.build();
        VisualizeResponseScene.build();
        HomeScene.switchScene();
        primaryStage.setMaximized(true);
        primaryStage.show();        
    }
    public static void main(String[] args) {
        launch(args);
    }
}
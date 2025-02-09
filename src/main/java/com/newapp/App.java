package com.newapp;

// import org.scenicview.ScenicView;

import com.newapp.controller.HomeController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    Stage homeStage;

    @Override
    public void start(Stage homeStage) {

        this.homeStage = homeStage;

        Parent root = null;
        FXMLLoader loader = new FXMLLoader();
        try {
            loader = new FXMLLoader(getClass().getResource("/home.fxml"));
            root = loader.load();

        } catch (Exception e) {
            e.printStackTrace();
        }
        HomeController controller = loader.getController();
        controller.setHomeStage(homeStage);
        Scene sceneFromFxml = new Scene(root, 800, 600);

        homeStage.setScene(sceneFromFxml);
        homeStage.setMaximized(true);

        homeStage.setTitle("JavaFX Application");
        // ScenicView.show(sceneFromFxml);
        homeStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
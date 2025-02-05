package com.newapp;

import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {

        VBox visualizeResponse = createTile("visualizeResponse","Visulaize Response");
        VBox openXmind = createTile("openXmind", "Open Xmind");
        VBox searchCode = createTile("searchCode", "Search Code");
        VBox testRule = createTile("testRule", "Test Rule");

        TilePane tilePane = new TilePane();
        tilePane.setPrefColumns(2);
        tilePane.setPrefRows(2);

        tilePane.setAlignment(Pos.CENTER);
        tilePane.setHgap(20);
        tilePane.setVgap(20);

        tilePane.getChildren().addAll(visualizeResponse, openXmind, searchCode, testRule);

        Scene scene = new Scene(tilePane);
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene); 
        primaryStage.show();
        
    }

    public VBox createTile(String imagePath, String text){

        // ImageView imageView = new ImageView(imagePath);

        // imageView.setFitHeight(50);
        // imageView.setFitHeight(50);

        Map<String, String> svgPathMap = new HashMap<>();

        svgPathMap.put("visualizeResponse", "M560-160v-80h120q17 0 28.5-11.5T720-280v-80q0-38 22-69t58-44v-14q-36-13-58-44t-22-69v-80q0-17-11.5-28.5T680-720H560v-80h120q50 0 85 35t35 85v80q0 17 11.5 28.5T840-560h40v160h-40q-17 0-28.5 11.5T800-360v80q0 50-35 85t-85 35H560Zm-280 0q-50 0-85-35t-35-85v-80q0-17-11.5-28.5T120-400H80v-160h40q17 0 28.5-11.5T160-600v-80q0-50 35-85t85-35h120v80H280q-17 0-28.5 11.5T240-680v80q0 38-22 69t-58 44v14q36 13 58 44t22 69v80q0 17 11.5 28.5T280-240h120v80H280Z");
        svgPathMap.put("openXmind", "M160-160q-33 0-56.5-23.5T80-240v-480q0-33 23.5-56.5T160-800h240l80 80h320q33 0 56.5 23.5T880-640H447l-80-80H160v480l96-320h684L837-217q-8 26-29.5 41.5T760-160H160Zm84-80h516l72-240H316l-72 240Zm0 0 72-240-72 240Zm-84-400v-80 80Z");
        svgPathMap.put("searchCode", "M320-240 80-480l240-240 57 57-184 184 183 183-56 56Zm320 0-57-57 184-184-183-183 56-56 240 240-240 240Z");
        svgPathMap.put("testRule", "M480-200q66 0 113-47t47-113v-160q0-66-47-113t-113-47q-66 0-113 47t-47 113v160q0 66 47 113t113 47Zm-80-120h160v-80H400v80Zm0-160h160v-80H400v80Zm80 40Zm0 320q-65 0-120.5-32T272-240H160v-80h84q-3-20-3.5-40t-.5-40h-80v-80h80q0-20 .5-40t3.5-40h-84v-80h112q14-23 31.5-43t40.5-35l-64-66 56-56 86 86q28-9 57-9t57 9l88-86 56 56-66 66q23 15 41.5 34.5T688-640h112v80h-84q3 20 3.5 40t.5 40h80v80h-80q0 20-.5 40t-3.5 40h84v80H688q-32 56-87.5 88T480-120Z");

        SVGPath svgPath = new SVGPath();

        svgPath.setContent(svgPathMap.get(imagePath));
        svgPath.setTranslateX(0);
        svgPath.setTranslateY(0);
        svgPath.setScaleX(0.05);
        svgPath.setScaleY(0.05);

        Label label = new Label(text);

        VBox tile = new VBox(10, svgPath, label);

        // tile.setScaleX(0.5);
        // tile.setScaleY(0.5);

        // tile.maxHeight(0.5);
        // tile.maxWidth(0.5);

        tile.setAlignment(Pos.CENTER);

        return tile;
    }

    public static void main(String[] args) {

        launch(args);

    }
}
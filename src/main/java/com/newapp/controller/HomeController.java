package com.newapp.controller;

import com.newapp.scenes.VisulaizeResponse;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HomeController {

    @FXML
    private VBox visualizeResponseBox;

    @FXML
    private ImageView visualizeResponseImage;

    @FXML
    private VBox openXmindBox;

    @FXML
    private ImageView openXmindImage;

    @FXML
    private VBox searchCodeBox;

    @FXML
    private ImageView searchCodeImage;

    @FXML
    private VBox testRuleBox;
    
    @FXML
    private ImageView testRuleImage;

    private Stage homeStage;
    private VisulaizeResponse visulaizeResponse = new VisulaizeResponse();

    @FXML
    public void initialize() {
        visualizeResponseImage.setImage(new Image(getClass().getResourceAsStream("/img/api.png")));
        openXmindImage.setImage(new Image(getClass().getResourceAsStream("/img/xmind.png")));
        searchCodeImage.setImage(new Image(getClass().getResourceAsStream("/img/search.png")));
        testRuleImage.setImage(new Image(getClass().getResourceAsStream("/img/test.png")));
    }

    @FXML
    private void handleVisualizeResponseClick() {
        homeStage.setScene(visulaizeResponse.getScene());
    }

    public void setHomeStage(Stage homeStage) {
        this.homeStage = homeStage;
    }

}

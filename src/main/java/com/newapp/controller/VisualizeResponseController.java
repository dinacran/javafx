package com.newapp.controller;

import com.newapp.services.ApiService;
import com.newapp.services.TokenService;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import org.json.JSONArray;
import org.json.JSONObject;


public class VisualizeResponseController {    

    @FXML
    private TextField docIdField;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private ToggleGroup viewModeGroup;

    @FXML
    private ToggleGroup dataToViewGroup;   
    
    private TokenService tokenService;

    public JSONArray response;
    
    public JSONObject request;

    public JSONObject bundler;

    public VisualizeResponseController() {
    }

    @FXML
    public void initialize() {

    }

    @FXML
    private void fetchData() throws Exception {
        request = null;
        response = null;
        bundler = null;

        String doc = docIdField.getText();
        String client = comboBox.getValue();

        if (doc == null || doc.isEmpty()) {
            showAlert("Enter a DocId");
            return;
        }

        String token = tokenService.getToken(); 
        if (token == null) {
            token = tokenService.promptForToken(); 
        }

        if ("auto find".equals(client)) {
            client = ApiService.findClient(doc, token); 
        }

        if (client == null || client.isEmpty() || !client.matches("^[a-zA-Z]+/\\d+$")) {
            showAlert("Select a Valid Client");
            return;
        }
        
        response = ApiService.getResponse(doc, client, token); 
        request = ApiService.getRequest(doc, client, token);
        bundler = ApiService.getBundler(doc, client, token);
        RadioButton selectedViewMode = (RadioButton) viewModeGroup.getSelectedToggle();
        RadioButton selectedDataToView = (RadioButton) dataToViewGroup.getSelectedToggle();


        
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

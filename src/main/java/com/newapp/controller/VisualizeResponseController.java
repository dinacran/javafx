package com.newapp.controller;

import com.newapp.services.ApiService;
import com.newapp.services.TokenService;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class VisualizeResponseController {
    

    @FXML
    private TextField docIdField;

    @FXML
    private TextField clientsField;

    private ApiService apiService;

    private TokenService tokenService;

    public VisualizeResponseController() {
        this.apiService = new ApiService();
    }

    @FXML
    private void fetchData() {
        String doc = docIdField.getText();
        String client = clientsField.getText();

        if (doc == null || doc.isEmpty()) {
            showAlert("Enter a DocId");
            return;
        }

        String token = tokenService.getToken(); 
        if (token == null) {
            token = tokenService.promptForToken(); 
        }

        if ("auto find".equals(client)) {
            client = apiService.findClient(doc, token); 
        }

        if (client == null || client.isEmpty() || !client.matches("^[a-zA-Z]+/\\d+$")) {
            showAlert("Select a Valid Client");
            return;
        }

        apiService.fetchData(client, doc, token); 
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

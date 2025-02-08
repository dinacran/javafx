package com.newapp.controller;

import java.io.BufferedReader;
import java.util.Collection;
import java.util.Collections;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


import org.json.JSONArray;
import org.json.simple.parser.JSONParser;

import com.newapp.services.ApiService;
import com.newapp.services.HighlightService;
import com.newapp.services.TokenService;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;


public class VisualizeResponseController {    

    @FXML
    private TextField docIdField;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private Button fetchButton;

    @FXML
    private ToggleGroup viewModeGroup;

    @FXML
    private ToggleGroup dataToViewGroup;

    @FXML
    private TextField searchField;

    @FXML
    private Button searchButton;
    
    @FXML
    private VBox dataContainer;

    @FXML
    private CodeArea codeArea;

    public BufferedReader response;
    
    public BufferedReader request;

    public BufferedReader bundler;

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

        String token = TokenService.getToken(); 
        if (token == null) {
            token = TokenService.promptForToken(); 
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

        String selectedViewMode = ((RadioButton) viewModeGroup.getSelectedToggle()).getText();
        String selectedDataToView = ((RadioButton) dataToViewGroup.getSelectedToggle()).getText();

        switch (selectedDataToView) {
            case "Response":
                setDataToContainer(response, selectedViewMode);                
                break;
            case "Request":
                setDataToContainer(request, selectedViewMode);  
                break;
            case "Bundler":
                setDataToContainer(bundler, selectedViewMode);                      
                break;
        }

    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void setDataToContainer(BufferedReader jsonData, String viewMode) throws Exception{
        StringBuilder inputLine = new StringBuilder();
        String line;

        while ((line = jsonData.readLine()) != null) {
            inputLine.append(line + "\n");

        }

        jsonData.close();

        JSONParser parser = new JSONParser();

        if ("JSON".equals(viewMode)) {
            codeArea.replaceText(0, 0, inputLine.toString());


            codeArea.setStyleSpans(0, HighlightService.computeHighlighting(inputLine.toString()));

        } else if ("Table".equals(viewMode)) {
            JSONArray jsonArray = (JSONArray) parser.parse(inputLine.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                Label label = new Label(jsonArray.get(i).toString());
                dataContainer.getChildren().add(label);
            }
        }

    }

    @FXML
    private void handleSearch() {
        String searchText = searchField.getText();
        // codeArea.replaceText(0, 0, codeArea.getText());
        codeArea.setStyleSpans(0, HighlightService.highlightSearchText(codeArea.getText(), searchText));


    }
}

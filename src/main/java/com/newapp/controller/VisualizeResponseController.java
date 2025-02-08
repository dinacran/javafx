package com.newapp.controller;

import java.io.BufferedReader;
import java.util.Collection;

import com.newapp.services.ApiService;
import com.newapp.services.HighlightService;
import com.newapp.services.TokenService;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.model.StyleSpans;

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

    public StringBuilder response;

    public StringBuilder bundler;

    public StringBuilder request;

    StyleSpans<Collection<String>> styleSpans;

    public VisualizeResponseController() {
    }

    @FXML
    public void initialize() {

        // codeArea.selectedTextProperty().addListener((obs, oldText, newText) -> {
        //     if (!newText.isEmpty()) {
        //         codeArea.setStyleClass(codeArea.getSelection().getStart(),
        //                 codeArea.getSelection().getEnd(),
        //                 "selected-highlight");
        //     } else {
        //         codeArea.setStyleSpans(0, HighlightService.computeHighlighting(codeArea.getText()));
        //     }
        // });

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

        BufferedReader responseReader = ApiService.getResponse(doc, client, token);
        BufferedReader requestReader = ApiService.getRequest(doc, client, token);
        BufferedReader bundlerReader = ApiService.getBundler(doc, client, token);

        String line;

        response = new StringBuilder();
        request = new StringBuilder();
        bundler = new StringBuilder();  

        while ((line = responseReader.readLine()) != null) {
            response.append(line + "\n");
        }

        while ((line = requestReader.readLine()) != null) {
            request.append(line + "\n");
        }

        while ((line = bundlerReader.readLine()) != null) {
            bundler.append(line + "\n");
        }

        responseReader.close();
        requestReader.close();
        bundlerReader.close();

        styleSpans = HighlightService.computeHighlighting(response.toString());

        switchData();

    }

    @FXML
    public void switchData() throws Exception {
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

    
    private void setDataToContainer(StringBuilder jsonData, String viewMode) throws Exception {
        
        String temp = jsonData.toString();

        codeArea.replaceText(temp);

        codeArea.setStyleSpans(0, HighlightService.computeHighlighting(temp));
        
    }

    @FXML
    private void handleSearch() {
        String searchText = searchField.getText();
        codeArea.setStyleSpans(0, HighlightService.highlightSearchText(codeArea.getText(), searchText));
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

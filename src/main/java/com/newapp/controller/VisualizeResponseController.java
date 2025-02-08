package com.newapp.controller;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private TextField findField;

    @FXML
    private Label findCountLabel;

    @FXML
    private Button prevMatchButton;

    @FXML
    private Button nextMatchButton;

    @FXML
    private VBox dataContainer;

    @FXML
    private CodeArea codeArea;

    public StringBuilder response;

    public StringBuilder bundler;

    public StringBuilder request;

    StyleSpans<Collection<String>> styleSpans;

    private List<Integer> matchPositions = new ArrayList<>();
    private int currentMatchIndex = -1;

    public VisualizeResponseController() {
    }

    @FXML
    public void initialize() {

        // codeArea.selectedTextProperty().addListener((obs, oldText, newText) -> {
        // if (!newText.isEmpty()) {
        // codeArea.setStyleClass(codeArea.getSelection().getStart(),
        // codeArea.getSelection().getEnd(),
        // "selected-highlight");
        // } else {
        // codeArea.setStyleSpans(0,
        // HighlightService.computeHighlighting(codeArea.getText()));
        // }
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
                if (response == null) {
                    showAlert(selectedDataToView + " is empty");
                    return;
                }
                setDataToContainer(response, selectedViewMode);
                break;
            case "Request":
                if (request == null) {
                    showAlert(selectedDataToView + " is empty");
                    return;
                }
                setDataToContainer(request, selectedViewMode);
                break;
            case "Bundler":
                if (bundler == null) {
                    showAlert(selectedDataToView + " is empty");
                    return;
                }
                setDataToContainer(bundler, selectedViewMode);
                break;
        }
        handleSearch();

    }

    private void setDataToContainer(StringBuilder jsonData, String viewMode) throws Exception {

        String temp = jsonData.toString();

        codeArea.replaceText(temp);

        codeArea.setStyleSpans(0, HighlightService.computeHighlighting(temp));

    }

    // @FXML
    // private void handleSearch() {
    // String searchText = findField.getText();
    // codeArea.setStyleSpans(0,
    // HighlightService.highlightSearchText(codeArea.getText(), searchText));
    // }

    @FXML
    private void handleSearch() {
        String searchText = findField.getText();
        if (searchText.isEmpty()) {
            return;
        }

        Pattern pattern = Pattern.compile(Pattern.quote(searchText), Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(codeArea.getText());

        matchPositions.clear();
        while (matcher.find()) {
            matchPositions.add(matcher.start());
        }

        if (matchPositions.isEmpty()) {
            findCountLabel.setText("0/0");
            currentMatchIndex = -1;
        } else {
            currentMatchIndex = 0;
            // highlightMatches();
            updatefindCountLabel();
            scrollToCurrentMatch();
        }
    }

    @FXML
    private void handlePrevMatch() {
        if (currentMatchIndex > 0) {
            currentMatchIndex--;
            scrollToCurrentMatch();
            updatefindCountLabel();
        }else if(currentMatchIndex == 0){
            currentMatchIndex = matchPositions.size() - 1;
            scrollToCurrentMatch();
            updatefindCountLabel();
        }
    }

    @FXML
    private void handleNextMatch() {
        if (currentMatchIndex < matchPositions.size() - 1) {
            currentMatchIndex++;
            scrollToCurrentMatch();
            updatefindCountLabel();
        }
        else if(currentMatchIndex == matchPositions.size() - 1){
            currentMatchIndex = 0;
            scrollToCurrentMatch();
            updatefindCountLabel();

        }
    }

    private void updatefindCountLabel() {
        findCountLabel.setText((currentMatchIndex + 1) + "/" + matchPositions.size());
    }

    public void highlightMatches() {
        String searchText = findField.getText();
        StyleSpans<Collection<String>> existingSpans = codeArea.getStyleSpans(0, codeArea.getLength());
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        int lastEnd = 0;

        for (int position : matchPositions) {
            spansBuilder.add(Collections.emptyList(), position - lastEnd);
            spansBuilder.add(Collections.singleton("highlight"), searchText.length());
            lastEnd = position + searchText.length();
        }
        spansBuilder.addAll(existingSpans.subView(lastEnd, codeArea.getLength()));

        StyleSpans<Collection<String>> spans = spansBuilder.create();
        codeArea.setStyleSpans(0, spans);
    }

    private void scrollToCurrentMatch() {
        if (currentMatchIndex >= 0 && currentMatchIndex < matchPositions.size()) {
            int position = matchPositions.get(currentMatchIndex);
            codeArea.showParagraphAtTop(codeArea.offsetToPosition(position, null).getMajor());
            codeArea.selectRange(position, position + findField.getText().length());
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

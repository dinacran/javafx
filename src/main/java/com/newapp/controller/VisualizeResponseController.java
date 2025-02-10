package com.newapp.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.newapp.services.ApiService;
import com.newapp.services.HighlightService;
import com.newapp.services.TokenService;

import javafx.animation.TranslateTransition;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.util.Duration;

public class VisualizeResponseController {

    @FXML
    private VBox page;

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
    private HBox findContainer;

    @FXML
    private SVGPath findSvg;

    @FXML
    private HBox findHeader;

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

    @FXML
    private Button toggleSearchButton;

    private String response;
    private String request;
    private String bundler;

    private List<Integer> matchPositions = new ArrayList<>();
    private int currentMatchIndex = 0;

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

        page.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.isControlDown() && event.getCode() == KeyCode.F) {
                String selectedText = codeArea.getSelectedText();
                if (!selectedText.isEmpty()) {
                    findField.setText(selectedText);
                }
                if (!findHeader.isVisible() || (findHeader.isVisible() && selectedText.isEmpty()))
                    toggleSearch();
                handleSearch();
                event.consume();
            }
        });
    }

    @FXML
    private void fetchData() throws Exception {
        response = null;
        request = null;
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

        switchData();
    }

    @FXML
    public void switchData() throws Exception {

        switch (((RadioButton)dataToViewGroup.getSelectedToggle()).getText()) {
            case "Response":
                if (response == null) {
                    showAlert( "Response data is empty");
                    return;
                }
                setDataToContainer(response);
                break;
            case "Request":
                if (request == null) {
                    showAlert("Request data is empty");
                    return;
                }
                setDataToContainer(request);
                break;
            case "Bundler":
                if (bundler == null) {
                    showAlert("Bundler data is empty");
                    return;
                }
                setDataToContainer(bundler);
                break;
        }
        handleSearch();
    }

    private void setDataToContainer(String jsonData) throws Exception {

        codeArea.replaceText(jsonData);
        codeArea.setStyleSpans(0, HighlightService.computeHighlighting(jsonData));
    }

    @FXML
    private void toggleSearch() {
        boolean isVisible = findHeader.isVisible();
        if (isVisible) {
            TranslateTransition slideOut = new TranslateTransition(Duration.millis(300), findContainer);
            slideOut.setFromX(0);
            slideOut.setToX(300);
            slideOut.setOnFinished(event -> {
                findHeader.setVisible(false);
                findHeader.setManaged(false);
                findContainer.setTranslateX(0);
                findSvg.setContent(
                        "M643.31-142.39 701.69-84h-466q-28.25 0-49.47-21.22T165-154.69v-650.62q0-28.25 21.22-49.47T235.69-876h356.39L795-639.69v482.84q0 12.8-6.46 26.4-6.46 13.6-18.16 23.06L560.23-316.92q-19.23 12.38-38.87 17.19-19.63 4.81-41.35 4.81-60.13 0-102.3-41.78-42.17-41.78-42.17-102.18 0-60.41 42.12-102.69 42.12-42.27 102.18-42.27 60.06 0 102.34 42.2 42.28 42.21 42.28 102.26 0 21.24-6.31 42.12-6.31 20.87-18.54 37.49l137 138.39v-398.93l-168.83-197.3H235.69q-4.61 0-8.46 3.84-3.84 3.85-3.84 8.46v650.62q0 4.61 3.84 8.46 3.85 3.84 8.46 3.84h407.62ZM480.12-353.31q36.73 0 61.34-27.97 24.62-27.98 24.62-65.74 0-33.29-25.86-55.86-25.85-22.58-60.34-22.58-34.5 0-60.23 22.49-25.73 22.49-25.73 56.08 0 38.04 24.74 65.81 24.74 27.77 61.46 27.77Zm-.12-104Zm0 0Z");
            });
            slideOut.play();
        } else { 
            findSvg.setContent(
                    "M535.85-481.23 360.54-657.31q-7.16-6.38-7.04-15.11.12-8.73 7.27-15.12 6.15-7.15 15.11-7.15 8.97 0 16.12 7.15l182.85 182.85q4.23 4.23 7.61 10.34 3.39 6.12 3.39 13.12 0 7.23-3.39 13.73-3.38 6.5-7.61 10.73L391.77-273.69q-6.39 6.38-15.62 6.65-9.23.27-15.38-6.88-7.15-7.16-7.15-15.62 0-8.46 7.15-15.61l175.08-176.08Z");
            TranslateTransition slideIn = new TranslateTransition(Duration.millis(300), findContainer);
            slideIn.setFromX(200);
            slideIn.setToX(0);
            findHeader.setVisible(true);
            findHeader.setManaged(true);
            findContainer.setTranslateX(findContainer.getWidth());
            slideIn.setOnFinished(event -> findField.requestFocus());
            slideIn.play();
        }
    }

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
            currentMatchIndex = 0;
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
        } else if (currentMatchIndex == 0) {
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
        } else if (currentMatchIndex == matchPositions.size() - 1) {
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
            int paragraph = codeArea.offsetToPosition(position, null).getMajor();
            codeArea.showParagraphAtTop(paragraph);
            codeArea.selectRange(position, position + findField.getText().length());
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

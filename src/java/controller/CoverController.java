package controller;

import carnet.Carnet;
import carnet.Cover;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class CoverController implements Observateur{
    @FXML
    private TextField title;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private TextField author;
    @FXML
    private TextArea participants;
    @FXML
    private Label pageNumber;
    @FXML
    private ImageView leftArrow;
    @FXML
    private ImageView rightArrow;

    private final Stage stage;
    private final Carnet carnet;

    public CoverController(Stage stage, Carnet carnet) {
        this.stage = stage;
        this.carnet = carnet;
        carnet.addObserver(this);
    }

    @FXML
    private void initialize() {
        pageNumber.setText("1/" + carnet.getNbPages());
        adjustFontSize();
    }

    @FXML
    private void adjustFontSize() {
        double maxWidth = title.getWidth();
        double minFontSize = 12.0; // Minimum allowed font size
        double maxFontSize = 150.0; // Maximum allowed font size

        // Calculate target font size based on text length
        double targetFontSize = Math.max(minFontSize, calculateFontSize(title.getText(), maxWidth));
        // Gradually adjust font size towards the target
        double currentFontSize = title.getFont().getSize();
        double adjustment = (targetFontSize - currentFontSize) / 3.8; // Adjust in steps of 0.2

        title.setFont(Font.font(title.getFont().getName(), Math.min(currentFontSize + adjustment, maxFontSize)));
    }

    private double calculateFontSize(String text, double maxWidth) {
        return maxWidth / Math.abs(text.length() - 3);
    }

    /**
     * saves current page and either goes to next or creates a new page
     */
    @FXML
    public void turnPageRight(){
        savePage();
        switchScenes();
        carnet.nextPage();
        carnet.notifyObservers();
    }

    /**
     * saves the page to the model
     */
    private void savePage(){
        Cover cover = (Cover) carnet.getCurrentPage();
        carnet.setTitle(title.getText());
        cover.setAuthor(author.getText());
        cover.setParticipants(participants.getText());
        carnet.setStartDate(startDatePicker.getValue());
        carnet.setEndDate(endDatePicker.getValue());
    }

    private void switchScenes() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/xml/dayPage.fxml"));
        DayPageController dc = new DayPageController(stage, carnet);
        loader.setController(dc);

        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void reagir() {
        if (carnet.getCurrentPageNb() == 1) {
            Cover cover = (Cover) carnet.getCurrentPage();
            title.setText(carnet.getTitle());
            startDatePicker.setValue(carnet.getStartDate());
            endDatePicker.setValue(carnet.getEndDate());
            author.setText(cover.getAuthor());
            participants.setText(cover.getParticipants());
            pageNumber.setText("1/" + carnet.getNbPages());

        }
    }
}

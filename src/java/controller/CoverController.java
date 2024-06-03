package controller;

import carnet.Carnet;
import carnet.Cover;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import outils.DateChecker;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import static outils.FontResizer.adjustFontSize;

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

    @FXML
    private VBox background;  // to change focus at the beginning

    private final Stage stage;
    private final Carnet carnet;

    public CoverController(Stage stage, Carnet carnet) {
        this.stage = stage;
        this.carnet = carnet;
        carnet.addObserver(this);
    }

    @FXML
    private void initialize() {
        Platform.runLater( () -> background.requestFocus());  // to not highlight the title
        stage.setOnCloseRequest(e -> {
            e.consume();
            short saveBeforeExit = verifyCarnet();
            if (saveBeforeExit == 1) {
                export();
                Platform.runLater(stage::close);
            } else if (saveBeforeExit == 0) {
                Platform.exit();
            }
        });

        pageNumber.setText("1/" + carnet.getNbPages());

        title.textProperty().addListener((observable, oldValue, newValue) -> adjustFontSize(title));
        title.widthProperty().addListener((observable, oldValue, newValue) -> adjustFontSize(title));
        adjustFontSize(title);
    }


    /**
     * saves current page and either goes to next or creates a new page
     */
    @FXML
    public void turnPageRight(){
        savePage();
        carnet.nextPage();
        switchScenes();
        carnet.notifyObservers();
    }

    @FXML
    public void setMinDate() {
        DateChecker.setEndDateBounds(endDatePicker, startDatePicker.getValue());
    }

    @FXML
    public void setMaxDate() {
        DateChecker.setBeginDateBounds(startDatePicker, endDatePicker.getValue());
    }

    /**
     * saves the page to the model
     */
    private void savePage(){
        Cover cover = carnet.getCover();
        cover.setTitle(title.getText());
        cover.setAuthor(author.getText());
        cover.setParticipants(participants.getText());
        if (startDatePicker.getValue() != null) {
            cover.setStartDate(startDatePicker.getValue());
        }
        if (endDatePicker.getValue() != null) {
            cover.setEndDate(endDatePicker.getValue());
        }
    }

    @FXML
    public void load() {
        //verifyCarnet();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a JSON file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            carnet.importCarnet(file.getPath());
        }
    }

    @FXML
    public void createNew() {
        short saveBeforeNew = verifyCarnet();
        if (saveBeforeNew == 1) {
            export();
            carnet.copyCarnet(new Carnet());
        } else if (saveBeforeNew == 0) {
            carnet.copyCarnet(new Carnet());
        }
        Platform.runLater( () -> background.requestFocus());
    }

    private short verifyCarnet() {
        savePage();
        if (!carnet.isNew()) {
            Alert alert =
                    new Alert(Alert.AlertType.WARNING,
                            "Your data will be lost. Do you want to save your diary before exit?",
                            ButtonType.YES,
                            ButtonType.NO,
                            ButtonType.CANCEL);
            alert.setTitle("Data lost warning");
            Optional<ButtonType> result = alert.showAndWait();

            if(result.isPresent()) {
                if (result.get() == ButtonType.YES) {
                    return 1;
                } else if (result.get() == ButtonType.NO) {
                    return 0;
                } else if (result.get() == ButtonType.CANCEL) {
                    return -1;
                }
            }
        }
        return 0;
    }

    private void export() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an location");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files", "*.json"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            carnet.export(file.getAbsolutePath());
        }
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
            Cover cover = carnet.getCover();
            title.setText(cover.getTitle());
            adjustFontSize(title);
            author.setText(cover.getAuthor());
            participants.setText(cover.getParticipants());
            pageNumber.setText("1/" + carnet.getNbPages());

            if (cover.getStartDate() != null) {
                startDatePicker.setValue(cover.getStartDate());
                DateChecker.setBeginDateBounds(endDatePicker, startDatePicker.getValue());
            } else {
                DateChecker.setBeginDateBounds(startDatePicker, LocalDate.MAX);
                startDatePicker.getEditor().clear();
            }
            if (cover.getEndDate() != null) {
                endDatePicker.setValue(cover.getEndDate());
                DateChecker.setEndDateBounds(endDatePicker, startDatePicker.getValue());
            } else {
                DateChecker.setEndDateBounds(endDatePicker, LocalDate.MIN);
                endDatePicker.getEditor().clear();
            }
        }
    }
}

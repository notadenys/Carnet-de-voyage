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
    private ImageView newCarnet;
    @FXML
    private ImageView loadCarnet;

    @FXML
    private VBox background;  // to change focus at the beginning

    private final Stage stage;
    private final Carnet carnet;

    public CoverController(Stage stage, Carnet carnet) {
        this.stage = stage;
        this.carnet = carnet;
        carnet.addObserver(this);
    }

    /**
     * to set everything up before rendering
     */
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

        title.textProperty().addListener((observable, oldValue, newValue) -> adjustFontSize(title, 20, 150));
        adjustFontSize(title, 20, 150);

        Tooltip.install(leftArrow, new Tooltip("Turn page left"));
        Tooltip.install(rightArrow, new Tooltip("Turn page right"));
        Tooltip.install(newCarnet, new Tooltip("Create a new carnet"));
        Tooltip.install(loadCarnet, new Tooltip("Import a new carnet from JSON"));
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

    /**
     * to set date contraints when button on the datepicker is pressed
     */
    @FXML
    public void setMinDate() {
        DateChecker.setEndDateBounds(endDatePicker, startDatePicker.getValue());
    }

    /**
     * to set date contraints when button on the datepicker is pressed
     */
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

    /**
     * call to loadCarnet to read from JSON
     */
    @FXML
    public void load() {
        short saveBeforeImport = verifyCarnet();
        if (saveBeforeImport == 1) {
            export();
            loadCarnet();
        } else if (saveBeforeImport == 0) {
            loadCarnet();
        }
    }

    /**
     * select a JSON file and read from it
     */
    private void loadCarnet() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a JSON file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            carnet.importCarnet(file.getPath());
        }
    }

    /**
     * create a new carnet
     */
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

    /**
     * check if saving the carnet to a file is necessary and call a dialog window
     * @return 1 to save, 0 to continue without saving, -1 to cancel
     */
    private short verifyCarnet() {
        savePage();
        if (!carnet.isNew() && !carnet.isSaved()) {
            Alert alert =
                    new Alert(Alert.AlertType.WARNING,
                            "Your data will be lost. Do you want to save your diary?",
                            ButtonType.YES,
                            ButtonType.NO,
                            ButtonType.CANCEL);
            alert.setTitle("Data loss warning");
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

    /**
     * select a location to write
     */
    private void export() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an location");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files", "*.json"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            carnet.export(file.getAbsolutePath());
        }
    }

    /**
     * sets a new scene to show
     */
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
            adjustFontSize(title, 20, 150);
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

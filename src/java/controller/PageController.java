package controller;

import carnet.Carnet;
import carnet.PageDay;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;

import java.io.File;

public class PageController implements Observateur {
    @FXML
    private TextField title;
    @FXML
    private TextArea description;
    @FXML
    private Button imgButton;
    @FXML
    private Rectangle photo;

    private final Carnet carnet;

    public PageController(Carnet carnet) {
        this.carnet = carnet;
        carnet.addObserver(this);
    }

    @FXML
    private void initialize() {
    }

    @FXML
    private void openImage()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("IMG Files", "*.png", "*.jpg", "*.bmp"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            photo.setFill(new ImagePattern(image));
            imgButton.setText("");
        }

    }

    @Override
    public void reagir() {
        if (carnet.getCurrentPage() != 0) {
            PageDay page = carnet.getCurrentPageDay();
            title.setText(page.getTitle());
            description.setText(page.getText());
        }
    }
}

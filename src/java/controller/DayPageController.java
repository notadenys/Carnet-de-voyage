package controller;

import carnet.Carnet;
import carnet.DayPage;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class DayPageController implements Observateur {
    @FXML
    private TextField title;
    @FXML
    private TextArea description;
    @FXML
    private Button imgButton;
    @FXML
    private Rectangle photo;
    @FXML
    private Label pageNumber;
    @FXML
    private ImageView leftArrow;
    @FXML
    private ImageView rightArrow;


    private final Carnet carnet;

    public DayPageController(Carnet carnet) {
        this.carnet = carnet;
        carnet.addObserver(this);
    }

    /**
     * initial function to set up all the components
     * @throws FileNotFoundException
     */
    @FXML
    private void initialize() throws FileNotFoundException {
        pageNumber.setText(carnet.getCurrentPage() + "/" + carnet.getNbPages());

        setArrows();
    }

    /**
     * opens filechooser and passes selected photo to setPhoto()
     */
    @FXML
    private void openImage()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("IMG Files", "*.png", "*.jpg", "*.bmp"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            setPhoto(file.toURI().toString());
        }
    }

    /**
     * sets selected photo in its place on a screen
     * @param photoURL
     */
    private void setPhoto(String photoURL)
    {
        carnet.getCurrentDayPage().setPhoto(photoURL);
        Image image = new Image(photoURL);
        photo.setFill(new ImagePattern(image));
        imgButton.setText("");
        carnet.getCurrentDayPage().setPhoto(photoURL);
    }

    /**
     * saves current page and goes to the previous one (if exists)
     */
    @FXML
    public void turnPageLeft(){
        savePage();
        if (carnet.getCurrentPage() > 1) carnet.previousPage();
        carnet.notifyObservers();
    }

    /**
     * saves current page and either goes to next or creates a new page
     */
    @FXML
    public void turnPageRight(){
        savePage();
        if (carnet.getCurrentPage() < carnet.getNbPages()) {
            carnet.nextPage();
        } else if (!carnet.getCurrentDayPage().equals(new DayPage())){
            carnet.createPage();
            carnet.nextPage();
        }
        carnet.notifyObservers();
    }

    /**
     * saves the page to the model
     */
    private void savePage(){
        DayPage dayPage = carnet.getCurrentDayPage();
        dayPage.setTitle(title.getText());
        dayPage.setText(description.getText());
    }

    /**
     * sets specific image on arrows dependent on the conditions
     */
    private void setArrows() {
        try {
            Image leftArrowImage;
            if (carnet.getCurrentPage() == 1) {
                leftArrowImage = new Image(new FileInputStream("src/ressources/img/left-arrow-disabled.png"));
            } else {
                leftArrowImage = new Image(new FileInputStream("src/ressources/img/left-arrow.png"));
            }
            leftArrow.setImage(leftArrowImage);

            Image rightArrowImage;
            if (carnet.getCurrentPage() == carnet.getNbPages()) {
                rightArrowImage = new Image(new FileInputStream("src/ressources/img/right-arrow-add.png"));
            } else {
                rightArrowImage = new Image(new FileInputStream("src/ressources/img/right-arrow.png"));
            }
            rightArrow.setImage(rightArrowImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void reagir() {
        DayPage page = carnet.getCurrentDayPage();
        if (title.getText().isEmpty()) title.setPromptText("Add Title");
        else title.setText(page.getTitle());
        if (description.getText().isEmpty()) description.setPromptText("Write your notes here...");
        else description.setText(page.getTitle());

        pageNumber.setText(carnet.getCurrentPage() + "/" + carnet.getNbPages());

        if (page.getExistsPhoto()) {
            setPhoto(page.getPhoto());
        } else {
            photo.setFill(null);
            imgButton.setText("Add Image");
        }
        setArrows();
    }
}

package controller;

import carnet.Carnet;
import carnet.DayPage;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

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
    @FXML
    private Slider emotions;

    private final Stage stage;
    private final Carnet carnet;

    public DayPageController(Stage stage, Carnet carnet) {
        this.stage = stage;
        this.carnet = carnet;
        carnet.addObserver(this);
    }

    /**
     * initial function to set up all the components
     * @throws FileNotFoundException
     */
    @FXML
    private void initialize() throws FileNotFoundException {
        DayPage page = ((DayPage)carnet.getCurrentPage());
        pageNumber.setText(page.getNbPage() + "/" + carnet.getNbPages());
        emotions.setValue(page.getEmotions());
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
        setArrows();
    }

    @FXML
    public void checkArrows() {
        savePage();
        setArrows();
    }

    /**
     * sets selected photo in its place on a screen
     * @param photoURL
     */
    private void setPhoto(String photoURL)
    {
        DayPage page = ((DayPage)carnet.getCurrentPage());
        page.setPhoto(photoURL);
        Image image = new Image(photoURL);
        photo.setFill(new ImagePattern(image));
        imgButton.setText("");
        page.setPhoto(photoURL);
    }

    /**
     * saves current page and goes to the previous one (if exists)
     */
    @FXML
    public void turnPageLeft(){
        savePage();
        carnet.previousPage();
        if (carnet.getCurrentPageNb() == 1) {
            switchScenes();
        }
        carnet.notifyObservers();
    }

    /**
     * saves current page and either goes to next or creates a new page
     */
    @FXML
    public void turnPageRight(){
        savePage();
        DayPage page = ((DayPage)carnet.getCurrentPage());
        if (page.getNbPage() < carnet.getNbPages()) {
            carnet.nextPage();
        } else if (!page.equals(new DayPage(page.getNbPage()))) {
            carnet.createPage();
            carnet.nextPage();
        }
        carnet.notifyObservers();
    }

    @FXML
    public void goToCover() {
        savePage();
        carnet.setCurrentPage(1);
        switchScenes();
        carnet.notifyObservers();
    }

    /**
     * saves the page to the model
     */
    private void savePage() {
        DayPage dayPage = (DayPage)carnet.getCurrentPage();
        dayPage.setTitle(title.getText());
        dayPage.setText(description.getText());
        dayPage.setEmotions((int) emotions.getValue());
    }

    private void switchScenes() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/xml/cover.fxml"));
        CoverController cc = new CoverController(stage, carnet);
        loader.setController(cc);

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

    /**
     * sets specific image on arrows dependent on the conditions
     */
    private void setArrows() {
        try {
            Image leftArrowImage = new Image(new FileInputStream("src/ressources/img/left-arrow.png"));
            leftArrow.setImage(leftArrowImage);

            Image rightArrowImage;
            DayPage page = ((DayPage)carnet.getCurrentPage());
            if (carnet.getCurrentPageNb() == carnet.getNbPages()) {
                if (page.equals(new DayPage(page.getNbPage()))) {
                    rightArrowImage = new Image(new FileInputStream("src/ressources/img/right-arrow-disabled.png"));
                } else {
                    rightArrowImage = new Image(new FileInputStream("src/ressources/img/right-arrow-add.png"));
                }
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
        if (carnet.getCurrentPageNb() > 1)
        {
            DayPage page = (DayPage)carnet.getCurrentPage();
            title.setText(page.getTitle());
            description.setText(page.getText());
            pageNumber.setText(carnet.getCurrentPageNb() + "/" + carnet.getNbPages());
            emotions.setValue(page.getEmotions());

            if (!page.getPhoto().isEmpty()) {
                setPhoto(page.getPhoto());
            } else {
                photo.setFill(null);
                imgButton.setText("Add Image");
            }
            setArrows();
        }
    }
}

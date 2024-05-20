import carnet.Carnet;
import controller.CoverController;
import controller.DayPageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/xml/cover.fxml"));
        Carnet carnet = new Carnet();
        CoverController cc = new CoverController(stage, carnet);
        loader.setController(cc);

        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Carnet");
        stage.show();
    }
}

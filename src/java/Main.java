import carnet.Carnet;
import controller.PageController;
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
        loader.setLocation(getClass().getResource("/xml/carnet.fxml"));
        Carnet carnet = new Carnet();
        PageController pc = new PageController(carnet);
        loader.setController(pc);

        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Carnet");
        stage.show();
    }
}

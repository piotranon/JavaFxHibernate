import controllers.bots;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class test extends Application {
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/bots.fxml"));
        Parent root = loader.load();
        bots controller=(bots) loader.getController();

        System.out.println(controller.toString());

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setTitle("Customer Bots");
        stage.setScene(scene);
        stage.show();
    }
}

import controllers.customers;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.*;


public class gui extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
//        System.out.println(getClass().getResource(".fxml").toString());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("scenes/customers.fxml"));
        Parent root = loader.load();
        //scene
        customers controller=(customers) loader.getController();
        controller.setParentStage(primaryStage);
        primaryStage.setTitle("JavaFX Hibernate");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}

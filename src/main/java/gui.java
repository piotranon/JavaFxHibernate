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
//
//        opisane ze ni9e powinno pracowac sie na set<>
//            slaba optymalizacja (moze przy wiekszej ilosci danych szybko pamiec sie przeladowac)
//        
//        https://vladmihalcea.com/hibernate-multiplebagfetchexception/
//
//

    }

    public static void main(String[] args) {
        launch(args);
    }
}

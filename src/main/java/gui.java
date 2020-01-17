import controllers.customers;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.*;


public class gui extends Application {

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) throws Exception{
//        System.out.println(getClass().getResource(".fxml").toString());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("scenes/customers.fxml"));
        Parent root = loader.load();
        //scene
        customers controller=(customers) loader.getController();
        controller.setParentStage(primaryStage);
        primaryStage.setTitle("JavaFX Hibernate");
        // no toolbar
        primaryStage.initStyle(StageStyle.UNDECORATED);

        //move window easly
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            }
        });
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

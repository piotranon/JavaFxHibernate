package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import entity.Bot;
import entity.Customer;
import entity.Function;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.hibernateSession;

public class editbots {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField name;

    @FXML
    private ChoiceBox<String> function;

    @FXML
    private Label price;

    @FXML
    private Button editBot;

    @FXML
    private Button cancel;

    private Customer c;
    private Bot b;

    private double xOffset=0;
    private double yOffset=0;
    private List<Function> functions;

    @FXML
    void Edit(ActionEvent event) throws IOException {
        StringBuilder errors=new StringBuilder();
        boolean missingdata=false;
        if(name.getText().length()<=0)
        {
            errors.append("Name is missing. \r\n");
            missingdata=true;
        }
        if(function.getSelectionModel().getSelectedItem()==null)
        {
            errors.append("Choose the Function");
            missingdata=true;
        }
        if(function.getSelectionModel().getSelectedItem()=="New")
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/addFunction.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            // no toolbar
            stage.initStyle(StageStyle.UNDECORATED);
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
                    stage.setX(event.getScreenX() - xOffset);
                    stage.setY(event.getScreenY() - yOffset);
                }
            });
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner((Stage) cancel.getScene().getWindow());
            stage.setScene(scene);
            stage.showAndWait();

            SessionFactory sessionFactory = hibernateSession.getSessionFactory();
            Session session = sessionFactory.openSession();
            session.beginTransaction();

            functions=hibernateSession.loadAllData(Function.class,session);

            session.close();
            function.getItems().clear();
            function.getItems().add("New");
            for(int i=0;i<functions.size();i++)
            {
                function.getItems().add(functions.get(i).getName());
            }
        }
        if(missingdata)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Not all data has been provided");
            alert.setHeaderText("Input data ERROR");
            alert.setContentText(errors.toString());
            alert.show();
        }else
        {
            int i;
            for(i=0;i<functions.size();i++)
            {
                if(functions.get(i).getName().equals(function.getSelectionModel().getSelectedItem()))
                    break;
            }

            b.setName(name.getText());
            b.setFunctions(functions.get(i));

            SessionFactory sessionFactory = hibernateSession.getSessionFactory();
            Session session = sessionFactory.openSession();
            session.beginTransaction();

            session.update(b);

            session.getTransaction().commit();
            session.close();

            cancel(event);
        }
    }

    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void initialize() {
        SessionFactory sessionFactory = hibernateSession.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        functions=hibernateSession.loadAllData(Function.class,session);
        session.close();
        function.getItems().add("New");
        for(int i=0;i<functions.size();i++)
        {
            function.getItems().add(functions.get(i).getName());
        }
    }

    void setCustomerData(Customer c)
    {
        this.c=c;
    }

    void setBot(Bot b){
        this.b=b;
        name.setText(b.getName());
        function.getSelectionModel().select(b.getFunctions().getName());
    }
}

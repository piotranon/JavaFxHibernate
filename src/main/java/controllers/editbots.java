package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import entity.Bot;
import entity.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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
    private TextField function;

    @FXML
    private Button editBot;

    @FXML
    private Button cancel;

    private Customer c;
    private Bot b;

    @FXML
    void Edit(ActionEvent event) {
        StringBuilder errors=new StringBuilder();
        boolean missingdata=false;
        if(name.getText().length()<=0)
        {
            errors.append("Name is missing. \r\n");
            missingdata=true;
        }
        if(function.getText().length()<=0)
        {
            errors.append("Function is missing. \r\n");
            missingdata=true;
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
            List<Bot> bots=new ArrayList<Bot>(c.getBots());

            Bot edited=bots.get(bots.indexOf(b));

            edited.setName(name.getText());
//            edited.setFunctions(function.getText());

            SessionFactory sessionFactory = hibernateSession.getSessionFactory();
            Session session = sessionFactory.openSession();
            session.beginTransaction();

            session.update(c);
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
    }

    void setCustomerData(Customer c)
    {
        this.c=c;
    }

    void setBot(Bot b){
        this.b=b;
        name.setText(b.getName());
//        function.setText(b.getFunctions());
    }
}

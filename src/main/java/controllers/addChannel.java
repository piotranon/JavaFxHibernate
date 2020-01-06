package controllers;

import java.net.URL;
import java.util.ResourceBundle;


import entity.Channel;
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

public class addChannel {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField name;

    @FXML
    private TextField description;

    @FXML
    private Button cancel;

    @FXML
    void Add(ActionEvent event) {
        StringBuilder errors=new StringBuilder();
        boolean missingdata=false;
        if(name.getText().length()<=0)
        {
            errors.append("Name is missing. \r\n");
            missingdata=true;
        }
        if(description.getText().length()<=0)
        {
            errors.append("Description is missing. \r\n");
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
            Channel channel=new Channel();

            channel.setName(name.getText());
            channel.setDescription(description.getText());
            channel.setCustomer(c);

            c.getChannels().add(channel);

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
        assert name != null : "fx:id=\"name\" was not injected: check your FXML file 'addChannel.fxml'.";
        assert description != null : "fx:id=\"description\" was not injected: check your FXML file 'addChannel.fxml'.";

    }
    private Customer c;
    void setCustomerData(Customer c)
    {
        this.c=c;
    }
}

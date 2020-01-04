package controllers;

import java.net.URL;
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
    private Bot bot;


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
            Bot bot2=new Bot();
            bot2.setName(name.getText());
            bot2.setFunctions(function.getText());
            bot2.setCustomer(c);

            List<Bot> botList=c.getBots();
            botList.remove(bot);
            botList.add(bot2);
            c.setBots(botList);

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
    void setBotData(Bot bot){
        this.bot=bot;
        name.setText(bot.getName());
        function.setText(bot.getFunctions());
    }

}

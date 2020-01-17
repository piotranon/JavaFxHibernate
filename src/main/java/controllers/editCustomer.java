package controllers;

import entity.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.hibernateSession;

import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class editCustomer {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField name;

    @FXML
    private TextField surname;

    @FXML
    private TextField pin;

    @FXML
    private DatePicker date_joined;

    @FXML
    private Button cancel;

    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void updateCustomer(ActionEvent event) {

        boolean missingdata=false;
        StringBuilder errors=new StringBuilder();
        if(name.getText().length()<=0)
        {
            errors.append("Name is missing. \r\n");
            missingdata=true;
        }
        if(surname.getText().length()<=0)
        {
            errors.append("Surname is missing. \r\n");
            missingdata=true;
        }
        if(pin.getText().length()!=11)
        {
            try{
                Long l=Long.parseLong(pin.getText());
            }catch (NumberFormatException e)
            {
                errors.append("Pin should only include numbers. \r\n");
            }
            errors.append("Pin length is wrong. \r\n");
            missingdata=true;
        }
        if(LocalDate.now().isBefore(date_joined.getValue()))
        {
            errors.append("Date cannot be in future.\r\n");
            missingdata=true;
        }

        boolean updated=false;
        StringBuilder changed=new StringBuilder();
        Customer copy=new Customer(c);
        if(!name.getText().equals(c.getCustomer_name()))
        {
            copy.setCustomer_name(name.getText());
            changed.append("name: "+c.getCustomer_name()+" to : "+name.getText()+"\r\n");
            updated=true;
        }
        if(!surname.getText().equals(c.getCustomer_surname()))
        {
            copy.setCustomer_surname(surname.getText());
            changed.append("surname: "+c.getCustomer_surname()+" to : "+surname.getText()+"\r\n");
            updated=true;
        }
        if(!date_joined.getValue().isEqual(c.getDate_joined().toLocalDate()))
        {
            copy.setDate_joined(java.sql.Date.valueOf(date_joined.getValue()));
            changed.append("date_joined: "+c.getDate_joined().toLocalDate()+" to : "+date_joined.getValue()+"\r\n");
            updated=true;
        }
        if(missingdata)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Not all data has been provided");
            alert.setHeaderText("Input data ERROR");
            alert.setContentText(errors.toString());
            alert.show();
        }
        if(!missingdata && updated)
        {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Update Customer");
            alert.setHeaderText("Are you sure that you want to update customer with specified data.");

            alert.setContentText("Name: "+c.getCustomer_name()+"\r\nSurname: "+c.getCustomer_surname()+ "\r\nDate_joined: "+c.getDate_joined()+"\r\n\r\nCHANGED DATA:\r\n"+changed.toString());

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                SessionFactory sessionFactory = hibernateSession.getSessionFactory();
                Session session = sessionFactory.openSession();
                session.beginTransaction();
                session.update(copy);
                session.getTransaction().commit();
                session.close();

                cancel(event);
            }
        }
        if(!missingdata && !updated)
        {
            cancel(event);
        }

    }
    private Customer c=new Customer();

    void setCustomerData(Customer c){
        name.setText(c.getCustomer_name());
        surname.setText(c.getCustomer_surname());
        pin.setText(""+c.getCustomer_nip());
        date_joined.setValue(c.getDate_joined().toLocalDate());
        this.c=c;
    }
    @FXML
    void initialize() {
        assert name != null : "fx:id=\"name\" was not injected: check your FXML file 'editCustomer.fxml'.";
        assert surname != null : "fx:id=\"surname\" was not injected: check your FXML file 'editCustomer.fxml'.";
        assert date_joined != null : "fx:id=\"date_joined\" was not injected: check your FXML file 'editCustomer.fxml'.";
        assert cancel != null : "fx:id=\"cancel\" was not injected: check your FXML file 'editCustomer.fxml'.";
    }
}
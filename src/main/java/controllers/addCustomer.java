package controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.Random;
import java.util.ResourceBundle;

import entity.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.hibernateSession;

public class addCustomer {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField name;

    @FXML
    private TextField surname;

    @FXML
    private DatePicker date_joined;

    @FXML
    private Button createNewCustomer;

    @FXML
    private Button cancel;

    @FXML
    private TextField pin;

    @FXML
    void Add(ActionEvent event) {
        StringBuilder errors=new StringBuilder();
        boolean missingdata=false;
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
        if(missingdata)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Not all data has been provided");
            alert.setHeaderText("Input data ERROR");
            alert.setContentText(errors.toString());
            alert.show();
        }else
        {

            Customer customer = new Customer();

            customer.setCustomer_name(name.getText());
            customer.setCustomer_surname(surname.getText());
            customer.setCustomer_nip(Long.parseLong(pin.getText()));
            customer.setDate_joined(java.sql.Date.valueOf(date_joined.getValue()));

            SessionFactory sessionFactory = hibernateSession.getSessionFactory();
            Session session = sessionFactory.openSession();
            session.beginTransaction();
//            System.out.println(customer.toString());
            session.save(customer);
            session.getTransaction().commit();
            session.close();

            cancel(event);
//            System.out.println(customer.toString());

        }
    }
    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }
    @FXML
    void initialize() {
        assert name != null : "fx:id=\"name\" was not injected: check your FXML file 'addCustomer.fxml'.";
        assert surname != null : "fx:id=\"surname\" was not injected: check your FXML file 'addCustomer.fxml'.";
        assert date_joined != null : "fx:id=\"date_joined\" was not injected: check your FXML file 'addCustomer.fxml'.";
        assert createNewCustomer != null : "fx:id=\"createNewCustomer\" was not injected: check your FXML file 'addCustomer.fxml'.";
        assert cancel != null : "fx:id=\"cancel\" was not injected: check your FXML file 'addCustomer.fxml'.";
        String[] names={"liver ","Harry ","Charlie ","Noah ","George ","Jack ","Alfie ","Leo ","Jacob ","Freddie ","Oscar ","Theo ","Archie ","Arthur ","Logan ","Joshua ","Thomas ","James ","Henry ","Max ","Lucas ","Ethan ","William ","Isaac ","Mason ","Riley ","Harrison ","Finley ","Tommy ","Teddy ","Dylan ","Daniel ","Tyler ","Adam ","Joseph ","Alexander ","Elijah ","Jayden ","Louie ","Arlo ","Hunter ","Jake ","Jaxon ","Reggie ","Frankie ","Harley ","Albie ","Harvey ","Toby ","Edward ","Lewis ","Sebastian ","Theodore ","Rory ","Ollie ","Alex ","Reuben ","Benjamin ","Luca ","Ryan ","Liam ","Bobby ","Carter ","Samuel ","Roman ","Louis ","David ","Hugo ","Jude ","Jenson ","Ronnie ","Zachary ","Callum ","Blake ","Jackson ","Ezra ","Kai ","Luke ","Matthew ","Michael ","Caleb ","Connor ","Elliott ","Baby ","Leon ","Jamie ","Grayson ","Nathan ","Ellis ","Stanley ","Finn ","Elliot ","Albert  ","Aaron ","Gabriel ","Cameron ","Ben ","Dexter ","Oakley","Eli"};

        Random r=new Random();
        int a=r.nextInt(names.length-1);
        name.setText(names[a]);
        surname.setText(names[a+1]);
        pin.setText(r.nextInt(10)+""+r.nextInt(10)+""+r.nextInt(10)+""+r.nextInt(10)+""+r.nextInt(10)+""+r.nextInt(10)+""+r.nextInt(10)+""+r.nextInt(10)+""+r.nextInt(10)+""+r.nextInt(10)+""+r.nextInt(10));
        date_joined.setValue(LocalDate.now());
    }
}

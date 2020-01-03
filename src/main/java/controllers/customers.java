package controllers;

import entity.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.hibernateSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class customers {

    @FXML
    private Button customers;

    @FXML
    private TableView<Customer> tableview;

    @FXML
    private TableColumn<Customer, String> name;

    @FXML
    private TableColumn<Customer, String> surname;

    @FXML
    private TableColumn<Customer, Date> join_date;

    @FXML
    private Button newCustomer;

    @FXML
    private TextField search;

    @FXML
    void newCustomer(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/addCustomer.fxml"));
        Parent root = loader.load();
//        addCustomer controller= (addCustomer) loader.getController();
        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setTitle("Create new Customer");
        stage.setScene(scene);
        stage.showAndWait();
        reloaddata();
    }
    @FXML
    void deleteCustomer(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Customer");
        alert.setHeaderText("Are you sure that you want to delete customer with specified data.");

        Customer c=new Customer(tableview.getSelectionModel().getSelectedItem());
        alert.setContentText("Name: "+c.getCustomer_name()+"\r\nSurname: "+c.getCustomer_surname()+ "\r\nDate_joined: "+c.getDate_joined());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){

            SessionFactory sessionFactory = hibernateSession.getSessionFactory();
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.remove(c);
            session.getTransaction().commit();
            session.close();
        }
        reloaddata();
    }

    @FXML
    void editCustomer(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/editCustomer.fxml"));
        Parent root = loader.load();
//        addCustomer controller= (addCustomer) loader.getController();
        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setTitle("Edit Customer");
        stage.setScene(scene);
        editCustomer controller=(editCustomer) loader.getController();
        controller.setCustomerData(tableview.getSelectionModel().getSelectedItem());

        stage.showAndWait();
        reloaddata();
    }
    @FXML
    void reloadDataToView(ActionEvent event) {
        search.setText("");
        reloaddata();
    }

    @FXML
    void limitList(KeyEvent event) {
        Limited.clear();
        String text=search.getText();
        if(search.getText().length()>0) {
            for (int i = 0; i < Customers.size(); i++) {
                boolean was = false;
                if (!was && Customers.get(i).getCustomer_name().contains(text)) {
                    was = true;
                    Limited.add(Customers.get(i));
                }
                if (!was && Customers.get(i).getCustomer_surname().contains(text)) {
                    was = true;
                    Limited.add(Customers.get(i));
                }
                if (!was && Customers.get(i).getDate_joined().toString().contains(text)) {
                    was = true;
                    Limited.add(Customers.get(i));
                }
            }
            setCustomers(Limited);
        }
        else
        {
            setCustomers(Customers);
        }
    }

    @FXML
    void initialize() {
//        assert customers != null : "fx:id=\"customers\" was not injected: check your FXML file 'customers.fxml'.";
//        assert tableview != null : "fx:id=\"tableview\" was not injected: check your FXML file 'customers.fxml'.";
//        assert name != null : "fx:id=\"name\" was not injected: check your FXML file 'customers.fxml'.";
//        assert surname != null : "fx:id=\"surname\" was not injected: check your FXML file 'customers.fxml'.";
//        assert join_date != null : "fx:id=\"join_date\" was not injected: check your FXML file 'customers.fxml'.";

        name.setCellValueFactory(new PropertyValueFactory<Customer, String>("customer_name"));
        surname.setCellValueFactory(new PropertyValueFactory<Customer, String>("customer_surname"));
        join_date.setCellValueFactory(new PropertyValueFactory<Customer, Date>("date_joined"));
        reloaddata();
    }

    private List<Customer> Customers = new ArrayList<Customer>();
    private List<Customer> Limited = new ArrayList<Customer>();
    public void reloaddata(){
        SessionFactory sessionFactory = hibernateSession.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Customers = hibernateSession.loadAllData(Customer.class, session);
        setCustomers(Customers);
        session.close();
    }

    public void setCustomers(List<Customer> customerslist) {

        ObservableList<Customer> customersxml = (ObservableList<Customer>) FXCollections.observableList(customerslist);

        tableview.getItems().clear();
        tableview.getItems().setAll(customersxml);
    }
}

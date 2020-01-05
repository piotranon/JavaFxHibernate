package controllers;

import entity.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.hibernateSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

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

    private Stage parentStage;

    @FXML
    void newCustomer(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/addCustomer.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(parentStage);
        stage.setTitle("Create new Customer");
        stage.setScene(scene);

        System.out.println("++++++++++++++++");
        System.out.println("new customer");
        System.out.println("++++++++++++++++");


        stage.showAndWait();

        System.out.println("++++++++++++++++");

        reloaddata();
    }
    @FXML
    void deleteCustomer(ActionEvent event) {
        System.out.println("--------------------");
        System.out.println("deleting customer");
        System.out.println("--------------------");

        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Customer");
            alert.setHeaderText("Are you sure that you want to delete customer with specified data.");

            Customer c = new Customer(tableview.getSelectionModel().getSelectedItem());
            alert.setContentText("Name: " + c.getCustomer_name() + "\r\nSurname: " + c.getCustomer_surname() + "\r\nDate_joined: " + c.getDate_joined());

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {

                SessionFactory sessionFactory = hibernateSession.getSessionFactory();
                Session session = sessionFactory.openSession();
                session.beginTransaction();
                session.remove(c);
                session.getTransaction().commit();
                session.close();
            }
            reloaddata();
        }catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Select Customer");
            alert.setHeaderText("Something went wrong.");
            alert.setContentText("Select customer first!");
            alert.showAndWait();
        }
        System.out.println("--------------------");
    }

    @FXML
    void editCustomer(ActionEvent event) throws IOException {
        System.out.println("////////////////////////");
        System.out.println("editing customer");
        System.out.println("////////////////////////");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/editCustomer.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(parentStage);

            stage.setTitle("Edit Customer");
            stage.setScene(scene);
            editCustomer controller = (editCustomer) loader.getController();
            controller.setCustomerData(tableview.getSelectionModel().getSelectedItem());
            stage.showAndWait();

            reloaddata();
        }catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Select Customer");
            alert.setHeaderText("Something went wrong.");
            alert.setContentText("Select customer first!");
            alert.showAndWait();
        }
        System.out.println("////////////////////////");
    }
    @FXML
    void reloadDataToView(ActionEvent event) {
        reloaddata();
    }

    @FXML
    void limitList(KeyEvent event) {
        loadview(Customers);
    }

    @FXML
    void editCustomerBots(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/bots.fxml"));
            Parent root = loader.load();
            bots controller=(bots) loader.getController();
            controller.setCustomerData(tableview.getSelectionModel().getSelectedItem());

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setTitle("Customer Bots");
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(parentStage);
            stage.showAndWait();
//            reloaddata();

        }catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Select Customer");
            alert.setHeaderText("Something went wrong.");
            alert.setContentText("Select customer first!");
            alert.showAndWait();
            e.printStackTrace();
        }

    }

    @FXML
    void editCustomerChannels(ActionEvent event) {
        try {

        }catch (NullPointerException e)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Select Customer");
            alert.setHeaderText("Something went wrong.");
            alert.setContentText("Select customer first!");
            alert.showAndWait();
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
    public void reloaddata(){
        System.out.println("----------------------");
        System.out.println("reloading customer data");
        System.out.println("----------------------");
        SessionFactory sessionFactory = hibernateSession.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        setCustomers(hibernateSession.loadAllData(Customer.class, session));

        session.close();
        System.out.println("----------------------");
    }
    public void setParentStage(Stage stage){
        parentStage=stage;
    }

    private List<Customer> Customers= new ArrayList<Customer>();

    public void setCustomers(List<Customer> Customers) {
        this.Customers=Customers;
        loadview(Customers);
    }
    void loadview(List<Customer> Customers){
        System.out.println("---------");
        System.out.println("refreshing customer view");
        System.out.println("---------");
        tableview.setItems(sortedList(Customers));
        System.out.println("---------");
    }

    SortedList<Customer> sortedList(List<Customer> Customers){

        ObservableList<Customer> customersxml = (ObservableList<Customer>) FXCollections.observableList(Customers);
        FilteredList<Customer> filteredList=new FilteredList<Customer>(customersxml);

        Predicate predicate=new Predicate() {
            public boolean test(Object o) {

                Customer c =new Customer((Customer) o);

                if(c.getCustomer_name().toLowerCase().contains(search.getText().toLowerCase()))
                {
                    return true;
                }else if(c.getCustomer_surname().toLowerCase().contains(search.getText().toLowerCase()))
                {
                    return true;
                }else if(c.getDate_joined().toString().toLowerCase().contains(search.getText().toLowerCase()))
                {
                    return true;
                }
                return false;
            }
        };

        filteredList.setPredicate(predicate);

        SortedList<Customer> sortedData = new SortedList<Customer>(filteredList);
        sortedData.comparatorProperty().bind(tableview.comparatorProperty());

        return sortedData;
    }
}

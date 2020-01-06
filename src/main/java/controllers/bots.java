package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.function.Predicate;

import entity.Bot;
import entity.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
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

public class bots {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button newCustomer;

    @FXML
    private TextField search;

    @FXML
    private Label name;

    @FXML
    private Label surname;

    @FXML
    private Label date;

    @FXML
    private TableView<Bot> tableview;

    @FXML
    private TableColumn<Bot, String> Name;

    @FXML
    private TableColumn<Bot, String> Function;

    @FXML
    private Button cancel;

    private Customer c;

    @FXML
    void deleteBot(ActionEvent event) {
        System.out.println("-----------------------");
        System.out.println("deleting bot");
        System.out.println("-----------------------");
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Bot");
            alert.setHeaderText("Are you sure that you want to delete bot with specified data.");
            Bot b=tableview.getSelectionModel().getSelectedItem();
            alert.setContentText("Name: " + b.getName() + "\r\nFunctions: " + b.getFunctions());


            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                c.getBots().remove(b);
                SessionFactory sessionFactory = hibernateSession.getSessionFactory();
                Session session = sessionFactory.openSession();
                session.beginTransaction();
                session.update(c);
                session.getTransaction().commit();
                session.close();
                reloaddata();
                System.out.println("usunieto: "+b.toString());
            }
        }catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Select Bot");
            alert.setHeaderText("Something went wrong.");
            alert.setContentText("Select Bot first!");
            alert.showAndWait();
        }
        System.out.println("-----------------------");
    }

    @FXML
    void editBot(ActionEvent event) throws IOException {
        System.out.println("//////////////////");
        System.out.println("editing bot");
        System.out.println("//////////////////");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/editbots.fxml"));
            Parent root = loader.load();
            editbots controller = (editbots) loader.getController();

            controller.setCustomerData(c);
            controller.setBot(tableview.getSelectionModel().getSelectedItem());

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner((Stage) cancel.getScene().getWindow());
            stage.setTitle("Customer Bots");
            stage.setScene(scene);
            stage.showAndWait();

            reloaddata();
        }catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Select Bot");
            alert.setHeaderText("Something went wrong.");
            alert.setContentText("Select Bot first!");
            alert.showAndWait();
        }
        System.out.println("//////////////////");
    }

    @FXML
    void limitList(KeyEvent event) {
        loadview(Bots);
    }

    @FXML
    void reloadDataToView(ActionEvent event) {
        search.setText("");
        reloaddata();
    }

    @FXML
    void newBot(ActionEvent event) throws IOException {
        System.out.println("+++++++++++++++++");
        System.out.println("new bot");
        System.out.println("+++++++++++++++++");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/addbots.fxml"));
        Parent root = loader.load();
        addbots controller=(addbots) loader.getController();
        controller.setCustomerData(c);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner((Stage)cancel.getScene().getWindow());
        stage.setTitle("Customer Bots");
        stage.setScene(scene);
        stage.showAndWait();

        reloaddata();

        System.out.println("+++++++++++++++++");

    }

    @FXML
    void initialize() {
//        assert newCustomer != null : "fx:id=\"newCustomer\" was not injected: check your FXML file 'bots.fxml'.";
//        assert search != null : "fx:id=\"search\" was not injected: check your FXML file 'bots.fxml'.";
//        assert name != null : "fx:id=\"name\" was not injected: check your FXML file 'bots.fxml'.";
//        assert surname != null : "fx:id=\"surname\" was not injected: check your FXML file 'bots.fxml'.";
//        assert date != null : "fx:id=\"date\" was not injected: check your FXML file 'bots.fxml'.";

        Name.setCellValueFactory(new PropertyValueFactory<Bot, String >("name"));
        Function.setCellValueFactory(new PropertyValueFactory<Bot, String>("Functions"));
    }

    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    public void reloaddata(){
        System.out.println("----------------------");
        System.out.println("reloading customer bots data");
        System.out.println("----------------------");

        SessionFactory sessionFactory = hibernateSession.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        setCustomerData(session.load(Customer.class,c.getId()));

        session.close();
        System.out.println("----------------------");
    }

    private Set<Bot> Bots;

    public void setCustomerData(Customer c) {
        this.c=c;
        Bots=c.getBots();
        name.setText("Name: "+c.getCustomer_name());
        surname.setText("Surname: "+c.getCustomer_surname());
        date.setText("Date_joined: "+c.getDate_joined().toString());

        loadview(Bots);
    }

    void loadview(Set<Bot> Bots)
    {
        System.out.println("---------");
        System.out.println("refreshing bots view");
        System.out.println("---------");
        tableview.setItems(sortedList(Bots));
        System.out.println("---------");
    }

    SortedList<Bot> sortedList(Set<Bot> Bots){

        List<?> test = new ArrayList<Bot>(Bots);
        ObservableList<Bot> botsxml=(ObservableList<Bot>) FXCollections.observableList(test);

        FilteredList<Bot> filteredList=new FilteredList<Bot>(botsxml);

        Predicate predicate=new Predicate() {
            public boolean test(Object o) {

                Bot b =new Bot((Bot) o);

                if(b.getName().toLowerCase().contains(search.getText().toLowerCase()))
                {
                    return true;
                }else if(b.getFunctions().toLowerCase().contains(search.getText().toLowerCase()))
                {
                    return true;
                }
                return false;
            }
        };

        filteredList.setPredicate(predicate);

        SortedList<Bot> sortedData= new SortedList<Bot>(filteredList);
        sortedData.comparatorProperty().bind(tableview.comparatorProperty());

        return sortedData;
    }
}
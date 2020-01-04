package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import entity.Bot;
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

    }

    @FXML
    void editBot(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/editbots.fxml"));
        Parent root = loader.load();
        editbots controller=(editbots) loader.getController();

        controller.setCustomerData(c);
        controller.setBotData(tableview.getSelectionModel().getSelectedItem());

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner((Stage)cancel.getScene().getWindow());
        stage.setTitle("Customer Bots");
        stage.setScene(scene);
        stage.showAndWait();

        reloadDataToView(event);
    }
        private List<Bot> Bots = new ArrayList<Bot>();
        private List<Bot> Limited = new ArrayList<Bot>();
    @FXML
    void limitList(KeyEvent event) {
        Limited.clear();
        String text=search.getText().toLowerCase();
        System.out.println("search: "+search.getText());
        if(search.getText().length()>=0)
        {
            System.out.println("-------");
            System.out.println("size: "+Bots.size());
            System.out.println(Bots.toString());
            for(int i=0;i<Bots.size();i++) {
                boolean was = false;
                if (!was && Bots.get(i).getName().contains(text)) {
                    was = true;
                    Limited.add(Bots.get(i));
                }
                if (!was && Bots.get(i).getFunctions().contains(text)) {
                    was = true;
                    Limited.add(Bots.get(i));
                }
                if (was)
                    System.out.println(Bots.get(i));
            }
            setBots(Limited);
        }
    }

    @FXML
    void reloadDataToView(ActionEvent event) {
        SessionFactory sessionFactory = hibernateSession.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        c=session.load(Customer.class,c.getId());
        Bots=c.getBots();
        Limited.clear();
        setBots(Bots);
        session.close();
    }

    @FXML
    void newBot(ActionEvent event) throws IOException {
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

        System.out.println("----------------------------");
        System.out.println(c.getBots().toString());

        reloadDataToView(event);
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
    public void setBots(List<Bot> botlist)
    {
        ObservableList<Bot> botsxml= (ObservableList<Bot>) FXCollections.observableList(botlist);
        tableview.getItems().clear();
        tableview.setItems(botsxml);
    }

    public void setCustomerData(Customer c) {
        this.c=c;
        setBots(c.getBots());
        Bots=c.getBots();
        name.setText("Name: "+c.getCustomer_name());
        surname.setText("Surname: "+c.getCustomer_surname());
        date.setText("Date_joined: "+c.getDate_joined().toString());
    }
}
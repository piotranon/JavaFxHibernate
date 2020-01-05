package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import entity.Channel;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.hibernateSession;

public class channels {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField search;

    @FXML
    private Button cancel;

    @FXML
    private Label name;

    @FXML
    private Label surname;

    @FXML
    private Label date;

    @FXML
    private TableView<Channel> tableview;

    @FXML
    private TableColumn<Channel, String> Name;

    @FXML
    private TableColumn<Channel, String> Description;

    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void deleteChannel(ActionEvent event) {

    }

    @FXML
    void editChannel(ActionEvent event) {

    }

    @FXML
    void limitList(KeyEvent event) {
        loadview(Channels);
    }

    @FXML
    void newChannel(ActionEvent event) throws IOException {
        System.out.println("+++++++++++++++++");
        System.out.println("new channel");
        System.out.println("+++++++++++++++++");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/addChannel.fxml"));
        Parent root = loader.load();
        addChannel controller=(addChannel) loader.getController();
        controller.setCustomerData(c);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner((Stage)cancel.getScene().getWindow());
        stage.setTitle("Customer Channels");
        stage.setScene(scene);
        stage.showAndWait();

        reloaddata();

        System.out.println("+++++++++++++++++");
    }

    @FXML
    void reloadDataToView(ActionEvent event) {

    }

    @FXML
    void initialize() {
        Name.setCellValueFactory(new PropertyValueFactory<Channel, String>("channel_name"));
        Description.setCellValueFactory(new PropertyValueFactory<Channel, String>("channel_description"));
    }

    private Customer c;
    private List<Channel> Channels = new ArrayList<Channel>();

    public void setCustomerData(Customer c) {
        this.c=c;

        name.setText("Name: "+c.getCustomer_name());
        surname.setText("Surname: "+c.getCustomer_surname());
        date.setText("Date_joined: "+c.getDate_joined().toString());

        loadview(Channels);
    }

    public void reloaddata(){
        System.out.println("----------------------");
        System.out.println("reloading customer channels data");
        System.out.println("----------------------");

        SessionFactory sessionFactory = hibernateSession.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        setCustomerData(session.load(Customer.class,c.getId()));

        session.close();
        System.out.println("----------------------");
    }

    void loadview(List<Channel> Channels)
    {
        System.out.println("---------");
        System.out.println("refreshing channels view");
        System.out.println("---------");
        tableview.setItems(sortedList(Channels));
        System.out.println("---------");
    }

    SortedList<Channel> sortedList(List<Channel> Channels){
        ObservableList<Channel> channelsxml=(ObservableList<Channel>) FXCollections.observableList(Channels);
        FilteredList<Channel> filteredList=new FilteredList<Channel>(channelsxml);

        Predicate predicate=new Predicate() {
            public boolean test(Object o) {

                Channel c =new Channel((Channel) o);

                if(c.getName().toLowerCase().contains(search.getText().toLowerCase()))
                {
                    return true;
                }else if(c.getDescription().toLowerCase().contains(search.getText().toLowerCase()))
                {
                    return true;
                }
                return false;
            }
        };

        filteredList.setPredicate(predicate);

        SortedList<Channel> sortedData= new SortedList<Channel>(filteredList);
        sortedData.comparatorProperty().bind(tableview.comparatorProperty());

        return sortedData;
    }
}

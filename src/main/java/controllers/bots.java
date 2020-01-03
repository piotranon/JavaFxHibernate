package controllers;

        import java.io.IOException;
        import java.net.URL;
        import java.util.List;
        import java.util.Observable;
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

    private Customer c;

    @FXML
    void deleteBot(ActionEvent event) {

    }

    @FXML
    void editBot(ActionEvent event) {

    }

    @FXML
    void limitList(KeyEvent event) {

    }

    @FXML
    void newBot(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/addbots.fxml"));
        Parent root = loader.load();

        addbots controller=(addbots) loader.getController();

        controller.setCustomerData(c);
        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.setTitle("Customer Bots");
        stage.setScene(scene);
        stage.showAndWait();
        reloaddata();
    }

    @FXML
    void reloadDataToView(ActionEvent event) {

    }
    void reloaddata()
    {
        setBots(c.getBots());
    }
    @FXML
    void initialize() {
//        assert newCustomer != null : "fx:id=\"newCustomer\" was not injected: check your FXML file 'bots.fxml'.";
//        assert search != null : "fx:id=\"search\" was not injected: check your FXML file 'bots.fxml'.";
//        assert name != null : "fx:id=\"name\" was not injected: check your FXML file 'bots.fxml'.";
//        assert surname != null : "fx:id=\"surname\" was not injected: check your FXML file 'bots.fxml'.";
//        assert date != null : "fx:id=\"date\" was not injected: check your FXML file 'bots.fxml'.";

        Name.setCellValueFactory(new PropertyValueFactory<Bot, String >("bot_name"));
        Function.setCellValueFactory(new PropertyValueFactory<Bot, String>("bot_functions"));
        reloaddata();
    }
    public void setBots(List<Bot> botlist)
    {
        ObservableList<Bot> botsxml= (ObservableList<Bot>) FXCollections.observableList(c.getBots());
        tableview.getItems().clear();
        tableview.setItems(botsxml);
    }

    public void setCustomerData(Customer c) {

        this.c=c;
        name.setText(c.getCustomer_name());
        surname.setText(c.getCustomer_surname());
        date.setText(c.getDate_joined().toString());
    }
}

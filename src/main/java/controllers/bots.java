package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.function.Predicate;

import entity.Bot;
import entity.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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

    @FXML
    private Button close;

    private Customer c;
    private double xOffset;
    private double yOffset;

    @FXML
    void close(ActionEvent event) {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }
    @FXML
    void cancel(ActionEvent event) throws IOException {
        xOffset=0;
        yOffset=0;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/customers.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        //scene
        customers controller=(customers) loader.getController();
        stage.setTitle("JavaFX Hibernate");
        // no toolbar
        stage.initStyle(StageStyle.UNDECORATED);

        //move window easly
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
        stage.setScene(new Scene(root));
        stage.show();

        Stage stag = (Stage) cancel.getScene().getWindow();
        stag.close();
    }
    @FXML
    void deleteBot(ActionEvent event) throws IOException {
        System.out.println("-----------------------");
        System.out.println("deleting bot");
        System.out.println("-----------------------");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/confirm.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            // no toolbar
            stage.initStyle(StageStyle.UNDECORATED);
            xOffset=0;
            yOffset=0;
            //move window easly
            root.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                }
            });
            root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    stage.setX(event.getScreenX() - xOffset);
                    stage.setY(event.getScreenY() - yOffset);
                }
            });
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner((Stage) close.getScene().getWindow());
            stage.setScene(scene);

            Bot b = new Bot(tableview.getSelectionModel().getSelectedItem());

            confirm controller=(confirm) loader.getController();
            controller.setAlertTitle("DELETING Bot");
            controller.setAlertText("Are you sure that you want to delete Bot with data: \r\n\r\nName: "+b.getName()+"\r\nFunction: "+b.getFunctions()+"\r\n");

            stage.showAndWait();

            if(controller.delete)
            {
                SessionFactory sessionFactory = hibernateSession.getSessionFactory();
                Session session = sessionFactory.openSession();
                session.beginTransaction();
                session.remove(b);
                session.getTransaction().commit();
                session.close();
                reloaddata();
            }
        }catch (Exception e)
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/alert.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            // no toolbar
            stage.initStyle(StageStyle.UNDECORATED);
            xOffset=0;
            yOffset=0;
            //move window easly
            root.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                }
            });
            root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    stage.setX(event.getScreenX() - xOffset);
                    stage.setY(event.getScreenY() - yOffset);
                }
            });
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner((Stage) close.getScene().getWindow());
            stage.setScene(scene);

            alert controller=(alert) loader.getController();
            controller.setAlertTitle("Select Bot");
            controller.setAlertText("PLEASE SELECT Bot");
            controller.ramka();
            stage.showAndWait();
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


            Stage stage = new Stage();
            // no toolbar
            stage.initStyle(StageStyle.UNDECORATED);
            xOffset=0;
            yOffset=0;
            //move window easly
            root.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                }
            });
            root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    stage.setX(event.getScreenX() - xOffset);
                    stage.setY(event.getScreenY() - yOffset);
                }
            });
            Scene scene = new Scene(root);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner((Stage) close.getScene().getWindow());
            stage.setScene(scene);
            stage.initOwner((Stage) cancel.getScene().getWindow());
            stage.setTitle("Customer Bots");
            stage.setScene(scene);
            stage.showAndWait();

            reloaddata();
        }catch (Exception e)
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/alert.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            // no toolbar
            stage.initStyle(StageStyle.UNDECORATED);
            xOffset=0;
            yOffset=0;
            //move window easly
            root.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                }
            });
            root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    stage.setX(event.getScreenX() - xOffset);
                    stage.setY(event.getScreenY() - yOffset);
                }
            });
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner((Stage) close.getScene().getWindow());
            stage.setScene(scene);

            alert controller=(alert) loader.getController();
            controller.setAlertTitle("Select Bot");
            controller.setAlertText("PLEASE SELECT Bot");
            controller.ramka();
            stage.showAndWait();
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
        // no toolbar
        stage.initStyle(StageStyle.UNDECORATED);
        xOffset=0;
        yOffset=0;
        //move window easly
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner((Stage) close.getScene().getWindow());
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
//        System.out.println();
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
        List<Bot> test = new ArrayList<Bot>(Bots);
        System.out.println("listA"+test.toString());


        ObservableList<Bot> botsxml=(ObservableList<Bot>) FXCollections.observableList(test);

        FilteredList<Bot> filteredList=new FilteredList<Bot>(botsxml);

        Predicate predicate=new Predicate() {
            public boolean test(Object o) {

                Bot b =new Bot((Bot) o);

                if(b.getName().toLowerCase().toLowerCase().contains(search.getText().toLowerCase()))
                    return true;
                if(b.getFunctions().getName().toLowerCase().contains(search.getText().toLowerCase()))
                    return true;

                return false;
            }
        };

        filteredList.setPredicate(predicate);

        SortedList<Bot> sortedData= new SortedList<Bot>(filteredList);
        sortedData.comparatorProperty().bind(tableview.comparatorProperty());

        return sortedData;
    }
}
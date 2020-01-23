package controllers;

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
import util.SceneManager;
import util.hibernateSession;

import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;

public class customers {

    @FXML
    private Button close;

    @FXML
    private TableView<Customer> tableview;

    @FXML
    private TableColumn<Customer, String> name;

    @FXML
    private TableColumn<Customer, String> surname;

    @FXML
    private TableColumn<Customer, Date> join_date;

    @FXML
    private TableColumn<Customer, Long> pin;

    @FXML
    private Button newCustomer;

    @FXML
    private TextField search;

    private Stage parentStage;

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    void newCustomer(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/addCustomer.fxml"));
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
        stage.initOwner(parentStage);
        stage.setTitle("Create new Customer");
        stage.setScene(scene);

        System.out.println("++++++++++++++++");
        System.out.println("new customer");
        System.out.println("++++++++++++++++");


        stage.showAndWait();

        System.out.println("++++++++++++++++");

        reloaddata();
        reloaddata();
    }
    @FXML
    void close(ActionEvent event) {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }
    @FXML
    void deleteCustomer(ActionEvent event) throws IOException {
        System.out.println("--------------------");
        System.out.println("deleting customer");
        System.out.println("--------------------");

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
            stage.initOwner(parentStage);
            stage.setScene(scene);

            Customer c = new Customer(tableview.getSelectionModel().getSelectedItem());

            confirm controller=(confirm) loader.getController();
            controller.setAlertTitle("DELETING CUSTOMER");
            controller.setAlertText("Are you sure that you want to delete Customer with data: \r\n\r\nName: "+c.getCustomer_name()+"\r\nSurname: "+c.getCustomer_surname()+"\r\nJoined since: "+c.getDate_joined());
            stage.showAndWait();

            if(controller.delete)
            {
                SessionFactory sessionFactory = hibernateSession.getSessionFactory();
                Session session = sessionFactory.openSession();
                session.beginTransaction();
                session.remove(c);
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
            controller.setAlertTitle("Select customer");
            controller.setAlertText("PLEASE SELECT CUSTOMER");
            controller.ramka();
            stage.showAndWait();
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
            stage.initOwner(parentStage);
            stage.setTitle("Edit Customer");
            stage.setScene(scene);
            editCustomer controller = (editCustomer) loader.getController();
            controller.setCustomerData(tableview.getSelectionModel().getSelectedItem());
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
            stage.initOwner(parentStage);
            stage.setScene(scene);

            alert controller=(alert) loader.getController();
            controller.setAlertTitle("Select customer");
            controller.setAlertText("PLEASE SELECT CUSTOMER");
            controller.ramka();
            stage.showAndWait();
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
            stage.setTitle("Customer Bots");
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);

            close(new ActionEvent());
            stage.show();

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
            stage.initOwner(parentStage);
            stage.setScene(scene);

            alert controller=(alert) loader.getController();
            controller.setAlertTitle("Select customer");
            controller.setAlertText("PLEASE SELECT CUSTOMER");
            controller.ramka();
            stage.showAndWait();
        }

    }

    @FXML
    void editCustomerChannels(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/channels.fxml"));
            Parent root = loader.load();

            channels controller=(channels) loader.getController();
            controller.setCustomerData(tableview.getSelectionModel().getSelectedItem());

            Scene scene = new Scene(root);
            Stage stage = new Stage();

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
            stage.initOwner(parentStage);
            stage.setScene(scene);

            stage.setTitle("Customer Bots");
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);

            close(new ActionEvent());
            stage.show();

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
            stage.initOwner(parentStage);
            stage.setScene(scene);

            alert controller=(alert) loader.getController();
            controller.setAlertTitle("Select customer");
            controller.setAlertText("PLEASE SELECT CUSTOMER");
            controller.ramka();
            stage.showAndWait();
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
        pin.setCellValueFactory(new PropertyValueFactory<Customer,Long>("customer_nip"));

        reloaddata();
    }

    public void shutdown(){
        session.close();
    }
    private SessionFactory sessionFactory;
    private Session session;

    public void reloaddata(){
        System.out.println("----------------------");
        System.out.println("reloading customer data");
        System.out.println("----------------------");
        sessionFactory = hibernateSession.getSessionFactory();
        session = sessionFactory.openSession();
        session.beginTransaction();

        setCustomers(hibernateSession.loadAllData(Customer.class, session));
        session.getTransaction().commit();

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



    //https://vladmihalcea.com/hibernate-multiplebagfetchexception/
}

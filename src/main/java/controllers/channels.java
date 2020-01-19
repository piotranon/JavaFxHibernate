package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.function.Predicate;

import entity.Bot;
import entity.Channel;
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
    private Button close;


    @FXML
    private TableView<Channel> tableview;

    @FXML
    private TableColumn<Channel, String> Name;

    @FXML
    private TableColumn<Channel, String> Description;

    @FXML
    void close(ActionEvent event) {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }
    private double xOffset;
    private double yOffset;

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
    void deleteChannel(ActionEvent event) throws IOException {
        System.out.println("-----------------------");
        System.out.println("deleting channel");
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

            Channel ch = new Channel(tableview.getSelectionModel().getSelectedItem());

            confirm controller=(confirm) loader.getController();
            controller.setAlertTitle("DELETING CHANNEL");
            controller.setAlertText("Are you sure that you want to delete Channel with data: \r\n\r\nName: "+ch.getName()+"\r\nDescription: "+ch.getDescription()+"\r\n");
            stage.showAndWait();

            if(controller.delete)
            {
                this.c.getChannels().remove(ch);
                SessionFactory sessionFactory = hibernateSession.getSessionFactory();
                Session session = sessionFactory.openSession();
                session.beginTransaction();
//                session.update(this.c);
                session.remove(ch);
                session.getTransaction().commit();
                session.close();
                reloaddata();
                System.out.println("usunieto: "+c.toString());
            }
        }catch (Exception e)
        {
            System.out.println(e.toString());
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
            controller.setAlertTitle("Select Channel");
            controller.setAlertText("PLEASE SELECT Channel");
            controller.ramka();
            stage.showAndWait();
        }
        System.out.println("-----------------------");
    }

    @FXML
    void editChannel(ActionEvent event) throws IOException {
        System.out.println("//////////////////");
        System.out.println("editing channel");
        System.out.println("//////////////////");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/editchannel.fxml"));
            Parent root = loader.load();
            editChannel controller = (editChannel) loader.getController();

            controller.setCustomerData(c);
            controller.setChannel(tableview.getSelectionModel().getSelectedItem());

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner((Stage) cancel.getScene().getWindow());
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Customer Channels");
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
            controller.setAlertTitle("Select Channel");
            controller.setAlertText("PLEASE SELECT Channel");
            controller.ramka();
            stage.showAndWait();
        }
        System.out.println("//////////////////");
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

        URL url=getClass().getResource("/scenes/addChannel.fxml");
        FXMLLoader loader = new FXMLLoader(url);
        if(loader == null)
        {
            throw new RuntimeException ("Could not find: "+url.toString());
        }
        Parent root = loader.load();
        addChannel controller=(addChannel) loader.getController();
        controller.setCustomerData(c);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initOwner((Stage)cancel.getScene().getWindow());
        stage.setTitle("Customer Channels");
        stage.setScene(scene);
        stage.showAndWait();

        reloaddata();

        System.out.println("+++++++++++++++++");
    }

    @FXML
    void reloadDataToView(ActionEvent event) {
        search.setText("");
        reloaddata();
    }

    @FXML
    void initialize() {
        Name.setCellValueFactory(new PropertyValueFactory<Channel, String>("name"));
        Description.setCellValueFactory(new PropertyValueFactory<Channel, String>("description"));

    }

    private Customer c;
    private Set<Channel> Channels;

    public void setCustomerData(Customer c) {
        this.c=c;
        Channels=c.getChannels();

        System.out.println("customer channels: ");
        System.out.println(Channels.toString());


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
//        List<Channel> channels=new ArrayList<Channel>(session.load(Channel.class,c.getId()));
//        channels=new HashSet();

        session.close();
        System.out.println("----------------------");
    }

    void loadview(Set<Channel> Channels)
    {
        System.out.println("---------");
        System.out.println("refreshing channels view");
        System.out.println("---------");
        System.out.println(c.toString());
        System.out.println("---------");
        tableview.setItems(sortedList(Channels));
        System.out.println("---------");
    }

    SortedList<Channel> sortedList(Set<Channel> Channels2){

        List<?> test=new ArrayList<Channel>(Channels2);
        System.out.println("lista: "+test.toString());

        ObservableList<Channel> channelsxml=(ObservableList<Channel>) FXCollections.observableList(test);

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

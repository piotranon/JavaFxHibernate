package controllers;
        import java.io.IOException;
        import java.net.URL;
        import java.util.*;
        import java.util.function.Predicate;

        import entity.Bot;
        import entity.Customer;
        import entity.Function;
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
        import javafx.scene.control.Button;
        import javafx.scene.control.TableColumn;
        import javafx.scene.control.TableView;
        import javafx.scene.control.TextField;
        import javafx.scene.control.cell.PropertyValueFactory;
        import javafx.scene.input.KeyEvent;
        import javafx.scene.input.MouseEvent;
        import javafx.stage.Modality;
        import javafx.stage.Stage;
        import javafx.stage.StageStyle;
        import org.hibernate.Session;
        import org.hibernate.SessionFactory;
        import util.hibernateSession;


public class functions {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button newBot;

    @FXML
    private TextField search;

    @FXML
    private Button cancel;

    @FXML
    private TableView<entity.Function> tableview;

    @FXML
    private TableColumn<entity.Function, String> Name;

    @FXML
    private TableColumn<entity.Function, String> Function;

    @FXML
    private TableColumn<entity.Function, Double> price;

    @FXML
    private Button close;

    private Customer c;
    private List<Function> functions=new ArrayList<>();
    private double xOffset;
    private double yOffset;

    @FXML
    void cancel(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/bots.fxml"));
        Parent root = loader.load();

        bots controller=(bots) loader.getController();
        controller.setCustomerData(c);

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
    }
    public void setCustomerData(Customer c) {
        this.c=c;
    }
    @FXML
    void close(ActionEvent event) {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    @FXML
    void deletefunction(ActionEvent event) throws IOException {
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

        Function b = tableview.getSelectionModel().getSelectedItem();

        confirm controller=(confirm) loader.getController();
        controller.setAlertTitle("DELETING Function");
        controller.setAlertText("Are you sure that you want to delete Function with data: \r\n\r\n"+b.toString());

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
    }

    @FXML
    void editfunction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/editFunction.fxml"));
        Parent root = loader.load();
        editFunction controller=loader.getController();
        controller.setfunction(tableview.getSelectionModel().getSelectedItem());
        Scene scene = new Scene(root);
        Stage stage = new Stage();
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
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner((Stage) cancel.getScene().getWindow());
        stage.setScene(scene);
        stage.showAndWait();

        SessionFactory sessionFactory = hibernateSession.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        functions=hibernateSession.loadAllData(Function.class,session);

        session.close();
        reloaddata();
    }

    @FXML
    void limitList(KeyEvent event) {
        loadview();
    }

    private void loadview() {
        System.out.println("---------");
        System.out.println("refreshing customer view");
        System.out.println("---------");
        tableview.setItems(sortedList(functions));
        System.out.println("---------");
    }

    private ObservableList<Function> sortedList(List<entity.Function> functions) {
        ObservableList<Function> customersxml = (ObservableList<Function>) FXCollections.observableList(functions);
        FilteredList<Function> filteredList=new FilteredList<Function>(customersxml);

        Predicate predicate=new Predicate() {
            public boolean test(Object o) {

                Function f =new Function((Function)o);

                if(f.getName().toLowerCase().contains(search.getText().toLowerCase()))
                {
                    return true;
                }else if(f.getDesc().toLowerCase().contains(search.getText().toLowerCase()))
                {
                    return true;
                }else if(search.getText().toLowerCase().contains(String.valueOf(f.getPrice())))
                {
                    return true;
                }
                return false;
            }
        };

        filteredList.setPredicate(predicate);

        SortedList<Function> sortedData = new SortedList<Function>(filteredList);
        sortedData.comparatorProperty().bind(tableview.comparatorProperty());

        return sortedData;
    }

    @FXML
    void newFunction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/addFunction.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
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
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner((Stage) cancel.getScene().getWindow());
        stage.setScene(scene);
        stage.showAndWait();

        SessionFactory sessionFactory = hibernateSession.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        functions=hibernateSession.loadAllData(Function.class,session);

        session.close();
        reloaddata();
    }

    @FXML
    void reloadDataToView(ActionEvent event) {
        search.setText("");
        reloaddata();
    }

    private void reloaddata() {
        System.out.println("----------------------");
        System.out.println("reloading functions data");
        System.out.println("----------------------");

        SessionFactory sessionFactory = hibernateSession.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        functions=hibernateSession.loadAllData(Function.class,session);
        System.out.println(functions.toString());
        session.close();
        System.out.println("----------------------");
        loadview();
    }

    @FXML
    void initialize() {
        assert newBot != null : "fx:id=\"newBot\" was not injected: check your FXML file 'functions.fxml'.";
        assert search != null : "fx:id=\"search\" was not injected: check your FXML file 'functions.fxml'.";
        assert cancel != null : "fx:id=\"cancel\" was not injected: check your FXML file 'functions.fxml'.";
        assert tableview != null : "fx:id=\"tableview\" was not injected: check your FXML file 'functions.fxml'.";
        assert Name != null : "fx:id=\"Name\" was not injected: check your FXML file 'functions.fxml'.";
        assert Function != null : "fx:id=\"Function\" was not injected: check your FXML file 'functions.fxml'.";
        assert close != null : "fx:id=\"close\" was not injected: check your FXML file 'functions.fxml'.";
        Name.setCellValueFactory(new PropertyValueFactory<entity.Function, String >("name"));
        Function.setCellValueFactory(new PropertyValueFactory<entity.Function, String>("desc"));
        price.setCellValueFactory(new PropertyValueFactory<entity.Function, Double>("price"));
        reloaddata();
    }
}

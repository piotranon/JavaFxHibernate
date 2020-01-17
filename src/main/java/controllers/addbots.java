package controllers;

import java.net.URL;
import java.util.*;

import entity.Bot;
import entity.Customer;
import entity.Function;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.hibernateSession;

public class addbots {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField name;

    @FXML
    private TableView<Function> func;

    @FXML
    private TableColumn<Function, String> funcname;

    @FXML
    private Button cancel;

    private Customer c;

    @FXML
    void Add(ActionEvent event) {
        StringBuilder errors=new StringBuilder();
        boolean missingdata=false;
        if(name.getText().length()<=0)
        {
            errors.append("Name is missing. \r\n");
            missingdata=true;
        }
//        if(function.getText().length()<=0)
//        {
//            errors.append("Function is missing. \r\n");
//            missingdata=true;
//        }
        if(missingdata)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Not all data has been provided");
            alert.setHeaderText("Input data ERROR");
            alert.setContentText(errors.toString());
            alert.show();
        }else
        {
            Bot bot=new Bot();
            bot.setName(name.getText());
//            bot.setFunctions(function.getText());
            bot.setCustomer(c);

            c.getBots().add(bot);

            SessionFactory sessionFactory = hibernateSession.getSessionFactory();
            Session session = sessionFactory.openSession();
            session.beginTransaction();

            session.merge(c);

            session.getTransaction().commit();
            session.close();

            cancel(event);
        }
    }

    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }
    private List<Function> functions;
    @FXML
    void initialize() {
//        function.setSelectionModel(SelectionMode.MULTIPLE);
        func.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        funcname.setCellValueFactory(new PropertyValueFactory<Function,String>("name"));

        SessionFactory sessionFactory = hibernateSession.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        functions=(hibernateSession.loadAllData(Function.class,session));
        ObservableList<Function> functionsxml=(ObservableList<Function>) FXCollections.observableList(functions);


        func.setItems(functionsxml);
        session.close();

//        function.setItems((ObservableList<Function>)functionsxml);
        List<String> functionnames=new ArrayList<String>();

//        functionnames
//        function.setItems((ObservableList<String>) FXCollections.observableList(functionnames));
//        functions2.setItems((ObservableList<String>) FXCollections.observableList(functionnames));
//        functions2.ge
    }

    void setCustomerData(Customer c)
    {
        this.c=c;
    }
}

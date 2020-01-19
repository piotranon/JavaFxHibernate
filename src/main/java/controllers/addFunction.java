package controllers;
        import java.net.URL;
        import java.util.ResourceBundle;

        import entity.Function;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.scene.control.Button;
        import javafx.scene.control.TextField;
        import javafx.stage.Stage;
        import org.hibernate.Session;
        import org.hibernate.SessionFactory;
        import util.hibernateSession;

public class addFunction {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField name;

    @FXML
    private TextField description;

    @FXML
    private TextField price;

    @FXML
    private Button cancel;

    @FXML
    void Add(ActionEvent event) {
        String name=this.name.getText();
        String description=this.description.getText();
        String price=this.price.getText();

        Function f=new Function(name,description,Integer.valueOf(price));

        SessionFactory sessionFactory = hibernateSession.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(f);

        session.getTransaction().commit();
        session.close();

        cancel(event);
    }

    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void initialize() {
        assert name != null : "fx:id=\"name\" was not injected: check your FXML file 'addfunction.fxml'.";
        assert cancel != null : "fx:id=\"cancel\" was not injected: check your FXML file 'addfunction.fxml'.";

    }
}
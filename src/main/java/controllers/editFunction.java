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

public class editFunction {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField name;

    @FXML
    private Button cancel;

    @FXML
    private TextField description;

    @FXML
    private TextField price;

    @FXML
    void Update(ActionEvent event) {

        f.setName(name.getText());
        f.setDesc(description.getText());
        f.setPrice(Double.valueOf(price.getText()));

        System.out.println("id: "+f.toString());
        SessionFactory sessionFactory = hibernateSession.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(f);
        session.getTransaction().commit();
        session.close();

        cancel(event);
    }

    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    private Function f;
    public void setfunction(Function f)
    {
        this.f=f;
        name.setText(f.getName());
        description.setText(f.getDesc());
        price.setText(String.valueOf(f.getPrice()));
    }

    @FXML
    void initialize() {
        assert name != null : "fx:id=\"name\" was not injected: check your FXML file 'editFunction.fxml'.";
        assert cancel != null : "fx:id=\"cancel\" was not injected: check your FXML file 'editFunction.fxml'.";
        assert description != null : "fx:id=\"description\" was not injected: check your FXML file 'editFunction.fxml'.";
        assert price != null : "fx:id=\"price\" was not injected: check your FXML file 'editFunction.fxml'.";

    }
}

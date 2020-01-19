package controllers;

        import java.net.URL;
        import java.util.ResourceBundle;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.scene.control.Button;
        import javafx.scene.control.Label;
        import javafx.scene.layout.BorderPane;
        import javafx.stage.Stage;

public class confirm {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private BorderPane alert;

    @FXML
    private Label alertTitle;

    @FXML
    private Label alertText;

    @FXML
    private Button cancel;

    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }
    public boolean delete=false;
    @FXML
    void confirm(ActionEvent event) {
        delete=true;
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }
    void setAlertTitle(String alertTitle) {
        this.alertTitle.setText(alertTitle);
    }

    void setAlertText(String alertText) {
        this.alertText.setText(alertText);
    }
    @FXML
    void initialize() {
        assert alert != null : "fx:id=\"alert\" was not injected: check your FXML file 'confirm.fxml'.";
        assert alertTitle != null : "fx:id=\"alertTitle\" was not injected: check your FXML file 'confirm.fxml'.";
        assert alertText != null : "fx:id=\"alertText\" was not injected: check your FXML file 'confirm.fxml'.";
        assert cancel != null : "fx:id=\"cancel\" was not injected: check your FXML file 'confirm.fxml'.";

    }
}

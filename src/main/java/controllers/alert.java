package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import antlr.ASTNULLType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class alert {

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
        border=false;
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }
    @FXML
    void initialize() {
        assert alert != null : "fx:id=\"alert\" was not injected: check your FXML file 'alert.fxml'.";
        assert alertTitle != null : "fx:id=\"alertTitle\" was not injected: check your FXML file 'alert.fxml'.";
        assert alertText != null : "fx:id=\"alertText\" was not injected: check your FXML file 'alert.fxml'.";
        alert.getStyleClass().clear();
        alert.getStyleClass().addAll("border");
    }

    void setAlertTitle(String alertTitle) {
        this.alertTitle.setText(alertTitle);
    }

    void setAlertText(String alertText) {
        this.alertText.setText(alertText);
    }
    boolean border=false;

    void ramka(){
        border=!border;
        if(border)
        {
            change();
        }
    }

    void change(){
        new Thread(new Runnable() {
            public void run() {
                try {
                    if(border) {
                        System.out.println("petla");
                        alert.setStyle("-fx-border-color:red;-fx-background-color: yellow;-fx-border-width: 5px;-fx-text-fill: black;");
                        Thread.sleep(200);
                        alert.getStyleClass().clear();
                        alert.getStyleClass().addAll("border");
                        alert.setStyle("-fx-border-color:orange;-fx-background-color: yellow;-fx-border-width: 5px;-fx-text-fill: black;");
                        Thread.sleep(200);
                        change();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
}

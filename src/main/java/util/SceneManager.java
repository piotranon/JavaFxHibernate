package util;

import java.io.IOException;
import java.util.Hashtable;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.stage.StageStyle;

public class SceneManager {
    private static Stage stage;
    private static Hashtable<String, String> view = new Hashtable<>();

    private static double xOffset = 0;
    private static double yOffset = 0;

    public static void addScene(String name, String path) throws IOException{
        view.put(name, path);
    }

    public static void removeScene(String name){
        view.remove(name);
    }

    public static void renderScene(String name){
        String path=null;
        //System.out.println(view.get(path));
        try{
            path = "/scenes/"+view.get(name);
            //System.out.println(view.get(path));
            Parent root = FXMLLoader.load(SceneManager.class.getResource(path));

            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED);

            xOffset = 0;
            yOffset = 0;

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

            stage.show();
        }
        catch(IOException ex){
            System.err.println("Nie można załadować pliku XML z widokiem: "+path);
            ex.printStackTrace();
        }
        catch(RuntimeException ex){
            System.err.println("Nazwa widoku jest nieprawidłowa!");
            System.out.println(ex.toString());
            ex.printStackTrace();
        }
    }

    public static void setStage(Stage _stage){

        stage = _stage;

    }

    public static Stage getStage() {
        return stage;
    }
}
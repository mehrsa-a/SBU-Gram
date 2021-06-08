package Model;

import Common.Post;
import Common.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static Post currentPost=new Post();
    public static User currentUser;

    @Override
    public void start(Stage primaryStage) throws Exception{
        PageLoader.initStage(primaryStage); //this is only needed when you start program
        //and need a new stage. all scenes will be loaded on this stage
        new PageLoader().load("Login");
    }


    public static void main(String[] args) {
        ConnectClient.connectToServer();
        launch(args);
    }
}

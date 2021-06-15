package Model;

import Common.Post;
import Common.User;
import Controller.LogInController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Main extends Application {

    public static Post currentPost=new Post();
    public static User currentUser=new User();
    public static List<Post> posts=new CopyOnWriteArrayList<>();
    public static Map<String, User> users=new ConcurrentHashMap<>();

    public static void update(){
        if(ClientAPI.getAllPosts()!=null){
            posts=new CopyOnWriteArrayList<>(ClientAPI.getAllPosts());
        }
        Map<String, User> map = ClientAPI.getAllUsers();
        if(map!=null){
            users = map;
        }
        for(User u: users.values()){
            if(ClientAPI.getAllOfMyPosts(u)!=null){
                u.getPosts().clear();
                u.getPosts().addAll(ClientAPI.getAllOfMyPosts(u));
            }
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        PageLoader.initStage(primaryStage);
        new PageLoader().load("Login");
    }


    public static void main(String[] args) {
        ConnectClient.connectToServer();
        launch(args);
    }
}

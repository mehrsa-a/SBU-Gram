package Model;

import Common.Massage;
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

/**
 * <h1>Main</h1>
 * <p>this class runs the client side</p>
 * @author Mehrsa Arabzadeh
 * @since 6/2/2021
 * @version 1.0
 */
public class Main extends Application {

    public static Post currentPost=new Post();
    public static User currentUser=new User();
    public static List<Post> posts=new CopyOnWriteArrayList<>();
    public static Map<String, User> users=new ConcurrentHashMap<>();
    public static List<Massage> massages=new CopyOnWriteArrayList<>();

    /**
     * his method update information in client side
     */
    public static void update(){
        if(ClientAPI.getAllPosts(Main.currentUser)!=null){
            posts=new CopyOnWriteArrayList<>(ClientAPI.getAllPosts(Main.currentUser));
        }
        Map<String, User> map = ClientAPI.getAllUsers(Main.currentUser);
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

    /**
     * this method opens the program
     * @param primaryStage it uses for initialize basic stage
     * @throws Exception because of using pageLoader
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        PageLoader.initStage(primaryStage);
        new PageLoader().load("Login");
    }

    /**
     * it connect the client to server and runs the client side
     * @param args it uses for opening program
     */
    public static void main(String[] args) {
        ConnectClient.connectToServer();
        launch(args);
    }
}

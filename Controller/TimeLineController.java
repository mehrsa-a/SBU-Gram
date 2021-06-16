package Controller;

import Common.Post;
import Common.User;
import Model.*;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Popup;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import static Model.Main.*;

public class TimeLineController {
    public Label Bio;
    public ImageView image;
    public JFXTextField title;
    public JFXTextArea post;
    public JFXListView<Post> PostList=new JFXListView<>();
    public JFXListView<Post> myPosts=new JFXListView<>();
    public JFXListView<User> accounts=new JFXListView<>();
    public Label username;
    public Label post1;
    public Label follower;
    public Label following;

    public void initialize(){
        username.setText(currentUser.getUsername());
        accounts.setItems(FXCollections.observableArrayList(Main.users.values()));
        accounts.setCellFactory(accounts -> new UserItem());
        PostList.setItems(FXCollections.observableArrayList(posts));
        PostList.setCellFactory(PostList -> new PostItem());
        myPosts.setItems(FXCollections.observableArrayList(currentUser.getPosts()));
        myPosts.setCellFactory(myPosts -> new PostItem());
        String temp=ClientAPI.getNumbers(currentUser);
        following.setText(String.valueOf(Integer.parseInt(temp.substring(0, temp.indexOf("|")))));
        follower.setText(String.valueOf(Integer.parseInt(temp.substring(temp.indexOf("|")+1, temp.lastIndexOf("|")))));
        post.setText(String.valueOf(Integer.parseInt(temp.substring(temp.lastIndexOf("|")+1))));
    }

    public void refresh(ActionEvent actionEvent) throws IOException {
        Main.update();
        ClientAPI.getAllPosts();
        for(User u: users.values()){
            ClientAPI.getMyPosts(u);
        }
        ClientAPI.getAllUsers();
        new PageLoader().load("Timeline");
        username.setText(currentUser.getUsername());
        accounts.setItems(FXCollections.observableArrayList(Main.users.values()));
        accounts.setCellFactory(accounts -> new UserItem());
        PostList.setItems(FXCollections.observableArrayList(posts));
        PostList.setCellFactory(PostList -> new PostItem());
        myPosts.setItems(FXCollections.observableArrayList(currentUser.getPosts()));
        myPosts.setCellFactory(myPosts -> new PostItem());
        String temp=ClientAPI.getNumbers(currentUser);
        following.setText(String.valueOf(Integer.parseInt(temp.substring(0, temp.indexOf("|")))));
        follower.setText(String.valueOf(Integer.parseInt(temp.substring(temp.indexOf("|")+1, temp.lastIndexOf("|")))));
        post.setText(String.valueOf(Integer.parseInt(temp.substring(temp.lastIndexOf("|")+1))));
    }

    public void editProfile(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("EditProfile");
        //EditProfileController.username.setText(currentUser.getUsername());
    }

    public void addPhoto(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser=new FileChooser();
        File file=fileChooser.showOpenDialog(new Popup());
        FileInputStream fileInputStream=new FileInputStream(file);
        byte[] bytes=fileInputStream.readAllBytes();
        Image newImage=new Image(new ByteArrayInputStream(bytes));
        image.setImage(newImage);
    }

    public void publish(ActionEvent actionEvent) {
        Post currentPost = new Post();
        currentPost.setUser(currentUser);
        currentPost.setPublisher(currentUser);
        currentPost.setTitle(title.getText());
        currentPost.setText(post.getText());
        posts.add(currentPost);
        currentUser.getPosts().add(currentPost);
        ClientAPI.addPost(currentPost);
        Main.update();
        ClientAPI.getAllPosts();
        for(User u: users.values()){
            ClientAPI.getMyPosts(u);
        }
        PostList.setItems(FXCollections.observableArrayList(posts));
        PostList.setCellFactory(PostList -> new PostItem());
        myPosts.setItems(FXCollections.observableArrayList(currentUser.getPosts()));
        myPosts.setCellFactory(myPosts -> new PostItem());
        currentPost=new Post();
        title.setText("");
        post.setText("");
        image.setImage(null);
    }

    public void logOut(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("Login");
    }

    public void openProfile(Event event) {
        Main.update();
        ClientAPI.getMyPosts(currentUser);
        username.setText(currentUser.getUsername());
        myPosts.setItems(FXCollections.observableArrayList(currentUser.getPosts()));
        myPosts.setCellFactory(myPosts -> new PostItem());
        String temp=ClientAPI.getNumbers(currentUser);
        following.setText(String.valueOf(Integer.parseInt(temp.substring(0, temp.indexOf("|")))));
        follower.setText(String.valueOf(Integer.parseInt(temp.substring(temp.indexOf("|")+1, temp.lastIndexOf("|")))));
        post.setText(String.valueOf(Integer.parseInt(temp.substring(temp.lastIndexOf("|")+1))));
    }

    public void openAccounts(Event event) {
        Main.update();
        ClientAPI.getAllUsers();
        accounts.setItems(FXCollections.observableArrayList(Main.users.values()));
        accounts.setCellFactory(accounts -> new UserItem());
    }

    public void openTimeline(Event event) {
        Main.update();
        ClientAPI.getAllPosts();
        PostList.setItems(FXCollections.observableArrayList(posts));
        PostList.setCellFactory(PostList -> new PostItem());
    }
}

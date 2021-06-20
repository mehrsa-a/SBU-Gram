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
import java.util.*;
import java.util.stream.Collectors;

import static Model.Main.*;

public class TimeLineController {
    public Label Bio;
    public String fullName="";
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
    public ImageView profile;
    public static byte[] help;
    public Label name;
    public JFXTextField searchField;
    public JFXListView<Post> explorePosts;

    public void initialize(){
        username.setText(currentUser.getUsername());
        byte[] x=ClientAPI.getProfile(currentUser);
        if(x!=null){
            Image newImage=new Image(new ByteArrayInputStream(x));
            profile.setImage(newImage);
        }
        accounts.setItems(FXCollections.observableArrayList(Main.users.values()));
        accounts.setCellFactory(accounts -> new UserItem());
        List<User> followed=users.values().stream()
                .filter(a-> currentUser.getFollowing().contains(a))
                .collect(Collectors.toList());
        Set<Post> set=new HashSet<>();
        set.addAll(currentUser.getPosts());
        for(User u: followed){
            set.addAll(u.getPosts());
        }
        PostList.setItems(FXCollections.observableArrayList(set));
        PostList.setCellFactory(PostList -> new PostItem());
        explorePosts.setItems(FXCollections.observableArrayList(posts));
        explorePosts.setCellFactory(explorePosts -> new PostItem());
        myPosts.setItems(FXCollections.observableArrayList(currentUser.getPosts()));
        myPosts.setCellFactory(myPosts -> new PostItem());
        String temp=ClientAPI.getNumbers(currentUser);
        following.setText(String.valueOf(Integer.parseInt(temp.substring(0, temp.indexOf("|")))));
        follower.setText(String.valueOf(Integer.parseInt(temp.substring(temp.indexOf("|")+1, temp.lastIndexOf("|")))));
        post.setText(String.valueOf(Integer.parseInt(temp.substring(temp.lastIndexOf("|")+1))));
        Map<String, String> info=ClientAPI.getInformation(currentUser);
        if(info!=null){
            if(info.get("bio") != null){
                Bio.setText(info.get("bio"));
            }
            if(info.get("firstName") != null){
                fullName=info.get("firstName");
            }
            if(info.get("lastName") != null){
                fullName=" "+info.get("lastName");
            }
        }
        if(!fullName.equals("")){
            name.setText(fullName);
        } else{
            name.setVisible(false);
        }
    }

    public void refresh(ActionEvent actionEvent) throws IOException {
        Main.update();
        ClientAPI.getAllPosts();
        for(User u: users.values()){
            ClientAPI.getMyPosts(u);
        }
        ClientAPI.getAllUsers(currentUser);
        new PageLoader().load("Timeline");
        username.setText(currentUser.getUsername());
        accounts.setItems(FXCollections.observableArrayList(Main.users.values()));
        accounts.setCellFactory(accounts -> new UserItem());
        List<User> followed=users.values().stream()
                .filter(a-> currentUser.getFollowing().contains(a))
                .collect(Collectors.toList());
        Set<Post> set=new HashSet<>();
        set.addAll(currentUser.getPosts());
        for(User u: followed){
            set.addAll(u.getPosts());
        }
        PostList.setItems(FXCollections.observableArrayList(set));
        PostList.setCellFactory(PostList -> new PostItem());
        explorePosts.setItems(FXCollections.observableArrayList(posts));
        explorePosts.setCellFactory(explorePosts -> new PostItem());
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
        help=bytes;
        Image newImage=new Image(new ByteArrayInputStream(bytes));
        image.setImage(newImage);
    }

    public void publish(ActionEvent actionEvent) {
        Post currentPost = new Post();
        currentPost.setUser(currentUser);
        currentPost.setPublisher(currentUser);
        currentPost.setTitle(title.getText());
        currentPost.setText(post.getText());
        if(help!=null){
            currentPost.setImage(help);
        }
        posts.add(currentPost);
        currentUser.getPosts().add(currentPost);
        if(help==null){
            ClientAPI.addPost(currentPost);
        }
        else{
            ClientAPI.addPost(currentPost, help);
        }
        Main.update();
        ClientAPI.getAllPosts();
        for(User u: users.values()){
            ClientAPI.getMyPosts(u);
        }
        List<User> followed=users.values().stream()
                .filter(a-> currentUser.getFollowing().contains(a))
                .collect(Collectors.toList());
        Set<Post> set=new HashSet<>();
        set.addAll(currentUser.getPosts());
        for(User u: followed){
            set.addAll(u.getPosts());
        }
        PostList.setItems(FXCollections.observableArrayList(set));
        PostList.setCellFactory(PostList -> new PostItem());
        explorePosts.setItems(FXCollections.observableArrayList(posts));
        explorePosts.setCellFactory(explorePosts -> new PostItem());
        myPosts.setItems(FXCollections.observableArrayList(currentUser.getPosts()));
        myPosts.setCellFactory(myPosts -> new PostItem());
        currentPost=new Post();
        title.setText("");
        post.setText("");
        image.setImage(null);
    }

    public void logOut(ActionEvent actionEvent) throws IOException {
        ClientAPI.logOut(currentUser);
        new PageLoader().load("Login");
    }

    public void openProfile(Event event) {
        Main.update();
        ClientAPI.getMyPosts(currentUser);
        username.setText(currentUser.getUsername());
        byte[] x=ClientAPI.getProfile(currentUser);
        if(x!=null){
            Image newImage=new Image(new ByteArrayInputStream(x));
            profile.setImage(newImage);
        }
        myPosts.setItems(FXCollections.observableArrayList(currentUser.getPosts()));
        myPosts.setCellFactory(myPosts -> new PostItem());
        String temp=ClientAPI.getNumbers(currentUser);
        following.setText(String.valueOf(Integer.parseInt(temp.substring(0, temp.indexOf("|")))));
        follower.setText(String.valueOf(Integer.parseInt(temp.substring(temp.indexOf("|")+1, temp.lastIndexOf("|")))));
        post1.setText(String.valueOf(Integer.parseInt(temp.substring(temp.lastIndexOf("|")+1))));
    }

    public void openAccounts(Event event) {
        Main.update();
        ClientAPI.getAllUsers(currentUser);
        accounts.setItems(FXCollections.observableArrayList(Main.users.values()));
        accounts.setCellFactory(accounts -> new UserItem());
    }

    public void openTimeline(Event event) {
        Main.update();
        ClientAPI.getAllPosts();
        List<User> followed=users.values().stream()
                .filter(a-> currentUser.getFollowing().contains(a))
                .collect(Collectors.toList());
        Set<Post> set=new HashSet<>();
        set.addAll(currentUser.getPosts());
        for(User u: followed){
            set.addAll(u.getPosts());
        }
        PostList.setItems(FXCollections.observableArrayList(set));
        PostList.setCellFactory(PostList -> new PostItem());
    }

    public void search(ActionEvent actionEvent) {
        Main.update();
        ClientAPI.getAllUsers(currentUser);
        List<User> searched= users.values()
                .stream()
                .filter(a->a.getUsername().contains(searchField.getText()))
                .collect(Collectors.toList());
        accounts.setItems(FXCollections.observableArrayList(searched));
        accounts.setCellFactory(accounts -> new UserItem());
    }

    public void backToAll(ActionEvent actionEvent) {
        Main.update();
        ClientAPI.getAllUsers(currentUser);
        accounts.setItems(FXCollections.observableArrayList(Main.users.values()));
        accounts.setCellFactory(accounts -> new UserItem());
    }

    public void openExplore(Event event) {
        explorePosts.setItems(FXCollections.observableArrayList(posts));
        explorePosts.setCellFactory(explorePosts -> new PostItem());
    }
}

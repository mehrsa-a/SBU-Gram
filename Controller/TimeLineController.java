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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
    public static String path;
    public Label name;
    public JFXTextField searchField;
    public JFXListView<Post> explorePosts;
    public JFXListView<User> Massages;
    public JFXTextField searchForMassage;
    public AnchorPane personalProfile;

    public void initialize(){
        List<Post> posts=ClientAPI.getAllPosts(currentUser);
        List<String> f=ClientAPI.getFollowings(currentUser);
        List<String> m=ClientAPI.getMuted(currentUser);
        Set<Post> t=new HashSet<>();
        for(Post p: posts){
            assert f != null;
            if(f.contains(p.getUser().getUsername())) {
                assert m != null;
                if (!(m.contains(p.getUser().getUsername()))) {
                    t.add(p);
                }
            }
            if(currentUser.getUsername().equals(p.getUser().getUsername())){
                t.add(p);
            }
            List<String> pu=p.getPublisher().stream()
                    .map(a-> a.getUsername())
                    .collect(Collectors.toList());
            for(String s: pu){
                if(f.contains(s)){
                    assert m != null;
                    if (!(m.contains(p.getUser().getUsername()))) {
                        t.add(p);
                    }
                }
                if(currentUser.getUsername().equals(s)){
                    t.add(p);
                }
            }
        }
        ClientAPI.getTimeline(currentUser);
        PostList.setItems(FXCollections.observableArrayList(t));
        PostList.setCellFactory(PostList -> new PostItem());
    }

    public void refresh(ActionEvent actionEvent) throws IOException {
        Main.update();
        ClientAPI.getAllPosts(currentUser);
        for(User u: users.values()){
            ClientAPI.getMyPosts(u);
        }
        ClientAPI.getAllUsers(currentUser);
        new PageLoader().load("Timeline");
        username.setText(currentUser.getUsername());
        List<String> blockers=ClientAPI.getBlockers(currentUser);
        List<User> acc=new ArrayList<>();
        for(User u: Main.users.values()){
            if(!blockers.contains(u.getUsername())){
                acc.add(u);
            }
        }
        accounts.setItems(FXCollections.observableArrayList(acc));
        accounts.setCellFactory(accounts -> new UserItem());
        List<Post> posts=ClientAPI.getAllPosts(currentUser);
        List<String> f=ClientAPI.getFollowings(currentUser);
        List<String> m=ClientAPI.getMuted(currentUser);
        Set<Post> t=new HashSet<>();
        for(Post p: posts){
            assert f != null;
            if(f.contains(p.getUser().getUsername())) {
                assert m != null;
                if (!(m.contains(p.getUser().getUsername()))) {
                    t.add(p);
                }
            }
            if(currentUser.getUsername().equals(p.getUser().getUsername())){
                t.add(p);
            }
            List<String> pu=p.getPublisher().stream()
                    .map(a-> a.getUsername())
                    .collect(Collectors.toList());
            for(String s: pu){
                if(f.contains(s)){
                    assert m != null;
                    if (!(m.contains(p.getUser().getUsername()))) {
                        t.add(p);
                    }
                }
                if(currentUser.getUsername().equals(s)){
                    t.add(p);
                }
            }
        }
        ClientAPI.getTimeline(currentUser);
        PostList.setItems(FXCollections.observableArrayList(t));
        PostList.setCellFactory(PostList -> new PostItem());
        List<Post> ep=new ArrayList<>();
        List<String> blocked=ClientAPI.getBlocked(currentUser);
        for(Post p: posts){
            List<String> pu=p.getPublisher().stream()
                    .map(a-> a.getUsername())
                    .collect(Collectors.toList());
            for(String s: pu){
                assert blocked != null;
                if(!blocked.contains(s)) {
                    assert blockers != null;
                    if (!blockers.contains(s)) {
                        ep.add(p);
                    }
                }
            }
        }
        explorePosts.setItems(FXCollections.observableArrayList(ep));
        explorePosts.setCellFactory(explorePosts -> new PostItem());
        myPosts.setItems(FXCollections.observableArrayList(currentUser.getPosts()));
        myPosts.setCellFactory(myPosts -> new PostItem());
        String temp=ClientAPI.getNumbers(currentUser, currentUser);
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
        path=file.getAbsolutePath();
        Image newImage=new Image(new ByteArrayInputStream(bytes));
        image.setImage(newImage);
    }

    public void publish(ActionEvent actionEvent) {
        Post currentPost = new Post();
        currentPost.setUser(currentUser);
        currentPost.getPublisher().add(currentUser);
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
            ClientAPI.addPost(currentPost, help, path);
        }
        Main.update();
        ClientAPI.getAllPosts(currentUser);
        for(User u: users.values()){
            ClientAPI.getMyPosts(u);
        }
        List<Post> posts=ClientAPI.getAllPosts(currentUser);
        List<String> f=ClientAPI.getFollowings(currentUser);
        List<String> m=ClientAPI.getMuted(currentUser);
        Set<Post> t=new HashSet<>();
        for(Post p: posts){
            assert f != null;
            if(f.contains(p.getUser().getUsername())) {
                assert m != null;
                if (!(m.contains(p.getUser().getUsername()))) {
                    t.add(p);
                }
            }
            if(currentUser.getUsername().equals(p.getUser().getUsername())){
                t.add(p);
            }
            List<String> pu=p.getPublisher().stream()
                    .map(a-> a.getUsername())
                    .collect(Collectors.toList());
            for(String s: pu){
                if(f.contains(s)){
                    assert m != null;
                    if (!(m.contains(p.getUser().getUsername()))) {
                        t.add(p);
                    }
                }
                if(currentUser.getUsername().equals(s)){
                    t.add(p);
                }
            }
        }
        PostList.setItems(FXCollections.observableArrayList(t));
        PostList.setCellFactory(PostList -> new PostItem());
        List<Post> ep=new ArrayList<>();
        List<String> blocked=ClientAPI.getBlocked(currentUser);
        List<String> blockers=ClientAPI.getBlockers(currentUser);
        for(Post p: posts){
            List<String> pu=p.getPublisher().stream()
                    .map(a-> a.getUsername())
                    .collect(Collectors.toList());
            for(String s: pu){
                assert blocked != null;
                if(!blocked.contains(s)) {
                    assert blockers != null;
                    if (!blockers.contains(s)) {
                        ep.add(p);
                    }
                }
            }
        }
        explorePosts.setItems(FXCollections.observableArrayList(ep));
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
        String temp=ClientAPI.getNumbers(currentUser, currentUser);
        following.setText(String.valueOf(Integer.parseInt(temp.substring(0, temp.indexOf("|")))));
        follower.setText(String.valueOf(Integer.parseInt(temp.substring(temp.indexOf("|")+1, temp.lastIndexOf("|")))));
        post1.setText(String.valueOf(Integer.parseInt(temp.substring(temp.lastIndexOf("|")+1))));
    }

    public void openAccounts(Event event) {
        Main.update();
        ClientAPI.getAllUsers(currentUser);
        List<String> blockers=ClientAPI.getBlockers(currentUser);
        List<User> acc=new ArrayList<>();
        for(User u: Main.users.values()){
            if(!blockers.contains(u.getUsername())){
                acc.add(u);
            }
        }
        accounts.setItems(FXCollections.observableArrayList(acc));
        accounts.setCellFactory(accounts -> new UserItem());
    }

    public void openTimeline(Event event) {
        Main.update();
        ClientAPI.getAllPosts(currentUser);
        List<Post> posts=ClientAPI.getAllPosts(currentUser);
        List<String> f=ClientAPI.getFollowings(currentUser);
        List<String> m=ClientAPI.getMuted(currentUser);
        Set<Post> t=new HashSet<>();
        for(Post p: posts){
            assert f != null;
            if(f.contains(p.getUser().getUsername())) {
                assert m != null;
                if (!(m.contains(p.getUser().getUsername()))) {
                    t.add(p);
                }
            }
            if(currentUser.getUsername().equals(p.getUser().getUsername())){
                t.add(p);
            }
            List<String> pu=p.getPublisher().stream()
                    .map(a-> a.getUsername())
                    .collect(Collectors.toList());
            for(String s: pu){
                if(f.contains(s)){
                    assert m != null;
                    if (!(m.contains(p.getUser().getUsername()))) {
                        t.add(p);
                    }
                }
                if(currentUser.getUsername().equals(s)){
                    t.add(p);
                }
            }
        }
        ClientAPI.getTimeline(currentUser);
        PostList.setItems(FXCollections.observableArrayList(t));
        PostList.setCellFactory(PostList -> new PostItem());
    }

    public void search(ActionEvent actionEvent) {
        Main.update();
        ClientAPI.getAllUsers(currentUser);
        List<String> blockers=ClientAPI.getBlockers(currentUser);
        List<User> acc=new ArrayList<>();
        for(User u: Main.users.values()){
            if(!blockers.contains(u.getUsername())){
                acc.add(u);
            }
        }
        List<User> searched= acc
                .stream()
                .filter(a->a.getUsername().contains(searchField.getText()))
                .collect(Collectors.toList());
        accounts.setItems(FXCollections.observableArrayList(searched));
        accounts.setCellFactory(accounts -> new UserItem());
    }

    public void backToAll(ActionEvent actionEvent) {
        Main.update();
        ClientAPI.getAllUsers(currentUser);
        List<String> blockers=ClientAPI.getBlockers(currentUser);
        List<User> acc=new ArrayList<>();
        for(User u: Main.users.values()){
            if(!blockers.contains(u.getUsername())){
                acc.add(u);
            }
        }
        accounts.setItems(FXCollections.observableArrayList(acc));
        accounts.setCellFactory(accounts -> new UserItem());
        searchField.setText("");
    }

    public void openExplore(Event event) {
        List<Post> posts=ClientAPI.getAllPosts(currentUser);
        List<Post> ep=new ArrayList<>();
        List<String> blocked=ClientAPI.getBlocked(currentUser);
        List<String> blockers=ClientAPI.getBlockers(currentUser);
        for(Post p: posts){
            List<String> pu=p.getPublisher().stream()
                    .map(a-> a.getUsername())
                    .collect(Collectors.toList());
            for(String s: pu){
                assert blocked != null;
                if(!blocked.contains(s)) {
                    assert blockers != null;
                    if (!blockers.contains(s)) {
                        ep.add(p);
                    }
                }
            }
        }
        explorePosts.setItems(FXCollections.observableArrayList(ep));
        explorePosts.setCellFactory(explorePosts -> new PostItem());
    }

    public void backToMassages(MouseEvent mouseEvent) {
        Main.update();
        ClientAPI.getAllUsers(currentUser);
        List<String> blockers=ClientAPI.getBlockers(currentUser);
        List<User> shown= users.values().stream()
                .filter(a-> ((ClientAPI.getMassaged(currentUser).contains(a.getUsername()))&&(!(blockers.contains(a.getUsername())))))
                .collect(Collectors.toList());
        Massages.setItems(FXCollections.observableArrayList(shown));
        Massages.setCellFactory(Massages -> new DirectUserItem());
        searchForMassage.setText("");
    }

    public void openDM(Event event) {
        Main.update();
        ClientAPI.getAllUsers(currentUser);
        List<String> blockers=ClientAPI.getBlockers(currentUser);
        List<User> shown= users.values().stream()
                .filter(a-> ((ClientAPI.getMassaged(currentUser).contains(a.getUsername()))&&(!(blockers.contains(a.getUsername())))))
                .collect(Collectors.toList());
        Massages.setItems(FXCollections.observableArrayList(shown));
        Massages.setCellFactory(Massages -> new DirectUserItem());
    }

    public void searchOnMassages(MouseEvent mouseEvent) {
        Main.update();
        ClientAPI.getAllUsers(currentUser);
        List<String> blockers=ClientAPI.getBlockers(currentUser);
        List<User> searched= users.values()
                .stream()
                .filter(a-> ((a.getUsername().contains(searchForMassage.getText()))&&(!(blockers.contains(a.getUsername())))))
                .collect(Collectors.toList());
        Massages.setItems(FXCollections.observableArrayList(searched));
        Massages.setCellFactory(Massages -> new DirectUserItem());


    }
}

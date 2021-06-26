package Controller;

import Common.Massage;
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
import static Model.Main.posts;

/**
 * <h1>TimeLineController</h1>
 * <p>this class has same panes that show general parts of program</p>
 * this class controls a tab pane that has 5 tabs.
 * one of them is user's timeline. one is add post tab. one is accounts tab. one of them handles personal profile and the last one is user's direct massages.
 * @author Mehrsa Arabzadeh
 * @since 6/2/2021
 * @version 1.0
 */
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
    public JFXListView<User> Massages;
    public JFXTextField searchForMassage;
    public AnchorPane personalProfile;

    private static Comparator<Massage> massageCompare = (a, b) -> -1 * Long.compare(a.getCreatedTime(), b.getCreatedTime());
    private static Comparator<Massage> readCompare = (a,b) -> 1 * Boolean.compare(a.isRead(), b.isRead() );
    private static Comparator<Massage> mailCompare = readCompare.thenComparing(massageCompare);

    /**
     * its initialize features that user can has in its account
     */
    public void initialize(){
        Main.update();
        ClientAPI.getAllUsers(currentUser);
        for(User u: users.values()){
            ClientAPI.getAllOfMyPosts(u);
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
        //ClientAPI.getTimeline(currentUser);
        PostList.setItems(FXCollections.observableArrayList(t));
        PostList.setCellFactory(PostList -> new PostItem());

        Main.update();
        List<Post> p = ClientAPI.getMyPosts(currentUser);
        username.setText(currentUser.getUsername());
        byte[] x=ClientAPI.getProfile(currentUser);
        if(x!=null){
            Image newImage=new Image(new ByteArrayInputStream(x));
            profile.setImage(newImage);
        }
        myPosts.setItems(FXCollections.observableArrayList(p));
        myPosts.setCellFactory(myPosts -> new PostItem());
        String temp=ClientAPI.getNumbers(currentUser, currentUser);
        following.setText(String.valueOf(Integer.parseInt(temp.substring(0, temp.indexOf("|")))));
        follower.setText(String.valueOf(Integer.parseInt(temp.substring(temp.indexOf("|")+1, temp.lastIndexOf("|")))));
        post1.setText(String.valueOf(Integer.parseInt(temp.substring(temp.lastIndexOf("|")+1))));
        Map<String, String> info=ClientAPI.getInformation(currentUser);
        if(info!=null){
            if(info.get("bio")!=null){
                Bio.setText(info.get("bio"));
            }
            if(info.get("firstName")!=null){
                fullName=info.get("firstName");
            }
            if(info.get("lastName")!=null){
                fullName=" "+info.get("lastName");
            }
        }
        if(fullName!=null){
            name.setText(fullName);
        } else{
            name.setVisible(false);
        }
    }

    /**
     * this method refresh user's account and shows it the new information
     * @param actionEvent by click on a button
     * @throws IOException because of using pageLoader
     */
    public void refresh(ActionEvent actionEvent) throws IOException {
        /*Main.update();
        ClientAPI.getAllPosts(currentUser);
        for(User u: users.values()){
            ClientAPI.getMyPosts(u);
        }
        ClientAPI.getAllUsers(currentUser);*/
        new PageLoader().load("Timeline");
        /*username.setText(currentUser.getUsername());
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
        myPosts.setItems(FXCollections.observableArrayList(currentUser.getPosts()));
        myPosts.setCellFactory(myPosts -> new PostItem());
        String temp=ClientAPI.getNumbers(currentUser, currentUser);
        following.setText(String.valueOf(Integer.parseInt(temp.substring(0, temp.indexOf("|")))));
        follower.setText(String.valueOf(Integer.parseInt(temp.substring(temp.indexOf("|")+1, temp.lastIndexOf("|")))));
        post.setText(String.valueOf(Integer.parseInt(temp.substring(temp.lastIndexOf("|")+1))));
        List<User> shown= users.values().stream()
                .filter(a-> ((ClientAPI.getMassaged(currentUser).contains(a.getUsername()))&&(!(blockers.contains(a.getUsername())))))
                .collect(Collectors.toList());
        Massages.setItems(FXCollections.observableArrayList(shown));
        Massages.setCellFactory(Massages -> new DirectUserItem());*/
    }

    /**
     * it open user's timeline and refresh it automatic
     * @param event by click on a tab
     */
    public void openTimeline(Event event) {
        Main.update();
        ClientAPI.getAllUsers(currentUser);
        ClientAPI.getAllPosts(currentUser);
        for(User u: users.values()){
            ClientAPI.getAllOfMyPosts(u);
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
        ClientAPI.getTimeline(currentUser);
        PostList.setItems(FXCollections.observableArrayList(t));
        PostList.setCellFactory(PostList -> new PostItem());
    }

    /**
     * user can add photo to its new post by this method
     * @param actionEvent by click on a button
     * @throws IOException because of using FileInputStream
     */
    public void addPhoto(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser=new FileChooser();
        File file=fileChooser.showOpenDialog(new Popup());
        if(file!=null){
            FileInputStream fileInputStream=new FileInputStream(file);
            byte[] bytes=fileInputStream.readAllBytes();
            help=bytes;
            path=file.getAbsolutePath();
            Image newImage=new Image(new ByteArrayInputStream(bytes));
            image.setImage(newImage);
        }
    }

    /**
     * user can publish its new post and add it to its personal profile and its followers timeline
     * @param actionEvent by click on a button
     */
    public void publish(ActionEvent actionEvent) {
        Post currentPost = new Post();
        currentPost.setUser(currentUser);
        currentPost.getPublisher().add(currentUser);
        currentPost.setTitle(title.getText());
        currentPost.setText(post.getText());
        if(help!=null){
            currentPost.setImage(help);
            ClientAPI.addPost(currentPost, help, path);
        } else{
            ClientAPI.addPost(currentPost);
        }
        title.setText("");
        post.setText("");
        image.setImage(null);
        help=null;
    }

    /**
     * it's open accounts tab and user can find accounts to follow or see their page
     * @param event by click on a tab
     */
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

    /**
     * user can search in accounts by this method
     * @param actionEvent by click on a button
     */
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

    /**
     * it cancel searching and show all accounts again
     * @param actionEvent by click on a button
     */
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

    /**
     * its in user's personal page and user can edit its information here
     * @param actionEvent by click on a button
     * @throws IOException because of using pageLoader
     */
    public void editProfile(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("EditProfile");
        //EditProfileController.username.setText(currentUser.getUsername());
    }

    /**
     * user can log out of its account
     * @param actionEvent by click on a button
     * @throws IOException because of using pageLoader
     */
    public void logOut(ActionEvent actionEvent) throws IOException {
        ClientAPI.logOut(currentUser);
        new PageLoader().load("Login");
    }

    /**
     * it opens user's personal page and refresh it automatic
     * @param event by click on a tab
     */
    public void openProfile(Event event) {
        Main.update();
        List<Post> p = ClientAPI.getMyPosts(currentUser);
        username.setText(currentUser.getUsername());
        byte[] x=ClientAPI.getProfile(currentUser);
        if(x!=null){
            Image newImage=new Image(new ByteArrayInputStream(x));
            profile.setImage(newImage);
        }
        myPosts.setItems(FXCollections.observableArrayList(p));
        myPosts.setCellFactory(myPosts -> new PostItem());
        String temp=ClientAPI.getNumbers(currentUser, currentUser);
        following.setText(String.valueOf(Integer.parseInt(temp.substring(0, temp.indexOf("|")))));
        follower.setText(String.valueOf(Integer.parseInt(temp.substring(temp.indexOf("|")+1, temp.lastIndexOf("|")))));
        post1.setText(String.valueOf(Integer.parseInt(temp.substring(temp.lastIndexOf("|")+1))));
        Map<String, String> info=ClientAPI.getInformation(currentUser);
        if(info!=null){
            if(info.get("bio")!=null){
                Bio.setText(info.get("bio"));
            }
            if(info.get("firstName")!=null){
                fullName=info.get("firstName");
            }
            if(info.get("lastName")!=null){
                fullName=" "+info.get("lastName");
            }
        }
        if(fullName!=null){
            name.setText(fullName);
        } else{
            name.setVisible(false);
        }
    }

    /**
     * it opens user's direct and shows all users that it chats with
     * @param event by click on a tab
     */
    public void openDM(Event event) {
        Main.update();
        ClientAPI.getAllUsers(currentUser);
        List<String> blockers=ClientAPI.getBlockers(currentUser);
        List<User> shown= users.values().stream()
                .filter(a-> ((ClientAPI.getMassaged(currentUser).contains(a.getUsername()))&&(!(blockers.contains(a.getUsername())))))
                .collect(Collectors.toList());
        List<Massage> temp=new ArrayList<>();
        for(String u: Objects.requireNonNull(ClientAPI.getMassaged(currentUser))){
            List<Massage> received=ClientAPI.getMassages(currentUser).stream()
                    .filter(a-> ((a.getSender().getUsername().equals(u))&&(a.getReceiver().getUsername().equals(Main.currentUser.getUsername()))))
                    .sorted(mailCompare)
                    .collect(Collectors.toList());
            if(received.size()!=0){
                temp.add(received.get(0));
            }
        }
        List<User> f=temp.
                stream()
                .sorted(mailCompare)
                .map(a-> a.getSender())
                .collect(Collectors.toList());
        List<String> strings=new ArrayList<>();
        List<User> finalList=new ArrayList<>();
        for(User u: f){
            for(User t: shown){
                if(t.getUsername().equals(u.getUsername())&&!(strings.contains(t.getUsername()))){
                    finalList.add(t);
                    strings.add(t.getUsername());
                }
            }
        }
        Massages.setItems(FXCollections.observableArrayList(finalList));
        Massages.setCellFactory(Massages -> new DirectUserItem());
    }

    /**
     * it cancel searching and show the accounts that user chats with only
     * @param mouseEvent by click on a photo
     */
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

    /**
     * user can search in all accounts by this method and massage them
     * @param mouseEvent by click on a photo
     */
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

    /**
     * user can come back to last page that it been
     * @param actionEvent by click on a button
     * @throws IOException because of using pageLoader
     */
    public void back(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("TimeLine");
    }
}

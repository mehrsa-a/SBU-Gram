package Controller;

import Common.User;
import Model.ClientAPI;
import Model.Main;
import Model.PageLoader;
import Common.Post;
import Model.PostItem;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static Controller.TimeLineController.*;
import static Model.Main.*;
import static Model.Main.posts;

/**
 * <h1>PostController</h1>
 * <p>this class shows posts in a special view</p>
 * @author Mehrsa Arabzadeh
 * @since 6/2/2021
 * @version 1.0
 */
public class PostController {
    public ImageView profile;
    public Label username;
    public Label name;
    public Label title;
    public Label post;
    public ImageView image;
    public Label date;
    public ImageView notLiked;
    public ImageView liked;
    public Label numberOfLikes;
    public Label numberOfReposts;
    public Label numberOfComments;
    public int likeNum=0, repostNum=0, commentNum=0;
    public AnchorPane postPane;
    public Post target;
    public static Post help;
    public String fullName;
    public ImageView reposted;
    public JFXButton notReposted;
    public Label alreadyRepost;
    public Label mineRepost;

    /**
     * its just a constructor
     * @param post it initialize global post with this
     * @throws IOException because of using pageLoader
     */
    public PostController(Post post) throws IOException {
        target=post;
        //currentPost=post;
        new PageLoader().load("Post", this);
    }

    /**
     * this method initialize post features
     * @return the pane that shows the post
     */
    public AnchorPane init(){
        alreadyRepost.setVisible(false);
        mineRepost.setVisible(false);
        username.setText(target.getUser().getUsername());
        date.setText(target.getTimeString());
        byte[] x=ClientAPI.getProfile(target.getUser());
        if(x!=null){
            Image newImage=new Image(new ByteArrayInputStream(x));
            profile.setImage(newImage);
        }
        title.setText(target.getTitle());
        post.setText(target.getText());
        byte[] y=target.getImage();
        if(y!=null){
            Image newImage=new Image(new ByteArrayInputStream(y));
            image.setImage(newImage);
        }
        String temp=ClientAPI.getPostFeatures(target);
        numberOfLikes.setText(temp.substring(0, temp.indexOf("|")));
        numberOfReposts.setText(temp.substring(temp.indexOf("|")+1, temp.lastIndexOf("|")));
        numberOfComments.setText(temp.substring(temp.lastIndexOf("|")+1));
        List<String> list=ClientAPI.getLikes(target);
        if(list.contains(currentUser.getUsername())){
            liked.setVisible(true);
            notLiked.setVisible(false);
        }
        List<String> r=ClientAPI.getReposts(target);
        if(r.contains(currentUser.getUsername())){
            reposted.setVisible(true);
        }
        Map<String, String> info=ClientAPI.getInformation(target.getUser());
        if(info!=null){
            if(info.get("firstName")!=null){
                fullName=info.get("firstName");
            }
            if(info.get("lastName")!=null){
                fullName=" "+info.get("lastName");
            }
        }
        if(fullName!=null){
            name.setText(fullName);
        } else {
            name.setVisible(false);
        }
        return postPane;
    }

    /**
     * user can like or take its like back by this method
     * @param actionEvent by click on a button
     */
    public void like(ActionEvent actionEvent) {
        if(!liked.isVisible()){
            likeNum= ClientAPI.like(Main.currentUser, target);
            numberOfLikes.setText(String.valueOf(likeNum));
            liked.setVisible(true);
            notLiked.setVisible(false);
        }
        else{
            likeNum= ClientAPI.dislike(Main.currentUser, target);
            numberOfLikes.setText(String.valueOf(likeNum));
            notLiked.setVisible(true);
            liked.setVisible(false);
        }
    }

    /**
     * user can repost the target post and add it to its posts
     * @param actionEvent by click on a button
     */
    public void repost(ActionEvent actionEvent) {
        if(reposted.isVisible()){
            alreadyRepost.setVisible(true);
        }
        if(target.getUser().getUsername().equals(currentUser.getUsername())){
            mineRepost.setVisible(true);
        }
        if(!reposted.isVisible()){
            target.getPublisher().add(currentUser);
            repostNum=ClientAPI.repost(Main.currentUser, target);
            numberOfReposts.setText(String.valueOf(repostNum));
            currentUser.getPosts().add(target);
            //ClientAPI.addPost(currentPost);
            Main.update();
            ClientAPI.getAllPosts(currentUser);
            for(User u: users.values()){
                ClientAPI.getMyPosts(u);
            }
            reposted.setVisible(true);
        }
    }

    /**
     * user can view post's comments
     * @param actionEvent by click on a button
     * @throws IOException because of using pageLoader
     */
    public void viewComments(ActionEvent actionEvent) throws IOException {
        help=target;
        new PageLoader().load("Comments");
    }

    /**
     * user can view the post's writer's personal profile
     * @param actionEvent by click on a button
     * @throws IOException because of using pageLoader
     */
    public void viewProfile(ActionEvent actionEvent) throws IOException {
        UserController.help=target.getUser();
        if(!(target.getUser().getUsername().equals(currentUser.getUsername()))){
            new PageLoader().load("Users");
        } else{
            new PageLoader().load("PersonalProfile");
        }
    }
}

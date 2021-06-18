package Controller;

import Common.User;
import Model.ClientAPI;
import Model.Main;
import Model.PageLoader;
import Common.Post;
import Model.PostItem;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import static Controller.TimeLineController.*;
import static Model.Main.*;
import static Model.Main.posts;

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

    public PostController(Post post) throws IOException {
        target=post;
        //currentPost=post;
        new PageLoader().load("Post", this);
    }

    public AnchorPane init(){
        username.setText(target.getUser().getUsername());
        byte[] x=ClientAPI.getProfile(currentUser);
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
        return postPane;
    }

    public void like(ActionEvent actionEvent) {
        if(!liked.isVisible()){
            likeNum= ClientAPI.like(target);
            numberOfLikes.setText(String.valueOf(likeNum));
            liked.setVisible(true);
            notLiked.setVisible(false);
        }
        else{
            likeNum= ClientAPI.dislike(target);
            numberOfLikes.setText(String.valueOf(likeNum));
            notLiked.setVisible(true);
            liked.setVisible(false);
        }
    }

    public void repost(ActionEvent actionEvent) {
        repostNum=ClientAPI.repost(target);
        numberOfReposts.setText(String.valueOf(repostNum));
        currentUser.getPosts().add(target);
        ClientAPI.addPost(target);
        Main.update();
        ClientAPI.getAllPosts();
        for(User u: users.values()){
            ClientAPI.getMyPosts(u);
        }
    }

    public void viewComments(ActionEvent actionEvent) throws IOException {
        help=target;
        new PageLoader().load("Comments");
    }

    public void viewProfile(ActionEvent actionEvent) throws IOException {
        UserController.help=target.getUser();
        new PageLoader().load("Users");
    }
}

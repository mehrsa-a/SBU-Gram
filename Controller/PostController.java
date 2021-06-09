package Controller;

import Model.PageLoader;
import Common.Post;
import Model.PostItem;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

import static Controller.TimeLineController.*;
import static Model.Main.currentPost;
import static Model.Main.currentUser;

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

    public PostController(Post post) throws IOException {
        currentPost=post;
        new PageLoader().load("Post", this);
    }

    public AnchorPane init(){
        username.setText(currentPost.getUser().getUsername());
        title.setText(currentPost.getTitle());
        post.setText(currentPost.getText());
        return postPane;
    }

    public void like(ActionEvent actionEvent) {
        if(!liked.isVisible()){
            liked.setVisible(true);
            notLiked.setVisible(false);
            likeNum++;
            numberOfLikes.setText(String.valueOf(likeNum));
        }
        else{
            notLiked.setVisible(true);
            liked.setVisible(false);
            likeNum--;
            numberOfLikes.setText(String.valueOf(likeNum));
        }
    }

    public void repost(ActionEvent actionEvent) {
        repostNum++;
        numberOfReposts.setText(String.valueOf(repostNum));
        currentUser.getPosts().add(currentPost);
    }

    public void viewComments(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("Comments");
    }

    public void viewProfile(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("Users");
    }
}

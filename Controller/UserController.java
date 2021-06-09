package Controller;

import Model.Main;
import Model.PageLoader;
import Common.User;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

import static Model.Main.currentUser;

public class UserController {
    public ImageView profile;
    public Label username;
    public Label name;
    public Label post;
    public Label follower;
    public Label following;
    public int postNum=0, followerNum=0, followingNum=0;
    public AnchorPane userPane;
    public JFXButton followingButton;
    public JFXButton unfollowing;

    public UserController(User user) throws IOException {
        currentUser=user;
        new PageLoader().load("User", this);
    }

    public AnchorPane init(){
        username.setText(currentUser.getUsername());
        return userPane;
    }

    public void follow(ActionEvent actionEvent) {
        followerNum++;
        follower.setText(String.valueOf(followerNum));
        unfollowing.setVisible(true);
        followingButton.setVisible(false);
    }

    public void viewProfile(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("Users");
    }

    public void unfollow(ActionEvent actionEvent) {
        followerNum--;
        follower.setText(String.valueOf(followerNum));
        followingButton.setVisible(true);
        unfollowing.setVisible(false);
    }
}

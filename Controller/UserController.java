package Controller;

import Model.Main;
import Model.PageLoader;
import Common.User;
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
    }

    public void viewProfile(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("Users");
    }
}

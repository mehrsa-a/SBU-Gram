package Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

public class UsersController {
    public JFXListView Posts;
    public Label Bio;
    public Label username;
    public Label post;
    public Label follower;
    public Label following;
    public int postNum=0, followerNum=0, followingNum=0;
    public JFXButton unfollowing;
    public JFXButton followingButton;

    public void follow(ActionEvent actionEvent) {
        followerNum++;
        follower.setText(String.valueOf(followerNum));
        unfollowing.setVisible(true);
        followingButton.setVisible(false);
    }

    public void back(ActionEvent actionEvent) {
    }

    public void unfollow(ActionEvent actionEvent) {
        followerNum--;
        follower.setText(String.valueOf(followerNum));
        followingButton.setVisible(true);
        unfollowing.setVisible(false);
    }
}

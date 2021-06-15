package Controller;

import Common.Post;
import Common.User;
import Model.ClientAPI;
import Model.PageLoader;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.List;

import static Model.Main.currentUser;

public class UsersController {
    public JFXListView<Post> Posts;
    public Label Bio;
    public Label username;
    public Label post;
    public Label follower;
    public Label following;
    public int postNum=0, followerNum=0, followingNum=0;
    public JFXButton unfollowing;
    public JFXButton followingButton;
    public AnchorPane userPane;
    public static User target;

    public void back(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("TimeLine");
    }

    public AnchorPane initialize(){
        target=UserController.target;
        username.setText(target.getUsername());
        String temp=ClientAPI.getNumbers(target);
        followingNum=Integer.parseInt(temp.substring(0, temp.indexOf("|")));
        following.setText(String.valueOf(followerNum));
        followerNum=Integer.parseInt(temp.substring(temp.indexOf("|")+1, temp.lastIndexOf("|")));
        follower.setText(String.valueOf(followerNum));
        postNum=Integer.parseInt(temp.substring(temp.lastIndexOf("|")+1));
        post.setText(String.valueOf(postNum));
        List<String> targetFollowers=ClientAPI.getFollowers(target);
        if(targetFollowers.contains(currentUser.getUsername())){
            unfollowing.setVisible(true);
            followingButton.setVisible(false);
        }
        return userPane;
    }

    public void follow(ActionEvent actionEvent) {
        String temp= ClientAPI.follow(target);
        followerNum=Integer.parseInt(temp.substring(temp.indexOf("|")+1));
        follower.setText(String.valueOf(followerNum));
        unfollowing.setVisible(true);
        followingButton.setVisible(false);
    }

    public void unfollow(ActionEvent actionEvent) {
        followerNum--;
        follower.setText(String.valueOf(followerNum));
        followingButton.setVisible(true);
        unfollowing.setVisible(false);
    }
}

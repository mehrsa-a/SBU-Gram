package Controller;

import Model.ClientAPI;
import Model.Main;
import Model.PageLoader;
import Common.User;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

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
    public User target;
    public static User help;
    public String fullName;

    public UserController(User user) throws IOException {
        target=user;
        //currentUser=user;
        new PageLoader().load("User", this);
    }

    public AnchorPane init(){
        username.setText(target.getUsername());
        byte[] x=ClientAPI.getProfile(target);
        if(x!=null){
            Image newImage=new Image(new ByteArrayInputStream(x));
            profile.setImage(newImage);
        }
        String temp=ClientAPI.getNumbers(target);
        followingNum=Integer.parseInt(temp.substring(0, temp.indexOf("|")));
        following.setText(String.valueOf(followingNum));
        followerNum=Integer.parseInt(temp.substring(temp.indexOf("|")+1, temp.lastIndexOf("|")));
        follower.setText(String.valueOf(followerNum));
        postNum=Integer.parseInt(temp.substring(temp.lastIndexOf("|")+1));
        post.setText(String.valueOf(postNum));
        List<String> targetFollowers=ClientAPI.getFollowers(target);
        if(targetFollowers.contains(currentUser.getUsername())){
            unfollowing.setVisible(true);
            followingButton.setVisible(false);
        }
        Map<String, String> info=ClientAPI.getInformation(target);
        if(info!=null){
            if(info.get("firstName") != null){
                fullName=info.get("firstName");
            }
            if(info.get("lastName") != null){
                fullName=" "+info.get("lastName");
            }
        }
        if(fullName != null){
            name.setText(fullName);
        } else{
            name.setVisible(false);
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

    public void viewProfile(ActionEvent actionEvent) throws IOException {
        help=target;
        new PageLoader().load("Users");
    }

    public void unfollow(ActionEvent actionEvent) {
        String temp= ClientAPI.unfollow(target);
        followerNum=Integer.parseInt(temp.substring(temp.indexOf("|")+1));
        follower.setText(String.valueOf(followerNum));
        followingButton.setVisible(true);
        unfollowing.setVisible(false);
    }
}

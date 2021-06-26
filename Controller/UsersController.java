package Controller;

import Common.Post;
import Common.User;
import Model.ClientAPI;
import Model.Main;
import Model.PageLoader;
import Model.PostItem;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
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

import static Model.Main.currentUser;

/**
 * <h1>UsersController</h1>
 * <p>this class shows all information of a user</p>
 * @author Mehrsa Arabzadeh
 * @since 6/2/2021
 * @version 1.0
 */
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
    public User target;
    public ImageView profile;
    public Label name;
    public String fullName;
    public JFXButton blockButton;
    public JFXButton unblockButton;
    public JFXButton muteButton;
    public JFXButton unMuteButton;

    /**
     * user can come back to last page that it been
     * @param actionEvent by click on a button
     * @throws IOException because of using pageLoader
     */
    public void back(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("TimeLine");
    }

    /**
     * this method initialize user features
     * @return the pane that shows the user
     */
    public AnchorPane initialize(){
        target=UserController.help;
        username.setText(target.getUsername());
        byte[] x=ClientAPI.getProfile(target);
        if(x!=null){
            Image newImage=new Image(new ByteArrayInputStream(x));
            profile.setImage(newImage);
        }
        String temp=ClientAPI.getNumbers(Main.currentUser, target);
        followingNum=Integer.parseInt(temp.substring(0, temp.indexOf("|")));
        following.setText(String.valueOf(followingNum));
        followerNum=Integer.parseInt(temp.substring(temp.indexOf("|")+1, temp.lastIndexOf("|")));
        follower.setText(String.valueOf(followerNum));
        postNum=Integer.parseInt(temp.substring(temp.lastIndexOf("|")+1));
        post.setText(String.valueOf(postNum));
        List<String> targetFollowers=ClientAPI.getFollowers(target);
        assert targetFollowers != null;
        if(targetFollowers.contains(currentUser.getUsername())){
            unfollowing.setVisible(true);
            followingButton.setVisible(false);
            List<String> muteNames=ClientAPI.getMuted(currentUser);
            assert muteNames != null;
            if(muteNames.contains(target.getUsername())){
                muteButton.setVisible(false);
                unMuteButton.setVisible(true);
            } else{
                unMuteButton.setVisible(false);
                muteButton.setVisible(true);
            }
        }
        List<Post> p = (List<Post>) ClientAPI.getMyPosts(target);
        Posts.setItems(FXCollections.observableArrayList(p));
        Posts.setCellFactory(Posts -> new PostItem());
        Map<String, String> info=ClientAPI.getInformation(target);
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
        List<String> blockName=ClientAPI.getBlocked(currentUser);
        assert blockName != null;
        if(blockName.contains(target.getUsername())){
            blockButton.setVisible(false);
            unblockButton.setVisible(true);
            followingButton.setVisible(false);
            unfollowing.setVisible(false);
        } else{
            unblockButton.setVisible(false);
            blockButton.setVisible(true);
        }
        return userPane;
    }

    /**
     * the user can follow target user with this method
     * @param actionEvent by click on a button
     */
    public void follow(ActionEvent actionEvent) {
        String temp= ClientAPI.follow(Main.currentUser, target);
        followerNum=Integer.parseInt(temp.substring(temp.indexOf("|")+1));
        follower.setText(String.valueOf(followerNum));
        unfollowing.setVisible(true);
        followingButton.setVisible(false);
        muteButton.setVisible(true);
        unMuteButton.setVisible(false);
    }

    /**
     * the user can unfollow target user with this method
     * @param actionEvent by click on a button
     */
    public void unfollow(ActionEvent actionEvent) {
        String temp= ClientAPI.unfollow(Main.currentUser, target);
        followerNum=Integer.parseInt(temp.substring(temp.indexOf("|")+1));
        follower.setText(String.valueOf(followerNum));
        followingButton.setVisible(true);
        unfollowing.setVisible(false);
        muteButton.setVisible(false);
        unMuteButton.setVisible(false);
    }

    /**
     * the user can block target user with this method
     * @param actionEvent by click on a button
     */
    public void block(ActionEvent actionEvent) {
        ClientAPI.block(currentUser, target);
        blockButton.setVisible(false);
        unblockButton.setVisible(true);
        unfollowing.setVisible(false);
        followingButton.setVisible(false);
    }

    /**
     * the user can unblock target user with this method
     * @param actionEvent by click on a button
     */
    public void unblock(ActionEvent actionEvent) {
        ClientAPI.unblock(currentUser, target);
        unblockButton.setVisible(false);
        blockButton.setVisible(true);
        followingButton.setVisible(true);
        unfollowing.setVisible(false);
    }

    /**
     * the user can mute target user with this method
     * @param actionEvent by click on a button
     */
    public void mute(ActionEvent actionEvent) {
        ClientAPI.mute(currentUser, target);
        muteButton.setVisible(false);
        unMuteButton.setVisible(true);
        currentUser.getMuted().add(target);
    }

    /**
     * the user can unMute target user with this method
     * @param actionEvent by click on a button
     */
    public void unMute(ActionEvent actionEvent) {
        ClientAPI.unMute(currentUser, target);
        unMuteButton.setVisible(false);
        muteButton.setVisible(true);
        currentUser.getMuted().add(target);
    }
}

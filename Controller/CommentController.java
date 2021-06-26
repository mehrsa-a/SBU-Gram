package Controller;

import Common.Comment;
import Common.Post;
import Model.ClientAPI;
import Model.PageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import static Model.Main.currentUser;

/**
 * <h1>CommentController</h1>
 * <p>this class shows comments in a special view</p>
 * @author Mehrsa Arabzadeh
 * @since 6/2/2021
 * @version 1.0
 */
public class CommentController {
    public ImageView profile;
    public Label username;
    public Label name;
    public Label date;
    public Label title;
    public Label post;
    public AnchorPane postPane;
    public Comment target;

    /**
     * its just a constructor
     * @param post it initialize global comment with this
     * @throws IOException because of using pageLoader
     */
    public CommentController(Comment post) throws IOException {
        target=post;
        //currentPost=post;
        new PageLoader().load("Comment", this);
    }

    /**
     * this method initialize comment features
     * @return the pane that shows the comment
     */
    public AnchorPane init(){
        username.setText(target.getUser().getUsername());
        byte[] x=ClientAPI.getProfile(target.getUser());
        if(x!=null){
            Image newImage=new Image(new ByteArrayInputStream(x));
            profile.setImage(newImage);
        }
        title.setText(target.getTitle());
        post.setText(target.getText());
        return postPane;
    }

    /**
     * user can see the personal page of comment's writer
     * @param actionEvent by click on a button
     * @throws IOException because of using pageLoader
     */
    public void viewProfile(ActionEvent actionEvent) throws IOException {
        UserController.help=target.getUser();
        new PageLoader().load("Users");
    }
}

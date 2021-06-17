package Controller;

import Common.Comment;
import Common.Post;
import Model.ClientAPI;
import Model.PageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.List;

import static Model.Main.currentUser;

public class CommentController {
    public ImageView profile;
    public Label username;
    public Label name;
    public Label date;
    public Label title;
    public Label post;
    public AnchorPane postPane;
    public Comment target;

    public CommentController(Comment post) throws IOException {
        target=post;
        //currentPost=post;
        new PageLoader().load("Comment", this);
    }

    public AnchorPane init(){
        username.setText(target.getUser().getUsername());
        title.setText(target.getTitle());
        post.setText(target.getText());
        return postPane;
    }

    public void viewProfile(ActionEvent actionEvent) throws IOException {
        UserController.help=target.getUser();
        new PageLoader().load("Users");
    }
}

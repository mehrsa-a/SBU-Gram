package Controller;

import Common.Comment;
import Model.*;
import Common.Post;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>CommentsController</h1>
 * <p>this page show all post's comments and user can add new comment too </p>
 * @author Mehrsa Arabzadeh
 * @since 6/2/2021
 * @version 1.0
 */
public class CommentsController {
    public List<Comment> comment=new ArrayList<>();
    public JFXListView<Comment> comments=new JFXListView<>();
    public Comment currentComment=new Comment();
    public JFXTextArea cm;

    /**
     * this method load all comments that post has
     */
    public void initialize(){
        comment=ClientAPI.getComments(PostController.help);
        comments.setItems(FXCollections.observableArrayList(comment));
        comments.setCellFactory(comments -> new CommentItem());
    }

    /**
     * this method initialize and add new comment
     * @param actionEvent by click on a button
     */
    public void addComment(ActionEvent actionEvent) {
        Comment currentComment=new Comment();
        currentComment.setTitle("in reply to "+ PostController.help.getUser().getUsername());
        currentComment.setText(cm.getText());
        currentComment.setUser(Main.currentUser);
        comment= ClientAPI.addComment(Main.currentUser, PostController.help, currentComment);
        comment=ClientAPI.getComments(PostController.help);
        assert comment != null;
        comment.add(currentComment);
        comments.setItems(FXCollections.observableArrayList(comment));
        comments.setCellFactory(comments -> new CommentItem());
        cm.setText("");

    }

    /**
     * user can come back to last page that it been
     * @param actionEvent by click on a button
     * @throws IOException because of using pageLoader
     */
    public void back(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("TimeLine");
    }
}

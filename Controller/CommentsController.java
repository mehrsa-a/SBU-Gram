package Controller;

import Common.Comment;
import Model.*;
import Common.Post;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CommentsController {
    public List<Comment> comment=new ArrayList<>();
    public JFXListView<Comment> comments=new JFXListView<>();
    public Comment currentComment=new Comment();
    public JFXTextField cm;

    public void initialize(){
        comment=ClientAPI.getComments(PostController.help);
        comments.setItems(FXCollections.observableArrayList(comment));
        comments.setCellFactory(comments -> new CommentItem());
    }

    public void addComment(ActionEvent actionEvent) {
        currentComment.setTitle("in reply to "+ PostController.help.getUser().getUsername());
        currentComment.setText(cm.getText());
        comment= ClientAPI.addComment(Main.currentUser, PostController.help, currentComment);
        comment.add(currentComment);
        comments.setItems(FXCollections.observableArrayList(comment));
        comments.setCellFactory(comments -> new CommentItem());
        cm.setText("");

    }

    public void back(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("TimeLine");
    }
}

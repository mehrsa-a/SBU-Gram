package Controller;

import Model.ClientAPI;
import Model.Main;
import Model.PageLoader;
import Common.Post;
import Model.PostItem;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CommentsController {
    public List<Post> comment=new ArrayList<>();
    public JFXListView<Post> comments=new JFXListView<>();
    public Post currentComment=new Post();
    public JFXTextField cm;

    public void initialize(){
        comment=ClientAPI.getComments(PostController.help);
        comments.setItems(FXCollections.observableArrayList(comment));
        comments.setCellFactory(PostList -> new PostItem());
    }

    public void addComment(ActionEvent actionEvent) {
        currentComment.setTitle("in reply to "+ PostController.help.getUser().getUsername());
        currentComment.setText(cm.getText());
        comment= ClientAPI.addComment(PostController.help, currentComment);
        comment.add(currentComment);
        comments.setItems(FXCollections.observableArrayList(comment));
        comments.setCellFactory(PostList -> new PostItem());
        cm.setText("");

    }

    public void back(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("TimeLine");
    }
}

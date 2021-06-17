package Model;

import Common.Comment;
import Common.Post;
import Controller.CommentController;
import Controller.PostController;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class CommentItem extends ListCell<Comment> {
    public void updateItem(Comment post, boolean empty) {
        super.updateItem(post, empty);
        if (post != null) {
            try {
                setGraphic(new CommentController(post).init());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

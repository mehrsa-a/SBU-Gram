package Model;

import Common.Comment;
import Common.Post;
import Controller.CommentController;
import Controller.PostController;
import javafx.scene.control.ListCell;

import java.io.IOException;

/**
 * <h1>CommentItem</h1>
 * <p>this class used for showing every comment in comments page</p>
 * @author Mehrsa Arabzadeh
 * @since 6/2/2021
 * @version 1.0
 */
public class CommentItem extends ListCell<Comment> {

    /**
     * this method uses for show items in list view
     * @param post its the target comment that shows
     * @param empty its boolean that uses for show item
     */
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

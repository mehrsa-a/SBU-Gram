package Model;

import Common.Post;
import Controller.PostController;
import javafx.scene.control.ListCell;

import java.io.IOException;

/**
 * <h1>PostItem</h1>
 * <p>this class used for showing every post in timeline or personal profiles</p>
 * @author Mehrsa Arabzadeh
 * @since 6/2/2021
 * @version 1.0
 */
public class PostItem extends ListCell<Post> {
    /**
     * this method uses for show items in list view
     * @param post its the target post that shows
     * @param empty its boolean that uses for show item
     */
    public void updateItem(Post post, boolean empty) {
        super.updateItem(post, empty);
        if (post != null) {
            try {
                setGraphic(new PostController(post).init());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

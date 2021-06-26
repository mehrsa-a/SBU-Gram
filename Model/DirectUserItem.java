package Model;

import Common.Comment;
import Common.Massage;
import Common.User;
import Controller.CommentController;
import Controller.DirectUsersController;
import javafx.scene.control.ListCell;

import java.io.IOException;

/**
 * <h1>DirectUserItem</h1>
 * <p>this class used for showing every account that user can massage</p>
 * @author Mehrsa Arabzadeh
 * @since 6/2/2021
 * @version 1.0
 */
public class DirectUserItem extends ListCell<User> {
    /**
     * this method uses for show items in list view
     * @param post its the target user that shows
     * @param empty its boolean that uses for show item
     */
    public void updateItem(User post, boolean empty) {
        super.updateItem(post, empty);
        if (post != null) {
            try {
                setGraphic(new DirectUsersController(post).init());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

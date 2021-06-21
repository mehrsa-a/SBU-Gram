package Model;

import Common.Comment;
import Common.Massage;
import Common.User;
import Controller.CommentController;
import Controller.DirectUsersController;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class DirectUserItem extends ListCell<User> {
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

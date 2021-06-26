package Model;

import Common.User;
import Controller.UserController;
import javafx.scene.control.ListCell;

import java.io.IOException;

/**
 * <h1>UserItem</h1>
 * <p>this class used for showing every account in accounts list</p>
 * @author Mehrsa Arabzadeh
 * @since 6/2/2021
 * @version 1.0
 */
public class UserItem extends ListCell<User> {
    /**
     * this method uses for show items in list view
     * @param user its the target user that shows
     * @param empty its boolean that uses for show item
     */
    public void updateItem(User user, boolean empty) {
        super.updateItem(user, empty);
        if (user != null) {
            try {
                setGraphic(new UserController(user).init());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

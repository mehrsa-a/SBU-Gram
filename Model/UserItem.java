package Model;

import Common.User;
import Controller.UserController;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class UserItem extends ListCell<User> {
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

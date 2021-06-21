package Model;

import Common.Massage;
import Common.User;
import Controller.DirectUsersController;
import Controller.MassageController;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class MassageItem extends ListCell<Massage> {
    public void updateItem(Massage post, boolean empty) {
        super.updateItem(post, empty);
        if (post != null) {
            try {
                setGraphic(new MassageController(post).init());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

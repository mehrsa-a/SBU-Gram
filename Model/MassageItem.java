package Model;

import Common.Massage;
import Common.User;
import Controller.DirectUsersController;
import Controller.MassageController;
import javafx.scene.control.ListCell;

import java.io.IOException;

/**
 * <h1>MassageItem</h1>
 * <p>this class used for showing every massage that user sent or received</p>
 * @author Mehrsa Arabzadeh
 * @since 6/2/2021
 * @version 1.0
 */
public class MassageItem extends ListCell<Massage> {
    /**
     * this method uses for show items in list view
     * @param post its the target massage that shows
     * @param empty its boolean that uses for show item
     */
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

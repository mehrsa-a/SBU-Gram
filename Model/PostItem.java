package Model;

import Common.Post;
import Controller.PostController;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class PostItem extends ListCell<Post> {
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

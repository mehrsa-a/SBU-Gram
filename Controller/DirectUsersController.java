package Controller;

import Common.User;
import Model.PageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class DirectUsersController {
    public AnchorPane userPane;
    public Label username;
    public Label date;
    public User target;
    public static User help;

    public DirectUsersController(User user) throws IOException {
        target=user;
        //currentUser=user;
        new PageLoader().load("DirectUsers", this);
    }

    public AnchorPane init(){
        username.setText(target.getUsername());
        return userPane;
    }

    public void massage(ActionEvent actionEvent) throws IOException {
        help=target;
        new PageLoader().load("MassagePage");
    }
}

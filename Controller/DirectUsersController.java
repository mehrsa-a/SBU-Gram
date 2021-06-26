package Controller;

import Common.Massage;
import Common.User;
import Model.ClientAPI;
import Model.Main;
import Model.PageLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <h1>DirectUsersController</h1>
 * <p>this class shows all accounts with message button that we can send massage to them</p>
 * @author Mehrsa Arabzadeh
 * @since 6/2/2021
 * @version 1.0
 */
public class DirectUsersController {
    public AnchorPane userPane;
    public Label username;
    public Label date;
    public Label newMassage;
    public User target;
    public static User help;
    public List<Massage> massages=new ArrayList<>();
    public List<Boolean> readMassages=new ArrayList<>();

    /**
     * its just a constructor
     * @param user it initialize global user with this
     * @throws IOException because of using pageLoader
     */
    public DirectUsersController(User user) throws IOException {
        target=user;
        new PageLoader().load("DirectUsers", this);
    }

    /**
     * this method initialize user features
     * @return the pane that shows the users
     */
    public AnchorPane init(){
        massages= Objects.requireNonNull(ClientAPI.getMassages(Main.currentUser)).stream()
                .filter(a-> ((a.getSender().getUsername().equals(target.getUsername()))&&(a.getReceiver().getUsername().equals(Main.currentUser.getUsername()))))
                .collect(Collectors.toList());
        readMassages=massages.stream()
                .map(a-> a.isRead())
                .collect(Collectors.toList());
        newMassage.setVisible(readMassages.contains(false));
        username.setText(target.getUsername());
        return userPane;
    }

    /**
     * it shows massage page with the target user
     * @param actionEvent by click on a button
     * @throws IOException because of using pageLoader
     */
    public void massage(ActionEvent actionEvent) throws IOException {
        help=target;
        massages= Objects.requireNonNull(ClientAPI.getMassages(Main.currentUser)).stream()
                .filter(a-> ((a.getSender().getUsername().equals(target.getUsername()))&&(a.getReceiver().getUsername().equals(Main.currentUser.getUsername()))))
                .collect(Collectors.toList());
        for(Massage m: massages){
            m.setRead(true);
            ClientAPI.readMassage(Main.currentUser, m);
        }
        new PageLoader().load("MassagePage");
    }
}

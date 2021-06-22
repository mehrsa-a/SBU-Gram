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

public class DirectUsersController {
    public AnchorPane userPane;
    public Label username;
    public Label date;
    public Label newMassage;
    public User target;
    public static User help;
    public List<Massage> massages=new ArrayList<>();
    public List<Boolean> readMassages=new ArrayList<>();

    public DirectUsersController(User user) throws IOException {
        target=user;
        new PageLoader().load("DirectUsers", this);
    }

    public AnchorPane init(){
        massages= Objects.requireNonNull(ClientAPI.getMassages(Main.currentUser)).stream()
                .filter(a-> ((a.getSender().getUsername().equals(target.getUsername()))&&(a.getReceiver().getUsername().equals(Main.currentUser.getUsername()))))
                .collect(Collectors.toList());
        readMassages=massages.stream()
                .map(a-> a.isRead())
                .collect(Collectors.toList());
        newMassage.setVisible(readMassages.contains(true));
        username.setText(target.getUsername());
        return userPane;
    }

    public void massage(ActionEvent actionEvent) throws IOException {
        help=target;
        massages= Objects.requireNonNull(ClientAPI.getMassages(Main.currentUser)).stream()
                .filter(a-> ((a.getSender().getUsername().equals(target.getUsername()))&&(a.getReceiver().getUsername().equals(Main.currentUser.getUsername()))))
                .collect(Collectors.toList());
        for(Massage m: massages){
            ClientAPI.readMassage(Main.currentUser, m);
        }
        new PageLoader().load("MassagePage");
    }
}

package Controller;

import Model.*;
import Common.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static Controller.SignUpController.users;
import static Controller.TimeLineController.*;
import static Model.Main.currentUser;
import static Model.Main.posts;

public class LogInController {
    @FXML
    public JFXTextField username;
    public JFXPasswordField password;
    public JFXCheckBox visiblePassword;
    public JFXButton logIn;
    public JFXButton signUp;
    public Label wrong;
    public JFXTextField visiblePass;

    private static Map<String, String> members=new HashMap<>();

    public static Map<String, String> getMembers(){
        return members;
    }

    public static void add(String user, String pass){
        members.put(user, pass);
    }

    public void showPassword(ActionEvent actionEvent){
        if (!visiblePass.isVisible()) {
            visiblePass.setVisible(true);
            password.setVisible(false);
            visiblePass.setText(password.getText());
        }
        else {
            visiblePass.setVisible(false);
            password.setVisible(true);
            password.setText(visiblePass.getText());
        }
    }

    public void logIn(ActionEvent actionEvent) throws IOException{
        String user=username.getText();
        String pass;
        if(visiblePass.isVisible()){
            pass=visiblePass.getText();
        } else{
            pass=password.getText();
        }
        User check=ClientAPI.login(user, pass);
        if(check!=null){
            currentUser=check;
            //TimeLineController.username.setText(currentUser.getUsername());
            Main.update();
            ClientAPI.getAllPosts();
            ClientAPI.getMyPosts();
            ClientAPI.getAllUsers();
            new PageLoader().load("TimeLine");
        } else{
            wrong.setVisible(true);
        }
    }


    public void signUp(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("Signup");
    }
}

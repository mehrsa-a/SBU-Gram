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

import static Controller.TimeLineController.*;
import static Model.Main.*;

/**
 * <h1>LogInController</h1>
 * <p>a exist user can log in its page by entering its username and password correctly</p>
 * @author Mehrsa Arabzadeh
 * @since 6/2/2021
 * @version 1.0
 */
public class LogInController {
    @FXML
    public JFXTextField username;
    public JFXPasswordField password;
    public JFXCheckBox visiblePassword;
    public JFXButton logIn;
    public JFXButton signUp;
    public Label wrong;
    public JFXTextField visiblePass;

    /**
     * user can see its password or not
     * @param actionEvent by click on a button
     */
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

    /**
     * if username and password were for same user, this method loads the user's timeline
     * @param actionEvent by click on a button
     * @throws IOException because of using pageLoader
     */
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
            ClientAPI.getAllPosts(currentUser);
            for(User u: users.values()){
                ClientAPI.getMyPosts(u);
            }
            ClientAPI.getAllUsers(currentUser);
            new PageLoader().load("TimeLine");
        } else{
            wrong.setVisible(true);
        }
    }

    /**
     * user can made a new account here
     * @param actionEvent by click on a button
     * @throws IOException because of using pageLoader
     */
    public void signUp(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("Signup");
    }

    /**
     * it loads recovery password page
     * @param actionEvent by click on a button
     * @throws IOException because of using pageLoader
     */
    public void forgotPass(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("ForgetPassword");
    }
}

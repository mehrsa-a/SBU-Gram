package Controller;

import Model.ClientAPI;
import Model.Main;
import Model.PageLoader;
import Common.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.ArrayList;

import static Model.Main.currentUser;

/**
 * <h1>SignUpController</h1>
 * <p>a new person can create a new account if its chosen username doesn't exist yet</p>
 * @author Mehrsa Arabzadeh
 * @since 6/2/2021
 * @version 1.0
 */
public class SignUpController {
    public JFXTextField newUsername;
    public JFXPasswordField newPassword;
    public JFXPasswordField confirmPassword;
    public JFXCheckBox showPassword;
    public JFXButton continueSignup;
    public JFXTextField showNewPassword;
    public JFXTextField showConfirm;
    public Label existUsername;
    public Label shortPass;
    public Label matchPass;
    public Label truePassword;

    /**
     * user can see its password or not
     * @param actionEvent by click on a button
     */
    public void showPass(ActionEvent actionEvent) {
        if (!showNewPassword.isVisible()) {
            showNewPassword.setVisible(true);
            newPassword.setVisible(false);
            showConfirm.setVisible(true);
            confirmPassword.setVisible(false);
            showNewPassword.setText(newPassword.getText());
            showConfirm.setText(confirmPassword.getText());
        }
        else {
            showNewPassword.setVisible(false);
            newPassword.setVisible(true);
            showConfirm.setVisible(false);
            confirmPassword.setVisible(true);
            newPassword.setText(showNewPassword.getText());
            confirmPassword.setText(showConfirm.getText());
        }
    }

    /**
     * user can choose its password only by numbers and english letters
     * @param pass its the password that user adds
     * @return a boolean that shows the password is acceptable or not
     */
    public boolean truePass(String pass){
        for(int i=0; i<pass.length(); i++){
            if('a'<=pass.charAt(i)&&pass.charAt(i)<='z'){
                continue;
            } else if('0'<=pass.charAt(i)&&pass.charAt(i)<='9'){
                continue;
            } else if('A'<=pass.charAt(i)&&pass.charAt(i)<='Z'){
                continue;
            } else{
                return false;
            }
        }
        return true;
    }

    /**
     * if username and password were in a right way, this method create a new account for user
     * @param actionEvent by click on a button
     * @throws IOException because of using pageLoader
     */
    public void continueSign(ActionEvent actionEvent) throws IOException {
        String user=newUsername.getText();
        String pass, confirm;
        if(showNewPassword.isVisible()){
            pass=showNewPassword.getText();
            confirm=showConfirm.getText();
        } else{
            pass=newPassword.getText();
            confirm=confirmPassword.getText();
        }
        if(!pass.equals(confirm)){
            existUsername.setVisible(false);
            shortPass.setVisible(false);
            truePassword.setVisible(false);
            matchPass.setVisible(true);
        } else if(!ClientAPI.isUsernameValid(user)){
            shortPass.setVisible(false);
            matchPass.setVisible(false);
            truePassword.setVisible(false);
            existUsername.setVisible(true);
        } else if(pass.length()<8){
            existUsername.setVisible(false);
            matchPass.setVisible(false);
            truePassword.setVisible(false);
            shortPass.setVisible(true);
        } else if(!truePass(pass)){
            existUsername.setVisible(false);
            matchPass.setVisible(false);
            shortPass.setVisible(false);
            truePassword.setVisible(true);
        } else{
            existUsername.setVisible(false);
            matchPass.setVisible(false);
            shortPass.setVisible(false);
            truePassword.setVisible(false);
            currentUser=new User(user, pass);
            ClientAPI.signUp(currentUser);
            Main.users.put(user, currentUser);
            new PageLoader().load("AddInformation");
        }
    }

    /**
     * user can log in with an existing account instead of create a new one
     * @param actionEvent by click on a button
     * @throws IOException because of using pageLoader
     */
    public void login(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("Login");
    }
}

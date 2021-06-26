package Controller;

import Model.ClientAPI;
import Model.Main;
import Model.PageLoader;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

import java.io.IOException;

/**
 * <h1>DeleteAccountController</h1>
 * <p>user can delete its account here</p>
 * @author Mehrsa Arabzadeh
 * @since 6/2/2021
 * @version 1.0
 */
public class DeleteAccountController {
    public JFXPasswordField newPassword;
    public JFXPasswordField confirmPassword;
    public JFXCheckBox showPassword;
    public JFXTextField showNewPassword;
    public JFXTextField showConfirm;
    public Label matchPass;
    public Label falsePass;

    /**
     * user can come back to last page that it been
     * @param actionEvent by click on a button
     * @throws IOException because of using pageLoader
     */
    public void back(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("EditProfile");
    }

    /**
     * this method confirm deleting account
     * @param actionEvent by click on a button
     * @throws IOException because of using pageLoader
     */
    public void deleteAcc(ActionEvent actionEvent) throws IOException {
        if (!showNewPassword.isVisible()){
            if(!(showNewPassword.getText().equals(showConfirm.getText()))){
                falsePass.setVisible(false);
                matchPass.setVisible(true);
            } else if (!(showNewPassword.getText().equals(Main.currentUser.getPassword()))){
                matchPass.setVisible(false);
                falsePass.setVisible(true);
            } else{
                ClientAPI.deleteAccount(Main.currentUser);
                new PageLoader().load("Login");
            }
        } else{
            if(!(newPassword.getText().equals(confirmPassword.getText()))){
                falsePass.setVisible(false);
                matchPass.setVisible(true);
            } else if (!(newPassword.getText().equals(Main.currentUser.getPassword()))){
                matchPass.setVisible(false);
                falsePass.setVisible(true);
            } else{
                ClientAPI.deleteAccount(Main.currentUser);
                new PageLoader().load("Login");
            }
        }
    }

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
}

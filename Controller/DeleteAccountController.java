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

public class DeleteAccountController {
    public JFXPasswordField newPassword;
    public JFXPasswordField confirmPassword;
    public JFXCheckBox showPassword;
    public JFXTextField showNewPassword;
    public JFXTextField showConfirm;
    public Label matchPass;
    public Label falsePass;

    public void back(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("EditProfile");
    }

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

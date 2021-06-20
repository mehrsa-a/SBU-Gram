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

public class ChangePasswordController {
    public JFXPasswordField newPassword;
    public JFXPasswordField confirmNewPassword;
    public JFXCheckBox showPassword;
    public JFXTextField showNewPassword;
    public JFXTextField showConfirmNew;
    public JFXPasswordField oldPassword;
    public JFXPasswordField confirmOldPassword;
    public JFXTextField showOldPassword;
    public JFXTextField showConfirmOld;
    public Label matchOld;
    public Label matchNew;
    public Label falsePass;

    public void back(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("EditProfile");
    }

    public void changePass(ActionEvent actionEvent) throws IOException {
        if (!newPassword.isVisible()){
            if(!(oldPassword.getText().equals(confirmOldPassword.getText()))){
                matchNew.setVisible(false);
                falsePass.setVisible(false);
                matchOld.setVisible(true);
            } else if(!(oldPassword.getText().equals(Main.currentUser.getPassword()))){
                matchOld.setVisible(false);
                matchNew.setVisible(false);
                falsePass.setVisible(true);
            } else if(!(newPassword.getText().equals(confirmNewPassword.getText()))){
                falsePass.setVisible(false);
                matchOld.setVisible(false);
                matchNew.setVisible(true);
            } else{
                ClientAPI.changePassword(Main.currentUser, newPassword.getText());
                new PageLoader().load("EditProfile");
            }
        } else{
            if(!(showOldPassword.getText().equals(showConfirmOld.getText()))){
                matchNew.setVisible(false);
                falsePass.setVisible(false);
                matchOld.setVisible(true);
            } else if(!(showOldPassword.getText().equals(Main.currentUser.getPassword()))){
                matchOld.setVisible(false);
                matchNew.setVisible(false);
                falsePass.setVisible(true);
            } else if(!(showNewPassword.getText().equals(showConfirmNew.getText()))){
                falsePass.setVisible(false);
                matchOld.setVisible(false);
                matchNew.setVisible(true);
            } else{
                ClientAPI.changePassword(Main.currentUser, showNewPassword.getText());
                new PageLoader().load("EditProfile");
            }
        }
    }

    public void showPass(ActionEvent actionEvent) {
        if (!newPassword.isVisible()) {
            newPassword.setVisible(true);
            newPassword.setText(showNewPassword.getText());
            confirmNewPassword.setVisible(true);
            confirmNewPassword.setText(showConfirmNew.getText());
            oldPassword.setVisible(true);
            oldPassword.setText(showOldPassword.getText());
            confirmOldPassword.setVisible(true);
            confirmOldPassword.setText(showConfirmOld.getText());
            showNewPassword.setVisible(false);
            showConfirmNew.setVisible(false);
            showOldPassword.setVisible(false);
            showConfirmOld.setVisible(false);

        }
        else {
            newPassword.setVisible(false);
            confirmNewPassword.setVisible(false);
            oldPassword.setVisible(false);
            confirmOldPassword.setVisible(false);
            showNewPassword.setVisible(true);
            showNewPassword.setText(newPassword.getText());
            showConfirmNew.setVisible(true);
            showConfirmNew.setText(confirmNewPassword.getText());
            showOldPassword.setVisible(true);
            showOldPassword.setText(oldPassword.getText());
            showConfirmOld.setVisible(true);
            showConfirmOld.setText(confirmOldPassword.getText());
        }
    }
}

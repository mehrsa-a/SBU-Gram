package Controller;

import Model.ClientAPI;
import Model.PageLoader;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.io.IOException;

public class ForgetPasswordController {
    public JFXTextField username;
    public Label question;
    public JFXTextField answer;
    public Label password;
    public Text text;
    public Label userExist;
    public String help;

    public void back(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("Login");
    }

    public void checkUsername(ActionEvent actionEvent) {
        String temp= ClientAPI.getForgetPassword(username.getText());
        if(temp==null){
            userExist.setVisible(true);
        } else{
            userExist.setVisible(false);
            question.setText(temp.substring(0, temp.indexOf("|")));
            help=temp.substring(temp.indexOf("|")+1, temp.lastIndexOf("|"));
            password.setText(temp.substring(temp.lastIndexOf("|")+1));
            question.setVisible(true);
        }
    }

    public void getPass(ActionEvent actionEvent) {
        if(answer.getText().equals(help)){
            text.setVisible(true);
            password.setVisible(true);
        }
    }
}

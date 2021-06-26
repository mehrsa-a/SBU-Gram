package Controller;

import Model.ClientAPI;
import Model.PageLoader;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.io.IOException;

/**
 * <h1>ForgetPasswordController</h1>
 * <p>if user set a password recovery for its account; by forgot password, it can take its password back</p>
 * @author Mehrsa Arabzadeh
 * @since 6/2/2021
 * @version 1.0
 */
public class ForgetPasswordController {
    public JFXTextField username;
    public Label question;
    public JFXTextField answer;
    public Label password;
    public Text text;
    public Label userExist;
    public String help;

    /**
     * user can come back to last page that it been
     * @param actionEvent by click on a button
     * @throws IOException because of using pageLoader
     */
    public void back(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("Login");
    }

    /**
     * it checks if username exist or not
     * @param actionEvent by click on a button
     */
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

    /**
     * if user answer the question in a right way it gives its password
     * @param actionEvent by click on a button
     */
    public void getPass(ActionEvent actionEvent) {
        if(answer.getText().equals(help)){
            text.setVisible(true);
            password.setVisible(true);
        }
    }
}

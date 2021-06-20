package Controller;

import Common.ForgetPasswordQuestions;
import Model.ClientAPI;
import Model.Main;
import Model.PageLoader;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

import java.io.IOException;

public class SetForgetPasswordController {
    public Label question;
    public JFXTextField answer;

    public void initialize(){
        question.setText(ForgetPasswordQuestions.choose());
    }

    public void changeQuestion(ActionEvent actionEvent) {
        question.setText(ForgetPasswordQuestions.choose());
    }

    public void finishSignUp(ActionEvent actionEvent) throws IOException {
        ClientAPI.setForgetPassword(Main.currentUser, question.getText(), answer.getText());
        new PageLoader().load("TimeLine");
    }

    public void skip(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("TimeLine");
    }
}

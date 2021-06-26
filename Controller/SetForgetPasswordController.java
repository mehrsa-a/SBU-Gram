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

/**
 * <h1>SetForgetPasswordController</h1>
 * <p>user can answer a question for future. if it forget its password, it can recovery its password by this question</p>
 * @author Mehrsa Arabzadeh
 * @since 6/2/2021
 * @version 1.0
 */
public class SetForgetPasswordController {
    public Label question;
    public JFXTextField answer;

    /**
     * it shows a random question for user to answer
     */
    public void initialize(){
        question.setText(ForgetPasswordQuestions.choose());
    }

    /**
     * user can change its question by this method
     * @param actionEvent by click on a button
     */
    public void changeQuestion(ActionEvent actionEvent) {
        question.setText(ForgetPasswordQuestions.choose());
    }

    /**
     * this method save the answer of the user for its question
     * @param actionEvent by click on a button
     * @throws IOException because of using pageLoader
     */
    public void finishSignUp(ActionEvent actionEvent) throws IOException {
        ClientAPI.setForgetPassword(Main.currentUser, question.getText(), answer.getText());
        new PageLoader().load("TimeLine");
    }

    /**
     * this method ignore users answer
     * @param actionEvent by click on a button
     * @throws IOException because of using pageLoader
     */
    public void skip(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("TimeLine");
    }
}

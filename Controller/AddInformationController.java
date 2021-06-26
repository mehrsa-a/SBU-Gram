package Controller;

import Common.User;
import Model.*;
import Server.Server;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Popup;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static Controller.TimeLineController.*;
import static Model.Main.*;

/**
 * <h1>AddInformationController</h1>
 * <p>user can add some personal information in this page or not</p>
 * @author Mehrsa Arabzadeh
 * @since 6/2/2021
 * @version 1.0
 */
public class AddInformationController {
    public ImageView image;
    public static byte[] help;
    public static String path;
    public JFXTextField gender;
    public JFXTextField birthday;
    public JFXTextArea bio;
    public JFXTextField locationField;
    public JFXTextField email;
    public JFXTextField phoneNumber;
    public JFXTextField lastName;
    public JFXTextField firstName;

    /**
     * this method choose a photo for profile and saves it in array of bytes
     * @param actionEvent by click on a button
     * @throws IOException because of using FileInputStream
     */
    public void setProfile(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser=new FileChooser();
        File file=fileChooser.showOpenDialog(new Popup());
        if(file!=null){
            FileInputStream fileInputStream=new FileInputStream(file);
            byte[] bytes=fileInputStream.readAllBytes();
            help=bytes;
            path= file.getAbsolutePath();
            Image newImage=new Image(new ByteArrayInputStream(bytes));
            image.setImage(newImage);
        }
    }

    /**
     * this method set information that user adds
     * @param actionEvent by click on a button
     * @throws IOException because of using pageLoader
     */
    public void signup(ActionEvent actionEvent) throws IOException {
        ClientAPI.setProfile(Main.currentUser, help, path);
        Map<String, String> info=new HashMap<>();
        if(firstName.getText()!=null)
            info.put("firstName", firstName.getText());
        if(lastName.getText()!=null)
            info.put("lastName", lastName.getText());
        if(phoneNumber.getText()!=null)
            info.put("phoneNumber", phoneNumber.getText());
        if(email.getText()!=null)
            info.put("email", email.getText());
        if(locationField.getText()!=null)
            info.put("location", locationField.getText());
        if(birthday.getText()!=null)
            info.put("birthday", birthday.getText());
        if(gender.getText()!=null)
            info.put("gender", gender.getText());
        if(bio.getText()!=null)
            info.put("bio", bio.getText());
        ClientAPI.addInformation(currentUser, info);
        Main.update();
        ClientAPI.getAllPosts(currentUser);
        for(User u: users.values()){
            ClientAPI.getMyPosts(u);
        }
        ClientAPI.getAllUsers(currentUser);
        new PageLoader().load("SetForgetPassword");
    }

    /**
     * this method ignore all information that user adds
     * @param actionEvent by click on a button
     * @throws IOException because of using pageLoader
     */
    public void skip(ActionEvent actionEvent) throws IOException {
        ClientAPI.setProfile(Main.currentUser, null, null);
        Main.update();
        ClientAPI.getAllPosts(currentUser);
        for(User u: users.values()){
            ClientAPI.getMyPosts(u);
        }
        ClientAPI.getAllUsers(currentUser);
        new PageLoader().load("SetForgetPassword");
        //TimeLineController.username.setText(currentUser.getUsername());
    }

    /**
     * this method just load a page if user wants to login instead of sign up
     * @param actionEvent by click on a button
     * @throws IOException because of using pageLoader
     */
    public void login(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("Login");
    }
}

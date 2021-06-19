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

public class AddInformationController {
    public ImageView image;
    public static byte[] help;
    public JFXTextField gender;
    public JFXTextField birthday;
    public JFXTextArea bio;
    public JFXTextField locationField;
    public JFXTextField email;
    public JFXTextField phoneNumber;
    public JFXTextField lastName;
    public JFXTextField firstName;

    public void setProfile(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser=new FileChooser();
        File file=fileChooser.showOpenDialog(new Popup());
        FileInputStream fileInputStream=new FileInputStream(file);
        byte[] bytes=fileInputStream.readAllBytes();
        help=bytes;
        Image newImage=new Image(new ByteArrayInputStream(bytes));
        image.setImage(newImage);
        ClientAPI.setProfile(Main.currentUser, help);
    }

    public void signup(ActionEvent actionEvent) throws IOException {
        ClientAPI.setProfile(Main.currentUser, help);
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
        ClientAPI.getAllPosts();
        for(User u: users.values()){
            ClientAPI.getMyPosts(u);
        }
        ClientAPI.getAllUsers();
        new PageLoader().load("TimeLine");
    }

    public void skip(ActionEvent actionEvent) throws IOException {
        Main.update();
        ClientAPI.getAllPosts();
        for(User u: users.values()){
            ClientAPI.getMyPosts(u);
        }
        ClientAPI.getAllUsers();
        new PageLoader().load("TimeLine");
        //TimeLineController.username.setText(currentUser.getUsername());
    }

    public void login(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("Login");
    }
}

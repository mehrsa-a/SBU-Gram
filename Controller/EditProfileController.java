package Controller;

import Common.User;
import Model.ClientAPI;
import Model.Main;
import Model.PageLoader;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Popup;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static Model.Main.currentUser;
import static Model.Main.users;

public class EditProfileController {
    public ImageView image;
    public static Text username;
    public byte[] help;
    public JFXTextField gender;
    public JFXTextField birthday;
    public JFXTextArea bio;
    public JFXTextField locationField;
    public JFXTextField email;
    public JFXTextField phoneNumber;
    public JFXTextField lastName;
    public JFXTextField firstName;

    public void initialize(){
        Map<String, String> info=ClientAPI.getInformation(currentUser);
        if(info!=null){
            if(info.get("firstName")!=null){
                firstName.setText(info.get("firstName"));
            }
            if(info.get("lastName")!=null){
                lastName.setText(info.get("lastName"));
            }
            if(info.get("phoneNumber")!=null){
                phoneNumber.setText(info.get("phoneNumber"));
            }
            if(info.get("email")!=null){
                email.setText(info.get("email"));
            }
            if(info.get("location")!=null){
                locationField.setText(info.get("location"));
            }
            if(info.get("birthday")!=null){
                birthday.setText(info.get("birthday"));
            }
            if(info.get("gender")!=null){
                gender.setText(info.get("gender"));
            }
            if(info.get("bio")!=null){
                bio.setText(info.get("bio"));
            }
        }
    }

    public void setProfile(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser=new FileChooser();
        File file=fileChooser.showOpenDialog(new Popup());
        FileInputStream fileInputStream=new FileInputStream(file);
        byte[] bytes=fileInputStream.readAllBytes();
        help=bytes;
        Image newImage=new Image(new ByteArrayInputStream(bytes));
        image.setImage(newImage);
    }

    public void logOut(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("Login");
    }

    public void confirm(ActionEvent actionEvent) throws IOException {
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

    public void back(ActionEvent actionEvent) throws IOException {
        Main.update();
        ClientAPI.getAllPosts();
        for(User u: users.values()){
            ClientAPI.getMyPosts(u);
        }
        ClientAPI.getAllUsers();
        new PageLoader().load("TimeLine");
    }

    public void deleteAccount(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("DeleteAccount");
    }

    public void changePass(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("ChangePassword");
    }
}

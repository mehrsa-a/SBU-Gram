package Controller;

import Common.User;
import Model.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Popup;

import java.io.*;

import static Controller.TimeLineController.*;
import static Model.Main.*;

public class AddInformationController {
    public ImageView image;

    public void setProfile(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser=new FileChooser();
        File file=fileChooser.showOpenDialog(new Popup());
        FileInputStream fileInputStream=new FileInputStream(file);
        byte[] bytes=fileInputStream.readAllBytes();
        Image newImage=new Image(new ByteArrayInputStream(bytes));
        image.setImage(newImage);
    }

    public void signup(ActionEvent actionEvent) throws IOException {
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

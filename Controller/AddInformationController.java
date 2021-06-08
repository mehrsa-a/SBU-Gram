package Controller;

import Model.PageLoader;
import Model.UserItem;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Popup;

import java.io.*;

import static Controller.SignUpController.users;
import static Controller.TimeLineController.accounts;
import static Model.Main.currentUser;

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
        new PageLoader().load("TimeLine");
        TimeLineController.username.setText(currentUser.getUsername());
        accounts.setItems(FXCollections.observableArrayList(users));
        accounts.setCellFactory(accounts -> new UserItem());
    }

    public void skip(ActionEvent actionEvent) throws IOException {
        accounts.setItems(FXCollections.observableArrayList(users));
        accounts.setCellFactory(accounts -> new UserItem());
        new PageLoader().load("TimeLine");
        //TimeLineController.username.setText(currentUser.getUsername());
    }

    public void login(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("Login");
    }
}

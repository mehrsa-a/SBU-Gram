package Controller;

import Common.Massage;
import Common.User;
import Model.ClientAPI;
import Model.PageLoader;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class MassagePageController {
    public ImageView image;
    public Text username;
    public JFXListView<Massage> otherMassages;
    public JFXListView<Massage> myMassages;
    public JFXTextField massageField;
    public User target;

    public void initialize(){
        target=DirectUsersController.help;
        byte[] x= ClientAPI.getProfile(target);
        if(x!=null){
            Image newImage=new Image(new ByteArrayInputStream(x));
            image.setImage(newImage);
        }
        username.setText(target.getUsername());
    }

    public void back(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("TimeLine");
    }

    public void attachFile(ActionEvent actionEvent) {
    }

    public void send(ActionEvent actionEvent) {
    }
}

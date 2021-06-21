package Controller;

import Common.Massage;
import Common.Time;
import Common.User;
import Model.*;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MassagePageController {
    public ImageView image;
    public Text username;
    public JFXListView<Massage> otherMassages;
    public JFXListView<Massage> myMassages;
    public JFXTextField massageField;
    public User target;
    public Massage currentMassage=new Massage();
    Map<User, Map<String, List<Massage>>> map=new HashMap<>();
    Map<String, List<Massage>> allMassages=new HashMap<>();
    List<Massage> sent=new ArrayList<>();
    List<Massage> received=new ArrayList<>();

    public void initialize(){
        target=DirectUsersController.help;
        byte[] x= ClientAPI.getProfile(target);
        if(x!=null){
            Image newImage=new Image(new ByteArrayInputStream(x));
            image.setImage(newImage);
        }
        username.setText(target.getUsername());
        if(ClientAPI.getMassages(Main.currentUser)!=null)
            map=ClientAPI.getMassages(Main.currentUser);
        if(map.get(target)!=null)
            allMassages=map.get(target);
        if(allMassages.get("sent")!=null){
            sent=allMassages.get("sent");
            received=allMassages.get("received");
        }
        myMassages.setItems(FXCollections.observableArrayList(sent));
        myMassages.setCellFactory(myMassages -> new MassageItem());
        otherMassages.setItems(FXCollections.observableArrayList(received));
        otherMassages.setCellFactory(otherMassages -> new MassageItem());
    }

    public void back(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("TimeLine");
    }

    public void attachFile(ActionEvent actionEvent) {
    }

    public void send(ActionEvent actionEvent) {
        Massage currentMassage=new Massage();
        currentMassage.setSender(Main.currentUser);
        currentMassage.setReceiver(target);
        currentMassage.setText(massageField.getText());
        Massage date=new Massage();
        date.setText(Time.getTime());
        date.setSender(target);
        date.setReceiver(Main.currentUser);
        ClientAPI.sendMassage(Main.currentUser, target, currentMassage, date);
        ClientAPI.sendMassage(Main.currentUser, target, currentMassage, date);
        if(ClientAPI.getMassages(Main.currentUser)!=null)
            map=ClientAPI.getMassages(Main.currentUser);
        if(map.get(target)!=null)
            allMassages=map.get(target);
        if(allMassages.get("sent")!=null){
            sent=allMassages.get("sent");
            received=allMassages.get("received");
        }
        sent.add(currentMassage);
        received.add(date);
        myMassages.setItems(FXCollections.observableArrayList(sent));
        myMassages.setCellFactory(myMassages -> new MassageItem());
        otherMassages.setItems(FXCollections.observableArrayList(received));
        otherMassages.setCellFactory(otherMassages -> new MassageItem());
        massageField.setText("");
    }
}

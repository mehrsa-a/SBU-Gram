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
import javafx.stage.FileChooser;
import javafx.stage.Popup;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MassagePageController {
    public ImageView image;
    public Text username;
    public JFXListView<Massage> otherMassages;
    public JFXListView<Massage> myMassages;
    public JFXTextField massageField;
    public User target;
    public static byte[] help;
    public static String path;
    public Massage currentMassage=new Massage();
    List<Massage> massages=new ArrayList<>();
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
        massages=ClientAPI.getMassages(Main.currentUser);
        assert massages != null;
        sent=massages.stream()
                .filter(a-> ((a.getSender().getUsername().equals(Main.currentUser.getUsername()))&&(a.getReceiver().getUsername().equals(target.getUsername()))))
                .collect(Collectors.toList());
        received=massages.stream()
                .filter(a-> ((a.getSender().getUsername().equals(target.getUsername()))&&(a.getReceiver().getUsername().equals(Main.currentUser.getUsername()))))
                .collect(Collectors.toList());
        myMassages.setItems(FXCollections.observableArrayList(sent));
        myMassages.setCellFactory(myMassages -> new MassageItem());
        otherMassages.setItems(FXCollections.observableArrayList(received));
        otherMassages.setCellFactory(otherMassages -> new MassageItem());
    }

    public void back(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("TimeLine");
    }

    public void attachFile(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser=new FileChooser();
        File file=fileChooser.showOpenDialog(new Popup());
        FileInputStream fileInputStream=new FileInputStream(file);
        byte[] bytes=fileInputStream.readAllBytes();
        help=bytes;
        path=file.getAbsolutePath();
    }

    public void send(ActionEvent actionEvent) {
        Massage currentMassage=new Massage();
        currentMassage.setSender(Main.currentUser);
        currentMassage.setReceiver(target);
        currentMassage.setText(massageField.getText());
        if(help!=null){
            currentMassage.setFile(help);
        }
        currentMassage.setRead(false);
        Massage date=new Massage();
        date.setText(Time.getTime());
        date.setSender(target);
        date.setReceiver(Main.currentUser);
        date.setRead(true);
        ClientAPI.sendMassage(Main.currentUser, target, currentMassage, date);
        ClientAPI.receiveMassage(Main.currentUser, target, currentMassage, date);
        massages=ClientAPI.getMassages(Main.currentUser);
        assert massages != null;
        sent=massages.stream()
                .filter(a-> ((a.getSender().getUsername().equals(Main.currentUser.getUsername()))&&(a.getReceiver().getUsername().equals(target.getUsername()))))
                .collect(Collectors.toList());
        received=massages.stream()
                .filter(a-> ((a.getSender().getUsername().equals(target.getUsername()))&&(a.getReceiver().getUsername().equals(Main.currentUser.getUsername()))))
                .collect(Collectors.toList());
        myMassages.setItems(FXCollections.observableArrayList(sent));
        myMassages.setCellFactory(myMassages -> new MassageItem());
        otherMassages.setItems(FXCollections.observableArrayList(received));
        otherMassages.setCellFactory(otherMassages -> new MassageItem());
        massageField.setText("");
        image.setImage(null);
        Main.update();
    }
}

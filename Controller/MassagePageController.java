package Controller;

import Common.Massage;
import Common.Time;
import Common.User;
import Model.*;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
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

/**
 * <h1>MassagePageController</h1>
 * <p>this class shows messages between two users</p>
 * @author Mehrsa Arabzadeh
 * @since 6/2/2021
 * @version 1.0
 */
public class MassagePageController {
    public ImageView image;
    public Text username;
    //public JFXListView<Massage> otherMassages;
    public JFXListView<Massage> myMassages;
    public JFXTextArea massageField;
    public User target;
    public static byte[] help;
    public static String path;
    public Massage currentMassage=new Massage();
    List<Massage> massages=new ArrayList<>();
    List<Massage> sent=new ArrayList<>();
    List<Massage> received=new ArrayList<>();

    /**
     * this method initialize chat between two user
     */
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
                .filter(a-> ((a.getSender().getUsername().equals(Main.currentUser.getUsername()))&&(a.getReceiver().getUsername().equals(target.getUsername())))||((a.getSender().getUsername().equals(target.getUsername()))&&(a.getReceiver().getUsername().equals(Main.currentUser.getUsername()))))
                .collect(Collectors.toList());
        /*received=massages.stream()
                .filter(a-> ((a.getSender().getUsername().equals(target.getUsername()))&&(a.getReceiver().getUsername().equals(Main.currentUser.getUsername()))))
                .collect(Collectors.toList());*/
        myMassages.setItems(FXCollections.observableArrayList(sent));
        myMassages.setCellFactory(myMassages -> new MassageItem());
    }

    /**
     * user can come back to last page that it been
     * @param actionEvent by click on a button
     * @throws IOException because of using pageLoader
     */
    public void back(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("TimeLine");
    }

    /**
     * user can attach a file to its massage
     * @param actionEvent by click on a button
     * @throws IOException because of using FileInputStream
     */
    public void attachFile(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser=new FileChooser();
        File file=fileChooser.showOpenDialog(new Popup());
        if(file!=null){
            FileInputStream fileInputStream=new FileInputStream(file);
            byte[] bytes=fileInputStream.readAllBytes();
            help=bytes;
            path=file.getAbsolutePath();
        }
    }

    /**
     * this method send a massage to other user
     * @param actionEvent by click on a button
     */
    public void send(ActionEvent actionEvent) {
        Massage currentMassage=new Massage();
        currentMassage.setSender(Main.currentUser);
        currentMassage.setReceiver(target);
        if(help!=null){
            currentMassage.setFile(help);
            currentMassage.setPath(path);
        } else{
            currentMassage.setText(massageField.getText());
        }
        currentMassage.setDateFlag(false);
        currentMassage.setRead(false);
        ClientAPI.sendMassage(Main.currentUser, target, currentMassage);
        ClientAPI.receiveMassage(Main.currentUser, target, currentMassage);
        massages=ClientAPI.getMassages(Main.currentUser);
        assert massages != null;
        sent=massages.stream()
                .filter(a-> ((a.getSender().getUsername().equals(Main.currentUser.getUsername()))&&(a.getReceiver().getUsername().equals(target.getUsername())))||((a.getSender().getUsername().equals(target.getUsername()))&&(a.getReceiver().getUsername().equals(Main.currentUser.getUsername()))))
                .collect(Collectors.toList());
        /*received=massages.stream()
                .filter(a-> ((a.getSender().getUsername().equals(target.getUsername()))&&(a.getReceiver().getUsername().equals(Main.currentUser.getUsername()))))
                .collect(Collectors.toList());*/
        myMassages.setItems(FXCollections.observableArrayList(sent));
        myMassages.setCellFactory(myMassages -> new MassageItem());
        /*otherMassages.setItems(FXCollections.observableArrayList(received));
        otherMassages.setCellFactory(otherMassages -> new MassageItem());*/
        massageField.setText("");
        help=null;
        Main.update();
    }

    /**
     * it refresh the chat for new massages
     * @param actionEvent by click on a button
     * @throws IOException because of using pageLoader
     */
    public void refresh(ActionEvent actionEvent) throws IOException {
        new PageLoader().load("MassagePage");
    }
}

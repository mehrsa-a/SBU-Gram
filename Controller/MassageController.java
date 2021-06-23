package Controller;

import Common.Massage;
import Model.ClientAPI;
import Model.Main;
import Model.PageLoader;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class MassageController {
    public AnchorPane massagePane;
    public Label massage;
    public Massage target;
    public static byte[] help;
    public ImageView image;
    public JFXTextField editField;
    public ImageView ce;
    public static Massage old;

    public MassageController(Massage massage) throws IOException {
        target=massage;
        new PageLoader().load("Massage", this);
    }

    public AnchorPane init(){
        editField.setVisible(false);
        ce.setVisible(false);
        help=target.getFile();
        massage.setText(target.getText());
        if(help!=null){
            Image newImage=new Image(new ByteArrayInputStream(help));
            image.setImage(newImage);
        }
        return massagePane;
    }

    public void deleteMassage(MouseEvent mouseEvent) throws IOException {
        ClientAPI.deleteMassage(Main.currentUser, target);
        List<Massage> massages=ClientAPI.getMassages(Main.currentUser).stream()
                .filter(a-> ((a.getSender().getUsername().equals(Main.currentUser.getUsername()))&&(a.getReceiver().getUsername().equals(target.getReceiver().getUsername())))||((a.getSender().getUsername().equals(target.getReceiver().getUsername()))&&(a.getReceiver().getUsername().equals(Main.currentUser.getUsername()))))
                .collect(Collectors.toList());
        if(massages==null||massages.size()==0){
            new PageLoader().load("TimeLine");
        }
        else{
            new PageLoader().load("MassagePage");
        }
    }

    public void edit(MouseEvent mouseEvent) {
        old=target;
        editField.setVisible(true);
        ce.setVisible(true);
    }

    public void confirmEdit(MouseEvent mouseEvent) throws IOException {
        target.setText(editField.getText());
        ClientAPI.editMassage(Main.currentUser, old, target);
        new PageLoader().load("MassagePage");
    }
}

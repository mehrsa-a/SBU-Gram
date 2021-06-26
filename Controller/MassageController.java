package Controller;

import Common.Massage;
import Model.ClientAPI;
import Model.Main;
import Model.PageLoader;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <h1>MassageController</h1>
 * <p>this class shows messages in a special view</p>
 * @author Mehrsa Arabzadeh
 * @since 6/2/2021
 * @version 1.0
 */
public class MassageController {
    public AnchorPane massagePane;
    public AnchorPane imagePane;
    public Label massage;
    public Massage target;
    public static byte[] help;
    public ImageView image;
    public JFXTextArea editField;
    public ImageView ce;
    public static Massage old;
    public ImageView trash;
    public ImageView pen;
    public Label date;

    /**
     * its just a constructor
     * @param massage it initialize global massage with this
     * @throws IOException because of using pageLoader
     */
    public MassageController(Massage massage) throws IOException {
        target=massage;
        if(target.getFile()!=null){
            new PageLoader().load("ImageMassage", this);
        } else{
            new PageLoader().load("Massage", this);
        }
    }

    /**
     * this method initialize massage features
     * @return the pane that shows the massage
     */
    public AnchorPane init(){
        help=target.getFile();
        date.setText(target.getTimeString());
        if(target.isDateFlag()){
            pen.setVisible(false);
            trash.setVisible(false);
        }
        if(help!=null){
            Image newImage=new Image(new ByteArrayInputStream(help));
            image.setImage(newImage);
            if(target.getSender().getUsername().equals(Main.currentUser.getUsername())){
                imagePane.setStyle("-fx-background-color: white");
            } else{
                imagePane.setStyle("-fx-background-color: lightgray");
                trash.setVisible(false);
            }
            return imagePane;
        } else{
            massage.setText(target.getText());
            editField.setVisible(false);
            ce.setVisible(false);
            if(target.getSender().getUsername().equals(Main.currentUser.getUsername())){
                massagePane.setStyle("-fx-background-color: white");
            } else{
                massagePane.setStyle("-fx-background-color: lightgray");
                trash.setVisible(false);
                pen.setVisible(false);
            }
            return massagePane;
        }
    }

    /**
     * user can delete the massage that it sent
     * @param mouseEvent by click on a photo
     * @throws IOException because of using pageLoader
     */
    public void deleteMassage(MouseEvent mouseEvent) throws IOException {
        ClientAPI.deleteMassage(Main.currentUser, target);
        new PageLoader().load("MassagePage");
    }

    /**
     * it open a area that user types a new massage on
     * @param mouseEvent by click on a photo
     */
    public void edit(MouseEvent mouseEvent) {
        editField.setVisible(true);
        editField.setText(target.getText());
        ce.setVisible(true);
    }

    /**
     * it confirms the changes that users add on a massage
     * @param mouseEvent by click on a photo
     * @throws IOException because of using pageLoader
     */
    public void confirmEdit(MouseEvent mouseEvent) throws IOException {
        ClientAPI.editMassage(Main.currentUser, target, editField.getText());
        //old=null;
        new PageLoader().load("MassagePage");
    }
}

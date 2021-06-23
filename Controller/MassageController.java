package Controller;

import Common.Massage;
import Model.PageLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class MassageController {
    public AnchorPane massagePane;
    public Label massage;
    public Massage target;
    public static byte[] help;
    public ImageView image;

    public MassageController(Massage massage) throws IOException {
        target=massage;
        new PageLoader().load("Massage", this);
    }

    public AnchorPane init(){
        help=target.getFile();
        massage.setText(target.getText());
        if(help!=null){
            Image newImage=new Image(new ByteArrayInputStream(help));
            image.setImage(newImage);
        }
        return massagePane;
    }

    public void deleteMassage(MouseEvent mouseEvent) {
    }

    public void edit(MouseEvent mouseEvent) {
    }
}

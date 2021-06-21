package Controller;

import Common.Massage;
import Model.PageLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MassageController {
    public AnchorPane massagePane;
    public Label massage;
    public Massage target;

    public MassageController(Massage massage) throws IOException {
        target=massage;
        new PageLoader().load("Massage", this);
    }

    public AnchorPane init(){
        return massagePane;
    }
}

package Model;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * <h1>PageLoader</h1>
 * <p>this class load pages</p>
 * @author Mehrsa Arabzadeh
 * @since 6/2/2021
 * @version 1.0
 */
public class PageLoader {
    private static Stage stage;
    private static Scene scene;
    public static final int WIDTH = 600;
    public static final int HEIGHT = 500;

    /**
     * this method initialize basic stage
     * @param primaryStage it use for initialize stage
     */
    public static void initStage(Stage primaryStage) {

        stage = primaryStage;
        stage.setTitle("SBU GRAM");
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
        stage.initStyle(StageStyle.DECORATED); //the three buttons on top of stage will be shown
        stage.setResizable(false); //the resizable button is enabled. if your design is responsive this must be true
        stage.getIcons().add(new Image(Paths.get("C:/Users/98917/IdeaProjects/project/src/images/icon.png").toUri().toString()));
    }

    /**
     * @param fxml it use for set scene's root
     * @throws IOException because of using loadFXML
     */
    public void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * @return it returns the stage
     */
    public static Stage getPrimaryStage() {
        return stage;
    }

    /**
     * @param fxml it uses for initialize fxmlLoader
     * @return it returns a parent that its load
     * @throws IOException because of using fxmlLoader
     */
    public Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/View/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * this method load pages that have controller
     * @param url it uses for initialize the page that it wanna load
     * @throws IOException because of using pageLoader
     */
    public void load(String url) throws IOException {
        scene = new Scene(new PageLoader().loadFXML(url));
        stage.setScene(scene);
        stage.show();
    }

    /**
     * this method load pages and set a controller for them in runtime
     * @param fxml it uses for initialize the page that it wanna load
     * @param controller it uses for initialize the page's controller that it wanna load
     * @throws IOException because of using pageLoader
     */
    public void load(String fxml, Object controller) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/View/" + fxml + ".fxml"));
        fxmlLoader.setController(controller);
        fxmlLoader.load();
    }
}


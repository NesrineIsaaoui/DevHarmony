package pi.blog;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class App {

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/pi/blog/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }
}


package main;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class Loader {
    public static <T> T loadFxmlFile(String filename) {
        FXMLLoader loader = new FXMLLoader(Loader.class.getResource(filename));

        T root = null;

        try {
            root = loader.load();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }
}

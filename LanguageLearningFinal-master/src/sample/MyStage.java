package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MyStage {
    private Stage window;
    private int width = 700;
    private int length = 500;

    public void switchScenes(Stage stage, String targetFile, String title) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(targetFile));
        stage.setScene(new Scene(root, width,length));
        stage.setTitle(title);
        stage.setResizable(false);
        stage.show();
    }
}

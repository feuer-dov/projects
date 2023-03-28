package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {
    private MyStage myStage = new MyStage();
    private Language language = new Language();
    @FXML
    private ChoiceBox<String> choiceBox;

    public void onClick(ActionEvent event) throws IOException {
        //gets language from choice box
        PrintWriter printWriter = new PrintWriter("words.csv");
        printWriter.close();

        String languageString = choiceBox.getValue();
        language.setLanguage(languageString);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("createWordList.fxml"));
        Parent wordListParent = loader.load();

        //gets controller from next window
        createWordListPageController wordListPageController = loader.getController();
        //adds language date to the next window's controller
        wordListPageController.setLanguage(language);

        stage.setScene(new Scene(wordListParent, 700,500));
        stage.setTitle("Word List");
        stage.setResizable(true);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceBox.setValue("French");
        ObservableList<String> languages = FXCollections.observableArrayList("French","Hebrew");
        choiceBox.setItems(languages);
    }
}

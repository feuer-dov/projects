package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class createWordListPageController{
    private Language language;
    private Translator translator = new Translator();
    private FileProcesser fileProcesser;
    private FullTranslation fullTranslation;
    private boolean isToEnglish;

    @FXML Button translateToLanguageButton;
    @FXML TextField initialField;
    @FXML TextField translationField;
    @FXML TextField IPAField;
    @FXML Hyperlink myHyperLink;
    @FXML AnchorPane anchorPane1;
    @FXML AnchorPane anchorPane2;
    @FXML HBox hBox;

    public void setLanguage(Language _language){
        language = _language;
        translateToLanguageButton.setText("Translate to "+language.getLanguage());
    }

    public void translateToLanguage() throws IOException {
        isToEnglish = false;
        String initial = initialField.getText();
        String translated = translator.translate("en",language.getLanguageCode(),initial).replace("&#39;", "'");

        translationField.setText(translated);
        if(language.getLanguage().equals("French")){
            IPAField.setText(translator.IPA(translated));
        }
    }

    public void translateToEnglish() throws IOException {
        isToEnglish = true;
        String initial = initialField.getText();
        String translated = translator.translate(language.getLanguageCode(),"en",initial).replace("&#39;", "'");;

        translationField.setText(translated);
        if(language.getLanguage().equals("French")){
            IPAField.setText(translator.IPA(initial));
        }
    }

    public void toIPA(){
        //sets text in translation box to IPA
        String word = translationField.getText();
        IPAField.setText(translator.IPA(word));
    }

    public void sendToIPAWebsite(){
        myHyperLink.setOnAction(e -> {
            if(Desktop.isDesktopSupported())
            {
                try {
                    Desktop.getDesktop().browse(new URI("http://web.mit.edu/6.mitx/www/24.900%20IPA/IPAapp.html"));
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
    public void addWord() throws IOException {
        organizeTranslation();
        System.out.println(fullTranslation.toString());
        fileProcesser = new FileProcesser();
        fileProcesser.addToCSV(fullTranslation.toString());
        this.clear();
    }

    private void organizeTranslation() {
        String initial = initialField.getText();
        String translated = translationField.getText();
        String IPA = IPAField.getText();

        System.out.println(initial+translated+IPA);

        if(isToEnglish){
            fullTranslation = new FullTranslation(translated, initial, IPA);
        } else {
            fullTranslation = new FullTranslation(initial,translated,IPA);
        }
    }

    private void replaceApostrophe(String word){
        word.replace("&#39;", "'");
    }

    public void clear(){
        initialField.setText("");
        translationField.setText("");
        IPAField.setText("");
    }

    public void goBack(ActionEvent event) throws IOException{
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("view/homePage.fxml"));
        stage.setScene(new Scene(root,700,500));
        stage.setResizable(false);
        stage.show();
    }

    public void endSession(ActionEvent event) throws IOException {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
        Desktop.getDesktop().open(new File("/Applications/Anki.app"));
    }
}


package converter;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import converter.downloader.Downloader;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AppController {
    //Textfield to get the amount to be converted
    @FXML
    private TextField inputText;

    //textfield to put the conversion amount
    @FXML
    private TextField outputText;

    //Dropdowns with the currencies to choose from
    @FXML
    private ComboBox<Currency> sourceComboBox;

    @FXML
    private ComboBox<Currency> targetComboBox;

    //Default Constructor
    public AppController() {
    }
    
    @FXML
    private void initialize() {
        //Initialize the ChoiceBoxes with the currencies.
        sourceComboBox.getItems().setAll(Currency.values());
        targetComboBox.getItems().setAll(Currency.values());
    }
    
    @FXML
    private void update()
    {
    }
    
    public static JsonObject fetchRates(String currencyCode) throws FileNotFoundException {
        // retrive entire file and place into a single string
        Scanner input = new Scanner(new File("./currencydata/" + currencyCode + ".json")).useDelimiter("\\Z");
        String jsonString = input.next();
        
        // parse json string
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(jsonString).getAsJsonObject();
        return json;
    }
}

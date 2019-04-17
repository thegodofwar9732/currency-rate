package converter;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import converter.downloader.Downloader;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

    @FXML
    private TextField LatestUpdate;

    //Default Constructor
    public AppController() {
    }

    @FXML
    private void initialize() throws IOException {
        //Initialize the ChoiceBoxes with the currencies.
        sourceComboBox.getItems().setAll(Currency.values());
        targetComboBox.getItems().setAll(Currency.values());
        
        //get time of last download
        InputStream is = new FileInputStream("DownloadDate.txt");
        BufferedReader buf = new BufferedReader(new InputStreamReader(is)); 
        String date = buf.readLine();
        LatestUpdate.setText(date);
    }
    
    @FXML
    private void convert() throws FileNotFoundException {
        String source = sourceComboBox.getValue().getCode();
        String target = targetComboBox.getValue().getCode();
        double rate = getRate(source, target) * Double.parseDouble(inputText.getText());
        outputText.setText(Double.toString(rate));
    }
    
    @FXML
    private void instantUpdate(KeyEvent event) throws IOException {
    	//Update the ouputText when a number is entered or when inputText is modified   	
    	if(event.getCode() == KeyCode.DIGIT0 ||event.getCode() == KeyCode.DIGIT1 ||event.getCode() == KeyCode.DIGIT2 ||event.getCode() == KeyCode.DIGIT3 ||event.getCode() == KeyCode.DIGIT4 || event.getCode() == KeyCode.DIGIT5 || event.getCode() == KeyCode.DIGIT6 || event.getCode() == KeyCode.DIGIT7 || event.getCode() == KeyCode.DIGIT8 || event.getCode() == KeyCode.DIGIT9 ) {    
    		convert();
    	}
    	else if(event.getCode() == KeyCode.BACK_SPACE && !inputText.getText().isEmpty()) {

    		if(  !(Double.parseDouble(inputText.getText()) <= 0) && inputText.getText() != null && inputText.getText().length() != 0 ){
    			convert();   			
    		}else if(event.getCode() == KeyCode.BACK_SPACE && inputText.getText().length() == 0) {
    			outputText.setText("0");
    		}    		
    	}
    	else if(event.getCode() == KeyCode.UNDEFINED) {
    		outputText.setText("0");
    	}else {
    		outputText.setText("0");
    	}
    }
    
    private JsonObject fetchRates(String sourceCurrency) throws FileNotFoundException {
        // retrieve entire file and place into a single string
        Scanner input = new Scanner(new File(Downloader.SAVE_DIRECTORY + sourceCurrency + ".json")).useDelimiter("\\Z");
        String jsonString = input.next();

        // parse json string
        JsonParser parser = new JsonParser();
        JsonObject rates = parser.parse(jsonString).getAsJsonObject();
        return rates;
    }

    private double getRate(String sourceCurrency, String targetCurrency) throws FileNotFoundException {
        JsonObject rates = fetchRates(sourceCurrency);
        double rate = rates.getAsJsonObject(targetCurrency).get("rate").getAsDouble();
        return rate;
    }
}

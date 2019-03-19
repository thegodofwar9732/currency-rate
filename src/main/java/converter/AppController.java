package converter;

import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
	private ComboBox<Currency> box1;
	
	@FXML
	private ComboBox<Currency> box2;
	
	//Default Constructor
	public AppController() 
    {
    }
    
    @FXML
    private void initialize() 
    {
    	//Initialize the ChoiceBoxes with the currencies. 
    		box1.getItems().setAll(Currency.values());
    		box2.getItems().setAll(Currency.values());
    }
    
    @FXML
    private void update()
    {
    }
    
    private JsonObject fetchRates(String sourceCurrency) throws FileNotFoundException {
        // retrive entire file and place into a single string
        Scanner input = new Scanner(new File("./currencydata/" + sourceCurrency + ".json")).useDelimiter("\\Z");
        String jsonString = input.next();
        
        // parse json string
        JsonParser parser = new JsonParser();
        JsonObject rates = parser.parse(jsonString).getAsJsonObject();
        return rates;
    }
    public Double getRate(String sourceCurrency, String targetCurrency) throws FileNotFoundException{
        JsonObject rates = fetchRates(sourceCurrency);
        Double rate = rates.getAsJsonObject(targetCurrency).get("rate").getAsDouble();
        return rate;
    }
}

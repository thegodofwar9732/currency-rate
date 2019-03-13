package converter;

import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

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
}

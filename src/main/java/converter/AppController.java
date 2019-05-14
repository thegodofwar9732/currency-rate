package converter;

import com.google.gson.JsonObject;
import converter.database.Database;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

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

    //Textbox to display the date and time of the last download
    @FXML
    private Text latestUpdateDate;

    @FXML
    private ImageView statusImageView;

    private Database database;

    //Default Constructor
    public AppController() {
        database = new Database();
    }

    @FXML
    private void initialize() {
        //Initialize the ChoiceBoxes with the currencies.
        sourceComboBox.getItems().setAll(Currency.values());
        targetComboBox.getItems().setAll(Currency.values());
        //Input text make disable when initialize
        inputText.setEditable(false);
        outputText.setEditable(false);
        statusImageView.setVisible(false);
        
    }
    
    @FXML
    private void edit(Event event){
    	inputText.setEditable(true);
    }
    
    @FXML
    private void instantUpdate(KeyEvent event) {
        //Update the ouputText when a number is entered or when inputText is modified
    	if(inputText.getText().isEmpty()) {
    		outputText.setText(":(");
    	}
    	else if (event.getCode() == KeyCode.DIGIT0 || event.getCode() == KeyCode.DIGIT1 || event.getCode() == KeyCode.DIGIT2 || event.getCode() == KeyCode.DIGIT3 || event.getCode() == KeyCode.DIGIT4 || event.getCode() == KeyCode.DIGIT5 || event.getCode() == KeyCode.DIGIT6 || event.getCode() == KeyCode.DIGIT7 || event.getCode() == KeyCode.DIGIT8 || event.getCode() == KeyCode.DIGIT9) {
            convert();
            
        }else if(event.getCode() == KeyCode.BACK_SPACE && !inputText.getText().isEmpty()) {

            if (!(Double.parseDouble(inputText.getText()) <= 0) && inputText.getText() != null && inputText.getText().length() != 0) {
                convert();
            } else if (event.getCode() == KeyCode.BACK_SPACE && inputText.getText().length() == 0) {
                outputText.setText("0");
            }
            
        } else if (event.getCode() == KeyCode.UNDEFINED) {
            outputText.setText("0");
            
        } else {
        	inputFilter();
        	clearInputText();
        }
    }
    
    private void inputFilter() {
    	if(Double.isNaN(Double.parseDouble(inputText.getText()))) {
    		//alert user
    		outputText.setText("0");
    		inputText.setText("0");
    	}
    }
    private void clearInputText() {
    	outputText.setText("0");
		inputText.setText("0");
    }
    public void setDate(String date) {
        latestUpdateDate.setText(date);
    }

    private void showStatus(String previousUpload, double rate, double previousRate) {
        statusImageView.setVisible(true);
        Tooltip tooltip = new Tooltip();
        double difference = rate - previousRate;

        if (difference > 0) {
            tooltip.setText(String.format("Rate up by %f since last update %s", difference, previousUpload));
            statusImageView.setImage(Status.UP.getImage());
        }
        if (difference < 0) {
            tooltip.setText(String.format("Rate down by %f since last update %s", difference, previousUpload));
            statusImageView.setImage(Status.DOWN.getImage());
        }
        if (difference == 0) {
            tooltip.setText(String.format("Rate unchanged since last update %s", previousUpload));
            statusImageView.setImage(Status.UNCHANGED.getImage());
        }

        Tooltip.install(statusImageView, tooltip);
    }

    private void convert() {
    	//put inputFilter methods here ,,
    	inputFilter();
        String source = sourceComboBox.getValue().getCode();
        String target = targetComboBox.getValue().getCode();
        //if user silly the same source and target
        if(source.equals(target)) {
        	outputText.setText(inputText.getText());
        	return;
        }
        System.out.println("Source:"+ target);
        double currentRate = getRate(source, target);        
        double result = currentRate * Double.parseDouble(inputText.getText());
        outputText.setText(Double.toString(result));
        double previousRate = getRate(source, target, 1);
        String previousUpload = database.getUploadDate(1);
        showStatus(previousUpload, currentRate, previousRate);
    }

    protected double getRate(String sourceCurrency, String targetCurrency) {
        return getRate(sourceCurrency, targetCurrency, 0);
    }

    protected double getRate(String sourceCurrency, String targetCurrency, int num) {
        JsonObject rates = database.getCurrencyData(sourceCurrency, num);
        double rate = rates.getAsJsonObject(targetCurrency).get("rate").getAsDouble();
        return rate;
    }
}

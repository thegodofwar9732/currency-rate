package converter;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

    private ApiClient apiClient;

    private String input;
    private double rate;
    private String sourceCurrency;
    private String targetCurrency;
    private String yesterday;

    //Default Constructor
    public AppController() {
        apiClient = new ApiClient();
        yesterday = LocalDate.now().minusDays(1).format(DateTimeFormatter.ISO_LOCAL_DATE);
        input = "1";
        rate = 1;
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
        inputText.setText(input);

        sourceComboBox.valueProperty().addListener((option, oldValue, newValue) -> {
            sourceCurrency = newValue.getCode();
            if (targetCurrency != null) {
                try {
                    showStatus();
                    convert();
                } catch (ParserConfigurationException | SAXException | XPathExpressionException | IOException e) {
                    e.printStackTrace();
                }
            }
        });

        targetComboBox.valueProperty().addListener((option, oldvalue, newvalue) -> {
            targetCurrency = newvalue.getCode();
            if (sourceCurrency != null) {
                try {
                    showStatus();
                    convert();
                } catch (ParserConfigurationException | SAXException | XPathExpressionException | IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @FXML
    private void inputEditEnabler(Event event) {
        if (isInvalidInput()) {
            return;
        }
        inputText.setEditable(true);
        convert();
    }

    //outputEditConverter
    @FXML
    private void outputEditConverter(Event event) {
        if (isInvalidInput()) {
            return;
        }
        convert();
    }

    @FXML
    private void instantUpdate(KeyEvent event) {
        //Update the ouputText when a number is entered or when inputText is modified
        if (isInvalidInput()) {
            return;
        }

        if (event.getCode() == KeyCode.DIGIT0 || event.getCode() == KeyCode.DIGIT1 || event.getCode() == KeyCode.DIGIT2 || event.getCode() == KeyCode.DIGIT3 || event.getCode() == KeyCode.DIGIT4 || event.getCode() == KeyCode.DIGIT5 || event.getCode() == KeyCode.DIGIT6 || event.getCode() == KeyCode.DIGIT7 || event.getCode() == KeyCode.DIGIT8 || event.getCode() == KeyCode.DIGIT9) {
            convert();

        } else if (event.getCode() == KeyCode.BACK_SPACE && !inputText.getText().isEmpty()) {

            if (!(Double.parseDouble(inputText.getText()) <= 0) && inputText.getText() != null && inputText.getText().length() != 0) {
                convert();
            } else if (event.getCode() == KeyCode.BACK_SPACE && inputText.getText().length() == 0) {
                outputText.setText("0");
            }

        } else if (event.getCode() == KeyCode.UNDEFINED) {
            return;
        }
    }

    private boolean isInvalidInput() {

        if (inputText.getText().isEmpty()) {
            outputText.setText("");
            return true;
        }
        boolean numeric = true;
        try {
            Double num = Double.parseDouble(inputText.getText());
        } catch (NumberFormatException e) {
            numeric = false;
        }
        if (!numeric) {
            outputText.setText("Characters are Not Allowed.");
            return true;
        }

        return false;

    }

    public void setDate(String date) {
        latestUpdateDate.setText(date);
    }

    private void showStatus() throws ParserConfigurationException, SAXException, XPathExpressionException, IOException {
        double previousRate = apiClient.getYesterdayRate(sourceCurrency, targetCurrency);
        rate = apiClient.getCurrentRate(sourceCurrency, targetCurrency);

        statusImageView.setVisible(true);
        Tooltip tooltip = new Tooltip();
        double difference = rate - previousRate;

        if (difference > 0) {
            tooltip.setText(String.format("Rate up by %f since last update %s", difference, yesterday));
            statusImageView.setImage(Status.UP.getImage());
        }
        if (difference < 0) {
            tooltip.setText(String.format("Rate down by %f since last update %s", difference, yesterday));
            statusImageView.setImage(Status.DOWN.getImage());
        }
        if (difference == 0) {
            tooltip.setText(String.format("Rate unchanged since last update %s", yesterday));
            statusImageView.setImage(Status.UNCHANGED.getImage());
        }

        Tooltip.install(statusImageView, tooltip);
    }

    private void convert() {

        if (isInvalidInput()) {
            return;
        }

        //if user pick the same source and target
        if (sourceCurrency.equals(targetCurrency)) {
            outputText.setText(inputText.getText());
            return;
        }
        double result = rate * Double.parseDouble(inputText.getText());

        outputText.setText(Double.toString(result));
    }
}

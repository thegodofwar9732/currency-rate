package converter;

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

    //Default Constructor
    public AppController() {
        apiClient = new ApiClient();
    }

    @FXML
    private void initialize() {
        //Initialize the ChoiceBoxes with the currencies.
        sourceComboBox.getItems().setAll(Currency.values());
        targetComboBox.getItems().setAll(Currency.values());

        statusImageView.setVisible(false);
    }

    @FXML

    private void instantUpdate(KeyEvent event) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
        //Update the outputText when a number is entered or when inputText is modified
        if (event.getCode() == KeyCode.DIGIT0 || event.getCode() == KeyCode.DIGIT1 || event.getCode() == KeyCode.DIGIT2 || event.getCode() == KeyCode.DIGIT3 || event.getCode() == KeyCode.DIGIT4 || event.getCode() == KeyCode.DIGIT5 || event.getCode() == KeyCode.DIGIT6 || event.getCode() == KeyCode.DIGIT7 || event.getCode() == KeyCode.DIGIT8 || event.getCode() == KeyCode.DIGIT9) {
            convert();
        } else if (event.getCode() == KeyCode.BACK_SPACE && !inputText.getText().isEmpty()) {

            if (!(Double.parseDouble(inputText.getText()) <= 0) && inputText.getText() != null && inputText.getText().length() != 0) {
                convert();
            } else if (event.getCode() == KeyCode.BACK_SPACE && inputText.getText().length() == 0) {
                outputText.setText("0");
            }
        } else if (event.getCode() == KeyCode.UNDEFINED) {
            outputText.setText("0");
        } else {
            outputText.setText("0");
        }
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

    private void convert() throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {
        String source = sourceComboBox.getValue().getCode();
        String target = targetComboBox.getValue().getCode();
        double currentRate = apiClient.getCurrentRate(source, target);
        double result = currentRate * Double.parseDouble(inputText.getText());

        outputText.setText(Double.toString(result));

        double previousRate = apiClient.getYesterdayRate(source, target);
        String yesterday = LocalDate.now().minusDays(1).format(DateTimeFormatter.ISO_LOCAL_DATE);
        showStatus(yesterday, currentRate, previousRate);
    }
}

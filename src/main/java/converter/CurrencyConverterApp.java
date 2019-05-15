package converter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CurrencyConverterApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        String dateNow = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/appview.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Currency Converter");

        AppController appController = fxmlLoader.getController();
        appController.setDate(dateNow);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
        mongoLogger.setLevel(Level.SEVERE);
        launch(args);
    }

    /**
     * protected so we can run tests on it
     * Checks to see if the file has already been downloaded today
     * @param localDate - todays date as a string
     * @return - true if the download date matches today's date, false if not
     */
    protected boolean downloadedToday (String localDate) {
    	Database database = new Database();
        String downloadDate = database.getUploadDate();
        return localDate.equals(downloadDate);
    }
}

package converter;

import converter.database.Database;
import converter.downloader.Downloader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CurrencyConverterApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {        
    	//If the file has already been downloaded today, it won't be downloaded again
    	String dateNow = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        if (!downloadedToday(dateNow)) {
        	Downloader downloader = new Downloader();
        	downloader.downloadFile(dateNow);
        }
     
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/appview.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Currency Converter");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
        mongoLogger.setLevel(Level.SEVERE);
        launch(args);
    }
    
    /**
     * Checks to see if the file has already been downloaded today
     * @param localDate - todays date as a string
     * @return - true if the download date matches today's date, false if not
     */
    private boolean downloadedToday (String localDate) {
    	Database database = new Database();
        String downloadDate = database.getUploadDate();
        return localDate.equals(downloadDate);
    }
    
}

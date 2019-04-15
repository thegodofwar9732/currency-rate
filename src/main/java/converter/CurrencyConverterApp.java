package converter;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import converter.downloader.ScheduledDownloadTask;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CurrencyConverterApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        //Download at midnight
        Timer timer = new Timer();
        TimerTask task = new ScheduledDownloadTask();
        
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0); // midnight
        today.set(Calendar.MINUTE, 0); // 0 minutes
        today.set(Calendar.SECOND, 0); // 0 seconds
        
        timer.schedule(task, today.getTime(), TimeUnit.HOURS.toMillis(24));
    	
    	Parent root = FXMLLoader.load(getClass().getResource("/fxml/appview.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Currency Converter");
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }

    public static void main(String[] args) {
        launch(args);
    }
}

package converter;

import converter.downloader.Downloader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CurrencyConverterApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/appview.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Currency Converter");
        primaryStage.setScene(scene);
        primaryStage.show();
        Downloader downloader = new Downloader();
        downloader.downloadFile();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

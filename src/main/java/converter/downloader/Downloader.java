package converter.downloader;

import converter.Currency;
import converter.database.Database;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Downloader {
    public static final String SAVE_DIRECTORY = "./currencydata/";

    private final String BASE_URL = "http://www.floatrates.com/daily/";

    private ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     * asynchronous download
     *
     * @param localDate date of download
     */
    public void downloadFile(String localDate) {
        executorService.submit(() -> {
            try {
                run(localDate);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                executorService.shutdown();
            }
        });
    }

    /**
     * download json file from server to local
     *
     * @param currency the name of currency file
     * @param source   the url where the currency file is located
     * @throws IOException if cant write to file
     */
    private void download(Currency currency, URL source) throws IOException {
        String fileName = currency.getCode() + ".json";
        File saveFile = new File(SAVE_DIRECTORY + fileName);
        FileUtils.copyURLToFile(source, saveFile);
    }

    /**
     * parallel download which skips non 200 http response code, then format the downloaded files and add to database
     *
     * @param localDate date of download
     * @throws IOException if write permission is denied
     */
    private void run(String localDate) throws IOException {
        for (Currency currency : Currency.values()) {
            URL source = new URL(BASE_URL + currency.getCode() + ".json");
            if (((HttpURLConnection) source.openConnection()).getResponseCode() == HttpURLConnection.HTTP_OK) {
                executorService.submit(() -> {
                    try {
                        download(currency, source);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
        executorService.shutdown();

        Database db = new Database();
        db.addToCollection(localDate);
    }
}
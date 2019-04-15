package converter.downloader;

import converter.Currency;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Downloader {
    public static final String SAVE_DIRECTORY = "./currencydata";

    private final String BASE_URL = "http://www.floatrates.com/daily/";

    public void downloadFile() throws IOException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (Currency currency : Currency.values()) {
            URL source = new URL(BASE_URL + currency.getCode() + ".json");
            if (((HttpURLConnection) source.openConnection()).getResponseCode() == 200) {
                executorService.submit(() -> {
                    try {
                        FileUtils.copyURLToFile(source, new File(String.format("%s/%s.json", SAVE_DIRECTORY, currency.getCode())));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
        executorService.shutdown();
        // get time of download
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm");
		LocalDateTime time = LocalDateTime.now();
		
	    File file = new File("DownloadDate.txt");
		FileWriter fileWriter = new FileWriter(file);
		fileWriter.write(dtf.format(time));
	    fileWriter.close();
    }
}
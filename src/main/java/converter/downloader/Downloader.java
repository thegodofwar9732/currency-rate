package converter.downloader;

import converter.Currency;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Downloader {
    static final String SAVE_DIRECTORY = "./currencydata";

    private final String BASE_URL = "http://www.floatrates.com/daily/";

    public void downloadFile() throws IOException {
        for (Currency currency : Currency.values()) {
            URL source = new URL(BASE_URL + currency.getCode() + ".json");
            if (((HttpURLConnection) source.openConnection()).getResponseCode() == 200) {
                FileUtils.copyURLToFile(source, new File(String.format("%s/%s.json", SAVE_DIRECTORY, currency.getCode())));
            }
        }
    }
}

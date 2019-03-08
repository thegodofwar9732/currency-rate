package converter.downloader;

import converter.Currency;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Downloader {
    private final String BASE_URL = "http://www.floatrates.com/daily/";
    private final int CONNECTION_TIMEOUT = 1000;
    private final int READ_TIMEOUT = 300;

    public void downloadFile() throws IOException {
        for (Currency currency : Currency.values()) {
            URL source = new URL(BASE_URL + currency.getCode() + ".json");
            if (((HttpURLConnection) source.openConnection()).getResponseCode() == 200) {
                FileUtils.copyURLToFile(source, new File(String.format("E:\\Desktop\\test\\%s.json", currency.getCode())), CONNECTION_TIMEOUT, READ_TIMEOUT);
            }
        }
    }
}

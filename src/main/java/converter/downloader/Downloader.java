package converter.downloader;

import converter.Currency;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Downloader {
    public static final String SAVE_DIRECTORY = "./currencydata/";

    private final String BASE_URL = "http://www.floatrates.com/daily/";

    private ExecutorService executorService = Executors.newCachedThreadPool();

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

    private void download(String localDate, Currency currency, URL source) throws IOException {
        String fileName = currency.getCode() + ".json";
        File saveFile = new File(SAVE_DIRECTORY + localDate + "/" + fileName);

        if (!saveFile.getParentFile().exists()) {
            saveFile.getParentFile().mkdir();
        }

        if (!saveFile.exists()) {
            saveFile.createNewFile();
        }

        ReadableByteChannel readableByteChannel = Channels.newChannel(source.openStream());
        FileOutputStream fileOutputStream = new FileOutputStream(saveFile);
        FileChannel fileChannel = fileOutputStream.getChannel();
        fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        readableByteChannel.close();
        fileOutputStream.close();
    }

    private void run(String localDate) throws IOException {
        for (Currency currency : Currency.values()) {
            URL source = new URL(BASE_URL + currency.getCode() + ".json");
            if (((HttpURLConnection) source.openConnection()).getResponseCode() == HttpURLConnection.HTTP_OK) {
                executorService.submit(() -> {
                    try {
                        download(localDate, currency, source);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
        executorService.shutdown();
    }
}
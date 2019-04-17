package converter.downloader;

import converter.Currency;
import converter.Database;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.GregorianCalendar;

public class Downloader {
    public static final String SAVE_DIRECTORY = "./currencydata/";

    private final String BASE_URL = "http://www.floatrates.com/daily/";

    private ExecutorService executorService = Executors.newCachedThreadPool();

    public void downloadFile() {
        executorService.submit(() -> {
            try {
                run();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                executorService.shutdown();
            }
        });
    }

    private void download(Currency currency, URL source) throws IOException {
        String fileName = currency.getCode() + ".json";
        File saveFile = new File(SAVE_DIRECTORY + fileName);

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

    private void run() throws IOException {
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
        Database db = new Database();

        Calendar c = new GregorianCalendar();
        String day = Integer.toString(c.get(Calendar.DAY_OF_MONTH));
        String month = Integer.toString(c.get(Calendar.MONTH) + 1);
        String year = Integer.toString(c.get(Calendar.YEAR));
        String today = month + "/" + day + "/" + year;
        executorService.submit(() -> {
            try {
                db.addToCollection(today);
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        });
    }
}
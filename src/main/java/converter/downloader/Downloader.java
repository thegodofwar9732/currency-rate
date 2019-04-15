package converter.downloader;

import converter.Currency;

import java.io.File;

import java.io.FileWriter;
import java.net.HttpURLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
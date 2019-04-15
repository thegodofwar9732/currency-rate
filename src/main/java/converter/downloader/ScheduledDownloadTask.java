package converter.downloader;

import java.time.LocalDate;
import java.util.TimerTask;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

public class ScheduledDownloadTask extends TimerTask {
    //Default Constructor
    public ScheduledDownloadTask() {

    }

    //The action to be performed by the timer task
    public void run() {
        //get time of last download
        String dateNow = LocalDate.now().format(ISO_LOCAL_DATE);
        Downloader downloader = new Downloader();
        downloader.downloadFile(dateNow);
    }
}

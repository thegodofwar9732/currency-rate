package converter.downloader;

import java.util.TimerTask;

public class ScheduledDownloadTask extends TimerTask {
	//Default Constructor
	public ScheduledDownloadTask() {
		
	}
	
	//The action to be performed by the timer task
	public void run() {
		try {
			Downloader downloader = new Downloader();
		    downloader.downloadFile();
		} catch (Exception ex) {
			System.out.println ("error scheduled download " + ex.getMessage());
		}	
	}
}

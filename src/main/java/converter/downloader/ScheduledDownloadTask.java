package converter.downloader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimerTask;

public class ScheduledDownloadTask extends TimerTask {
	//Default Constructor
	public ScheduledDownloadTask() {
		
	}
	//The action to be performed by the timer task
	public void run() {
	    try {
			//get time of last download
		    InputStream is = new FileInputStream("DownloadDate.txt"); 
		    BufferedReader buf = new BufferedReader(new InputStreamReader(is)); 
		    String date = buf.readLine();

		    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm");	
		    LocalDate localdate = LocalDate.parse(date,formatter);
		    
		    //if dates are the same, it should not be downloaded.
		    LocalDate dateNow = LocalDate.now();
		    System.out.println("date now: " + dateNow);
		    if (!(dateNow.equals(localdate))) {
				Downloader downloader = new Downloader();
				downloader.downloadFile();
		    }
		} catch (IOException e) {
			System.out.println ("error with scheduled download " + e.getMessage());
		}
	}
}

package com.pramati.crawler.mailDownloaderServices;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.pramati.crawler.mailDownloader.MailDownloadController;
import com.pramati.crawler.uti.Utility;

/**
 * Provides the method to download the mails
 * 
 */
public class MailDownloadControllerImp implements MailDownloadController {

	/**
	 * 
	 *
	 * @param mailLinkOfYear
	 *            set of URL giving the locations of page.
	 * 
	 *            save the data in local file System
	 * 
	 */
	public void downloadMails(Set<String> mailLinkOfYear) throws IOException,
			InterruptedException {
		Utility.dropExistingFiles(Utility.FILE_LOC);
		downloadMain(mailLinkOfYear);
	}

	/**
	 * 
	 *
	 * @param mailLinkOfYear
	 *            set of URL giving the locations of page.
	 * 
	 *            Helper method to create thread and execute the task of reading
	 *            and saving data.
	 * 
	 */
	private void downloadMain(Set<String> mailLinkOfYear) throws IOException,
			InterruptedException {
		long startTime = System.currentTimeMillis();

		Iterator<String> it = mailLinkOfYear.iterator();

		ExecutorService executor = Executors.newFixedThreadPool(6);
		String url = null;
		while (it.hasNext()) {
			url = it.next();
			executor.execute((new MailDownloadService(url)));
		}
		executor.shutdown();

		executor.awaitTermination(6, TimeUnit.MINUTES);

		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println(totalTime);
	}
}

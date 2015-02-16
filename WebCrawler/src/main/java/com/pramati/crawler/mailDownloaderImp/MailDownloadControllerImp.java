package com.pramati.crawler.mailDownloaderImp;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

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

	final static Logger logger = Logger
			.getLogger(MailDownloadControllerImp.class);

	public void downloadMails(Set<String> mailLinkOfYear) throws IOException,
			InterruptedException {
		if (logger.isDebugEnabled()) {
			logger.debug("Dropping  existing mails...");
		}

		if (logger.isInfoEnabled()) {
			logger.info("Dropping  existing mails...");
		}
		// We may download only remaining files--req not clear
		// Utility.dropExistingFiles(Utility.FILE_LOC);
		if (!Utility.isExist(Utility.FILE_LOC)) {
			Utility.createFolder(Utility.FILE_LOC);
		}
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
			if (logger.isDebugEnabled()) {
				logger.debug("Saving files for URL" + url);
			}

			if (logger.isInfoEnabled()) {
				logger.info("Saving files for URL" + url);
			}
			executor.execute(Utility.newMailDownloader(url));
		}
		executor.shutdown();

		executor.awaitTermination(6, TimeUnit.MINUTES);

		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		if (logger.isDebugEnabled()) {
			logger.debug("Time taken in downloading is  :" + totalTime);
		}

		if (logger.isInfoEnabled()) {
			logger.info("Saving files for URL  " + totalTime);
		}
		// executor.execute((new MailDownloadService(url)));
	}
}

package com.pramati.crawler.maildownloaderimpl;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.pramati.crawler.maildownloader.MailDownloadController;
import com.pramati.crawler.util.Utility;

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

		Utility util = Utility.getInstance();
		if (!util.isExist(util.getFileLoc())
				/*|| util.createFolder(util.getFileLoc())*/) {
			util.createFolder(util.getFileLoc());
			//downloadMailurls(mailLinkOfYear);
		}
		 downloadMailurls(mailLinkOfYear);
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
	private void downloadMailurls(Set<String> mailLinkOfYear)
			throws IOException, InterruptedException {
		long startTime = System.currentTimeMillis();

		Iterator<String> it = mailLinkOfYear.iterator();
		int numberOfThreads = Utility.getInstance().getNumberofThreads();

		ExecutorService executor = Executors
				.newFixedThreadPool(numberOfThreads);
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

		executor.awaitTermination(numberOfThreads, TimeUnit.MINUTES);

		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		if (logger.isDebugEnabled()) {
			logger.debug("Time taken in download is  :" + totalTime);
		}

		if (logger.isInfoEnabled()) {
			logger.info("Saved files for URL  " + totalTime);
		}
		// executor.execute((new MailDownloadService(url)));
	}
}

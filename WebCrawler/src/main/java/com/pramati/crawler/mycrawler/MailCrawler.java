package com.pramati.crawler.mycrawler;

import java.io.IOException;
import java.util.Scanner;
import java.util.Set;

import org.apache.log4j.Logger;

import com.pramati.crawler.mailDownloader.HTMLExtractor;
import com.pramati.crawler.mailDownloader.MailDownloadController;
import com.pramati.crawler.mailDownloaderImp.HTMLExtractorImp;
import com.pramati.crawler.mailDownloaderImp.MailDownloadControllerImp;
import com.pramati.crawler.uti.Utility;
import com.pramati.crawler.uti.Utility.TABLETITLE;

/**
 * 
 *
 * Main class
 * 
 */
public class MailCrawler {

	/**
	 * @param args
	 *            stub to test the mailDownloader
	 * 
	 *            Developer: deepak.singh@imaginea.com
	 * 
	 */

	final static Logger logger = Logger.getLogger(MailCrawler.class);

	public static void main(String[] args) {
		try {

			// take input from user to download the mails of corresponding
			// years
			TABLETITLE tbt = getInputForYear();

			HTMLExtractor htmlReq = new HTMLExtractorImp();
			Set<String> mailLinkOfYear = htmlReq.htmlURLExtractor(
					Utility.URL_TO_CRWL, Utility.TABLE_ID, tbt.toString());

			MailDownloadController mController = new MailDownloadControllerImp();
			mController.downloadMails(mailLinkOfYear);
		} catch (InterruptedException e) {
			logger.error("URL Interruptions", e);
		} catch (IOException e) {
			logger.error("IO Issue", e);
		}

	}

	/**
	 * 
	 *
	 * Helper method to take user input for years
	 * 
	 */
	private static TABLETITLE getInputForYear() {
		int choice = 0;
		TABLETITLE year = null;
		do {
			// logs a debug message
			if (logger.isDebugEnabled()) {
				logger.debug("Accepting User input");
			}
			System.out.println("Available Years: ");
			System.out
					.println("Please select a cooresponding number for Years.");
			Scanner user_input = new Scanner(System.in);

			int i = 1;
			for (TABLETITLE value : TABLETITLE.values()) {
				System.out.println(i + ": " + value.name());
				i++;
			}
			try {
				choice = user_input.nextInt();
				year = TABLETITLE.values()[choice - 1];
			} catch (Exception e) {
				System.out
						.println("You must choose a valid year. Try numbers.");
				user_input.next();
			}
		} while (choice == 0);
		if (logger.isInfoEnabled()) {
			logger.info("Year selected is :" + year);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Year selected is :" + year);
		}
		return year;
	}

}

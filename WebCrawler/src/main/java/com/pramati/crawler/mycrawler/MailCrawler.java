package com.pramati.crawler.mycrawler;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
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
			System.err
					.println("Please check the network connection and try again!!");
			logger.error("Please check the network connection and try again!!",
					e);
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
				while (choice > 4 || choice <= 0) {
					System.out
							.println("You must choose a valid year. Try numbers in range 1-4.");
					choice = user_input.nextInt();
				}
				year = TABLETITLE.values()[choice - 1];

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

	public void readPropertiesFile() throws IOException {

		Properties prop = new Properties();
		InputStream input = null;

		try {

			String filename = "config.properties";
			input = getClass().getClassLoader().getResourceAsStream(filename);
			if (input == null) {
				return;
			}

			prop.load(input);

			Enumeration<?> e = prop.propertyNames();
			while (e.hasMoreElements()) {
				String key = (String) e.nextElement();
				String value = prop.getProperty(key);
				if (key.equals("baseURL")) {
					// Utility.URL_TO_CRWL=value;
				}
			}

		} finally {
			if (input != null) {

				input.close();
			}
		}

	}

}

package com.pramati.crawler.mycrawler;

import java.io.IOException;
import java.util.Scanner;
import java.util.Set;

import org.apache.log4j.Logger;

import com.pramati.crawler.maildownloader.HTMLExtractor;
import com.pramati.crawler.maildownloader.MailDownloadController;
import com.pramati.crawler.maildownloaderImpl.HTMLExtractorImp;
import com.pramati.crawler.maildownloaderImpl.MailDownloadControllerImp;
import com.pramati.crawler.util.Utility;
import com.pramati.crawler.util.Utility.TABLETITLE;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
			String tbt;
			if (args.length == 0) {
				tbt = getInputForYear();
			} else {
				tbt = args[0];
			}
			/*
			 * Utility util = Utility.getInstance();
			 * util.readPropertiesFile("config.properties");
			 */
			ApplicationContext context = Utility.getAppContext();
			Utility util = (Utility) context.getBean("utility");
			util.readPropertiesFile("config.properties");
			util.setYearString(tbt);

			HTMLExtractor htmlReq = (HTMLExtractorImp) context
					.getBean("htmlExtractor");
			// HTMLExtractor htmlReq = new HTMLExtractorImp();
			Set<String> mailLinkOfYear = htmlReq.extractHTMLForurl(
					util.getUrlToCrawl(), util.getTableID(), tbt);

			// MailDownloadController mController = new
			// MailDownloadControllerImp();
			MailDownloadController mController = (MailDownloadControllerImp) context
					.getBean("mailDownloadController");
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
	 * @throws IOException
	 * 
	 */
	private static String getInputForYear() throws IOException {
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
				// getInputForYear();
				while (choice > TABLETITLE.values().length || choice <= 0) {
					System.out
							.println("You must choose a valid year. Try numbers in range 1-"
									+ TABLETITLE.values().length);
					choice = user_input.nextInt();
				}
				year = TABLETITLE.values()[choice - 1];

			} finally {
				user_input.close();
			}

		} while (choice == 0);
		if (logger.isInfoEnabled()) {
			logger.info("Year selected is :" + year);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Year selected is :" + year);
		}
		return year.toString();
	}

}

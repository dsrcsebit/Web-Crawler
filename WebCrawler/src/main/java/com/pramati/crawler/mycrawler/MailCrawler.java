package com.pramati.crawler.mycrawler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.pramati.crawler.maildownloader.HTMLExtractor;
import com.pramati.crawler.maildownloader.MailDownloadController;
import com.pramati.crawler.util.Utility;
import com.pramati.crawler.util.Utility.TABLETITLE;

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
	HTMLExtractor htmlReq;
	MailDownloadController mController;

	public static void main(String[] args) {

		try {

			// take input from user to download the mails of corresponding
			// years
			String tbt;
			if (args == null || args.length == 0) {
				tbt = getInputForYear();
			} else {
				tbt = args[0];
			}

			ApplicationContext context = Utility.getAppContext();
			Utility util = (Utility) context.getBean("utility");
			util.readPropertiesFile("config.properties");
			util.setYearString(tbt);

			MailCrawler mailCrawler = (MailCrawler) context
					.getBean("mainCrawler");
			Set<String> mailLinkOfYear = mailCrawler.getHtmlReq()
					.extractHTMLForurl(util.getUrlToCrawl(), util.getTableID(),
							tbt);

			MailDownloadController mController = mailCrawler.getmController();
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

	public HTMLExtractor getHtmlReq() {
		return htmlReq;
	}

	public void setHtmlReq(HTMLExtractor htmlReq) {
		this.htmlReq = htmlReq;
	}

	public MailDownloadController getmController() {
		return mController;
	}

	public void setmController(MailDownloadController mController) {
		this.mController = mController;
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

			Scanner user_input = new Scanner(new InputStreamReader(System.in, "UTF-8"));

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

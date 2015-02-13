package com.pramati.crawler.mailDownloaderImp;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

import com.pramati.crawler.mailDownloader.HTMLExtractor;
import com.pramati.crawler.uti.Utility;

/**
 * 
 *
 * Take the base URL, provides the task to threadpool
 * 
 */
public class MailDownloader implements Runnable {

	final String URL;
	final static Logger logger = Logger.getLogger(MailDownloader.class);

	public MailDownloader(String uRL) {
		super();
		URL = uRL;
	}

	/**
	 * 
	 * Wrapper method to extract the data and write to local file System
	 * 
	 * 
	 */
	public boolean saveMailFromURL() throws IOException {
		HTMLExtractor hreq = new HTMLExtractorImp();
		Set<String> mailURL = hreq.htmlURLExtractor(URL + "?0",
				"table#msglist", "");
		Set<String> temp = new HashSet<String>();
		int counter = 1;
		temp.addAll(mailURL);

		while (temp.size() > 1) {
			temp.clear();
			temp = hreq.htmlURLExtractor(URL + "?" + counter++,
					"table#msglist", "");

			if (temp != null && temp.size() > 1) {
				mailURL.addAll(temp);
			}
		}
		Iterator<String> it = mailURL.iterator();
		while (it.hasNext()) {

			String testURL = it.next().toString();
			if (testURL.indexOf("%") != -1) {
				testURL = testURL.substring(0, testURL.indexOf("%") - 1)
						+ "/raw/" + testURL.substring(testURL.indexOf("%"));
				String fileName = testURL.substring(testURL.indexOf("%"))
						+ testURL.hashCode();

				Utility.writeToFile(hreq.linkParser(testURL), Utility.FILE_LOC
						+ fileName + System.currentTimeMillis());
			}
		}
		return true;
	}

	/**
	 * 
	 *
	 * task to execute by threads
	 * 
	 */

	public void run() {
		try {
			saveMailFromURL();
		} catch (IOException e) {

			logger.error("Error in saving files"
					+ Thread.currentThread().getName());

		}
	}

}
package com.pramati.crawler.maildownloaderImpl;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

import com.pramati.crawler.maildownloader.HTMLExtractor;
import com.pramati.crawler.util.Utility;

/**
 * 
 *
 * Take the base URL, provides the task to threadpool
 * 
 */
public class MailDownloader implements Runnable {

	private final String url;
	private final static Logger logger = Logger.getLogger(MailDownloader.class);
	private HTMLExtractor hreq;

	public MailDownloader(String uRL, HTMLExtractor hreq) {
		super();
		url = uRL;
		this.hreq = hreq;
	}

	/**
	 * 
	 * Wrapper method to extract the data and write to local file System
	 * 
	 * 
	 */
	public boolean saveMailFromURL() throws IOException {
		// = new HTMLExtractorImp();
		Set<String> mailURL = hreq.extractHTMLForurl(url + "?0",
				"table#msglist", "");
		Set<String> temp = new HashSet<String>();
		int counter = 1;
		temp.addAll(mailURL);

		while (temp.size() > 1) {
			temp.clear();
			temp = hreq.extractHTMLForurl(url + "?" + counter++,
					"table#msglist", "");

			if (temp != null && temp.size() > 1) {
				mailURL.addAll(temp);
			}
		}
		Iterator<String> it = mailURL.iterator();
		Utility util = Utility.getInstance();
		while (it.hasNext()) {

			String testURL = it.next().toString();
			if (testURL.indexOf("%") != -1) {
				testURL = testURL.substring(0, testURL.indexOf("%") - 1)
						+ "/raw/" + testURL.substring(testURL.indexOf("%"));
				String fileName = testURL.substring(testURL.indexOf("%"))
						+ testURL.hashCode();
				if (!util.isExist(fileName))
					util.writeToFile(hreq.parseLinkForData(testURL),
							util.getFileLoc() + fileName);
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

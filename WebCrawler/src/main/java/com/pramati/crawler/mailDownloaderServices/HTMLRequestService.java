package com.pramati.crawler.mailDownloaderServices;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.pramati.crawler.mailDownloader.HTMLRequester;

/**
 * Service implementation for extacting email URLs from HTML file and parsing
 * the URL data.
 */

public class HTMLRequestService implements HTMLRequester {
	final static Logger logger = Logger.getLogger(HTMLRequestService.class);

	/**
	 * Returns a set of URLs on the HTML page given as param
	 *
	 * @param webURL
	 *            a string URL giving the location of page.
	 * @param tableID
	 *            the div of the table in HTML page.
	 * @param text
	 *            table head
	 * @return the set of URL for specified URL input
	 * 
	 */
	public Set<String> htmlURLExtractor(String webURL, String tableID,
			String text) throws IOException {
		Document doc;
		Set<String> linkSetByDate = new HashSet<String>();
		// to make the URL for email.
		if (webURL.contains("thread") && !webURL.contains("thread?")) {
			webURL = webURL.replace("thread", "");
		}

		int statusCode = connectURL(webURL);
		if (statusCode == 200) {
			if (logger.isDebugEnabled()) {
				logger.debug("Connected to URL " + webURL + " successfuly");
			}

			if (logger.isInfoEnabled()) {
				logger.info("Connected to URL " + webURL + " successfuly");
			}
			doc = Jsoup.connect(webURL).timeout(10 * 1000).get();
			// for paginated data
			if (webURL.contains("thread?")) {
				webURL = webURL.substring(0, webURL.length() - 8);
			}
			// Extracting the URLs and storing in Set to return
			for (Element table : doc.select(tableID)) {
				if (text.isEmpty() || table.select("th").text().equals(text)) {
					for (Element row : table.select("tr")) {
						Elements tds = row.select("a[href]");
						String attribute = tds.attr("href");
						if (!attribute.isEmpty()
								&& !attribute.equals("browser"))
							linkSetByDate.add(webURL + attribute);

					}
				}
			}

		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("Not able to Connect to URL " + webURL);
			}

			if (logger.isInfoEnabled()) {
				logger.info("Not able to Connect to URL " + webURL);
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("The counts of extracted URLs is  "
					+ linkSetByDate.size());
		}

		if (logger.isInfoEnabled()) {
			logger.info("The counts of extracted URLs is  "
					+ linkSetByDate.size());
		}
		return linkSetByDate;
	}

	/**
	 * Returns status code for connection
	 *
	 * @param webURL
	 *            a string URL giving the location of page.
	 * 
	 * @return status code
	 * 
	 */
	private int connectURL(String webURL) throws IOException {
		Connection.Response response = Jsoup
				.connect(webURL)
				.userAgent(
						"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
				.timeout(160000).execute();

		int statusCode = response.statusCode();
		return statusCode;
	}

	/**
	 * Returns data after parsing the webURL
	 *
	 * @param webURL
	 *            a string URL giving the location of page.
	 * 
	 * @return data after parsing the webURL
	 * 
	 */
	public String linkParser(String webURL) throws IOException {

		String resultString = "";
		Document doc;

		int statusCode = connectURL(webURL);
		if (statusCode == 200) {
			doc = Jsoup.connect(webURL).timeout(15 * 1000).get();
			resultString = doc.text();
			if (logger.isDebugEnabled()) {
				logger.debug("Data parsing successful for URL :" + webURL);
			}

			if (logger.isInfoEnabled()) {
				logger.info("Data parsing successful :" + webURL);
			}

		}

		return resultString;
	}

	public boolean isBrokenLink(URL baseURL) {
		// TODO Auto-generated method stub
		return false;
	}

	public void displayBrokenLinkReport(Set<String> brokenLinks) {
		// TODO Auto-generated method stub

	}

}

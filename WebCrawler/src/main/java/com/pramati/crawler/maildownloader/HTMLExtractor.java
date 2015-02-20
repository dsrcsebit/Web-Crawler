package com.pramati.crawler.maildownloader;

import java.io.IOException;
import java.net.URL;
import java.util.Set;

/**
 * this interface provides API for extracting the email links in HTML page,
 * parse the page to extract contents
 */
public interface HTMLExtractor {
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
	Set<String> extractHTMLForurl(String webURL, String tableID, String text)
			throws IOException;

	/**
	 * Returns status code for connection
	 *
	 * @param webURL
	 *            a string URL giving the location of page.
	 * 
	 * @return status code
	 * 
	 */

	String parseLinkForData(String URL) throws IOException;

	/**
	 * Returns status code for for link
	 *
	 * @param baseURL
	 *            a URL to check.
	 * 
	 * @return status code
	 * 
	 */
	boolean isBrokenLink(URL baseURL);

	/**
	 * Returns void
	 *
	 * @param brokenLinks
	 *            a set of broken link to display.
	 * 
	 * @return nothing
	 * 
	 */

	void displayBrokenLinkReport(Set<String> brokenLinks);
}

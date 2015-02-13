package com.pramati.crawler.mailDownloader;

import java.io.IOException;
import java.net.URL;
import java.util.Set;

/**
 * this interface provides API for extracting the email links in HTML page,
 * parse the page to extract contents
 */
public interface HTMLExtractor {

	Set<String> htmlURLExtractor(String webURL, String tableID, String text)
			throws IOException;

	String linkParser(String URL) throws IOException;

	boolean isBrokenLink(URL baseURL);

	void displayBrokenLinkReport(Set<String> brokenLinks);
}

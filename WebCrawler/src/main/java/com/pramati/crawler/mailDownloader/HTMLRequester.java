package com.pramati.crawler.mailDownloader;

import java.io.IOException;
import java.net.URL;
import java.util.Set;

public interface HTMLRequester {
	Set<String> htmlURLExtractor(String webURL, String tableID, String text)
			throws IOException;

	String linkParser(String URL) throws IOException;

	boolean isBrokenLink(URL baseURL);

	void displayBrokenLinkReport(Set<String> brokenLinks);
}

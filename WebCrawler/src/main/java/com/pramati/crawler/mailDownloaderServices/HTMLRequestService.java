package com.pramati.crawler.mailDownloaderServices;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.pramati.crawler.mailDownloader.HTMLRequester;

public class HTMLRequestService implements HTMLRequester {
	Document doc;

	public Set<String> htmlURLExtractor(String webURL, String tableID,
			String text) throws IOException {
		Set<String> linkSetByDate = Collections.emptySet();
		if (webURL.contains("thread") && !webURL.contains("thread?")) {
			webURL = webURL.replace("thread", "");
		}

		linkSetByDate = new HashSet<String>();
		int statusCode = connectURL(webURL);
		if (statusCode == 200) {
			doc = Jsoup.connect(webURL).get();
			if(webURL.contains("thread?")){
				webURL=webURL.substring(0, webURL.length()-8);
			}
			for (Element table : doc.select(tableID)) {
				if (text.isEmpty() || table.select("th").text().equals(text)) {
					for (Element row : table.select("tr")) {
						Elements tds = row.select("a[href]");
						String attribute=tds.attr("href");
						if(!attribute.isEmpty())
						linkSetByDate.add(webURL + attribute);

					}
				}
			}

		}

		return linkSetByDate;
	}

	private int connectURL(String webURL) throws IOException {
		Connection.Response response = Jsoup
				.connect(webURL)
				.userAgent(
						"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
				.timeout(10000).execute();

		int statusCode = response.statusCode();
		return statusCode;
	}

	public String linkParser(String URL) throws IOException {

		String resultString = "";

		int statusCode = connectURL(URL);
		if (statusCode == 200) {
			doc = Jsoup.connect(URL).get();
			resultString = doc.text();

		}

		return resultString;
		// DO DFS to get all mail links
	}

	public boolean isBrokenLink(URL baseURL) {
		// TODO Auto-generated method stub
		return false;
	}

	public void displayBrokenLinkReport(Set<String> brokenLinks) {
		// TODO Auto-generated method stub

	}

}

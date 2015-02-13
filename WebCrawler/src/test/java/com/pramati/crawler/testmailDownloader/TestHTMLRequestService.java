package com.pramati.crawler.testmailDownloader;

import static org.junit.Assert.assertSame;

import java.io.IOException;
import java.util.Set;

import org.junit.Test;

import com.pramati.crawler.mailDownloader.HTMLRequester;
import com.pramati.crawler.mailDownloaderServices.HTMLRequestService;
import com.pramati.crawler.uti.Utility;

public class TestHTMLRequestService {

	@Test
	public void testHTMLRequestService() throws IOException {
		HTMLRequester htmlReq = new HTMLRequestService();
		Set<String> mailLinkOf2014 = htmlReq.htmlURLExtractor(
				Utility.URL_TO_CRWL, "table.year", "Year 2015");
		assertSame("The testHTMLRequestService is executing", 2, mailLinkOf2014.size());
	}
	


}

package com.pramati.crawler.testmailDownloader;

import static org.junit.Assert.assertSame;

import java.io.IOException;
import java.util.Set;

import org.junit.Test;

import com.pramati.crawler.mailDownloader.HTMLExtractor;
import com.pramati.crawler.mailDownloaderImp.HTMLExtractorImp;
import com.pramati.crawler.uti.Utility;

public class TestHTMLExtractor {

	@Test
	public void testHTMLRequestService() throws IOException {
		HTMLExtractor htmlReq = new HTMLExtractorImp();
		Set<String> mailLinkOf2014 = htmlReq.htmlURLExtractor(
				Utility.URL_TO_CRWL, "table.year", "Year 2015");
		assertSame("The testHTMLRequestService is executing", 2, mailLinkOf2014.size());
	}
	


}

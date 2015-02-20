package com.pramati.crawler.testmaildownloader;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.io.IOException;
import java.util.Set;

import org.junit.Test;

import com.pramati.crawler.maildownloader.HTMLExtractor;
import com.pramati.crawler.maildownloaderimpl.HTMLExtractorImp;
import com.pramati.crawler.util.Utility;

public class TestHTMLExtractor {
	static HTMLExtractor htmlReq;
	static Utility util;

	
	static { htmlReq = (HTMLExtractorImp) Utility.getAppContext().getBean(
	  "htmlExtractor"); util = (Utility)
	 Utility.getAppContext().getBean("utility"); try {
	 util.readPropertiesFile("config.properties"); } catch (IOException e) {
	 }
	}
	 
	/*estHTMLExtractor thExtract = (TestHTMLExtractor) Utility.getAppContext()
			.getBean("testExtrator");*/

	@Test
	public void testhtmlForUrl() throws IOException {

		Set<String> mailLinkOf2014 = htmlReq.extractHTMLForurl(
				util.getUrlToCrawl(), util.getTableID(),
				util.getYearString());
		assertSame("The testHTMLRequestService is executing", 2,
				mailLinkOf2014.size());
	}

	@Test
	public void testParseLinkForData() throws IOException {

		String data = htmlReq.parseLinkForData(util
				.getTestURLforParseData());
		assertNotNull(data);

	}

}

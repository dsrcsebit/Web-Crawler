/**
 * 
 */
package com.pramati.crawler.testmaildownloader;

import java.io.IOException;

import org.junit.Test;

import com.pramati.crawler.maildownloaderimpl.HTMLExtractorImp;
import com.pramati.crawler.maildownloaderimpl.MailDownloader;
import com.pramati.crawler.util.Utility;

/**
 * @author deepaks
 *
 */
public class TestMailDownloader {
	static Utility util;
	static {
		util = (Utility) Utility.getAppContext().getBean("utility");
		try {
			util.readPropertiesFile("config.properties");
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	@Test
	public void testsaveMailFromURL() throws IOException {

		new Thread(new MailDownloader(util.getUrlToCrawl() + "/thread",
				(HTMLExtractorImp) Utility.getAppContext().getBean(
						"htmlExtractor"))).start();
	}

	@Test(expected = IOException.class)
	public void testaveMailFromURL1() throws IOException {

		new MailDownloader(util.getUrlToCrawl() + "/thread",
				(HTMLExtractorImp) Utility.getAppContext().getBean(
						"htmlExtractor")).saveMailFromURL();
	}
}

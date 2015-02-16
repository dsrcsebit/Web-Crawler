/**
 * 
 */
package com.pramati.crawler.testmailDownloader;

import java.io.IOException;

import org.junit.Test;

import com.pramati.crawler.mailDownloaderImp.HTMLExtractorImp;
import com.pramati.crawler.mailDownloaderImp.MailDownloader;

/**
 * @author deepaks
 *
 */
public class TestMailDownloader {

	@Test
	public void testsaveMailFromURL() throws IOException {
		new Thread(
				new MailDownloader(
						"http://mail-archives.apache.org/mod_mbox/maven-users/201502.mbox/thread", new HTMLExtractorImp()))
				.start();
	}

	@Test(expected = IOException.class)
	public void testaveMailFromURL1() throws IOException {

		new MailDownloader(
				"http://xyz-mail-archives.apache.org/mod_mbox/maven-users/201502.mbox/thread/deepak",new HTMLExtractorImp())
				.saveMailFromURL();
	}

}

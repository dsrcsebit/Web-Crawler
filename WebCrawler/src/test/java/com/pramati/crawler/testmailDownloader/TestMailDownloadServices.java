/**
 * 
 */
package com.pramati.crawler.testmailDownloader;

import java.io.IOException;

import org.junit.Test;

import com.pramati.crawler.mailDownloaderServices.MailDownloadService;

/**
 * @author deepaks
 *
 */
public class TestMailDownloadServices {

	@Test
	public void testsaveMailFromURL() throws IOException {
		new Thread(
				new MailDownloadService(
						"http://mail-archives.apache.org/mod_mbox/maven-users/201502.mbox/thread"))
				.start();
	}

	@Test(expected = IOException.class)
	public void testaveMailFromURL1() {
		new Thread(
				new MailDownloadService(
						"http://mail-archives.apache.org/mod_mbox/maven-users/201502.mbox/thread/deepak"))
				.start();
	}

}

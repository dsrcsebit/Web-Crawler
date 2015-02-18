package com.pramati.crawler.maildownloader;

import java.io.IOException;
import java.util.Set;

/**
 * Provides an API to save emails from links to local file System
 */
public interface MailDownloadController {
	void downloadMails(Set<String> mailLinkOfYear) throws IOException,
			InterruptedException;

}

package com.pramati.crawler.mycrawler;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.pramati.crawler.mailDownloader.HTMLRequester;
import com.pramati.crawler.mailDownloaderServices.HTMLRequestService;
import com.pramati.crawler.mailDownloaderServices.MailDownloadService;
import com.pramati.crawler.uti.Utility;

public class crawler {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			downloadMain();
			System.out.println(MailDownloadService.i);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Problem with IO");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.err.println("Problem with URL");
		}
		// new MailDownloadService((String)
		// mailLinkOf2014.toArray()[1]).saveMailFromURL();

	}

	private static void downloadMain() throws IOException, InterruptedException {
		long startTime = System.currentTimeMillis();
		HTMLRequester htmlReq = new HTMLRequestService();
		Set<String> mailLinkOf2014 = htmlReq.htmlURLExtractor(
				Utility.URL_TO_CRWL, "table.year", "Year 2014");
		Iterator<String> it = mailLinkOf2014.iterator();

		ExecutorService executor = Executors.newFixedThreadPool(6);
		String url = null;
		while (it.hasNext()) {
		url = it.next();
		executor.execute((new MailDownloadService(url)));
		}
		executor.shutdown();

		executor.awaitTermination(4, TimeUnit.MINUTES);

		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println(totalTime);
	}
}

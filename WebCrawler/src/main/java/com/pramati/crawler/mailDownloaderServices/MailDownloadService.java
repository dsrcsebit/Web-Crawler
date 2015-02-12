package com.pramati.crawler.mailDownloaderServices;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import com.pramati.crawler.mailDownloader.HTMLRequester;
import com.pramati.crawler.uti.Utility;

public class MailDownloadService implements Runnable {

	String URL;
	public static AtomicInteger i = new AtomicInteger();

	public MailDownloadService(String uRL) {
		super();
		URL = uRL;
	}

	private  boolean  saveMailFromURL() throws IOException {
		HTMLRequester hreq = new HTMLRequestService();
		Set<String> mailURL = hreq.htmlURLExtractor(URL + "?0",
				"table#msglist", "");
		Set<String> temp = new HashSet<String>();
		int counter = 1;
		temp.addAll(mailURL);

		while (temp.size() > 1) {
			temp.clear();
			temp = hreq.htmlURLExtractor(URL + "?" + counter++,
					"table#msglist", "");
			//temp.remove(o)

			if (temp != null && temp.size() > 1) {
				mailURL.addAll(temp);
			}
		}
		System.out.println(mailURL.size());
		Iterator<String> it = mailURL.iterator();
		while (it.hasNext()) {
			// System.out.println(mailURL.size() + "mailURLs are"+mailURL );

			String testURL = it.next().toString();
			if (testURL.indexOf("%") != -1) {
				testURL = testURL.substring(0, testURL.indexOf("%") - 1)
						+ "/raw/" + testURL.substring(testURL.indexOf("%"));
				String fileName = testURL.substring(testURL.indexOf("%"))
						+ testURL.hashCode();
				try
				{
					Utility.writeToFile(hreq.linkParser(testURL), fileName+System.currentTimeMillis());
				}
				catch(Exception e){
					Utility.writeToFile(hreq.linkParser(testURL), fileName+System.currentTimeMillis());
				}
			} else {
				//String fileName = String.valueOf(System.currentTimeMillis())
						//+ testURL;
				System.out.println(testURL);
				Utility.writeToFile(hreq.linkParser(testURL), testURL);
			}
			// System.out.println(hreq.linkParser(testURL));
		}
		return false;
	}

	public void run() {
		try {
			saveMailFromURL();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

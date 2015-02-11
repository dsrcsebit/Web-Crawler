package com.pramati.crawler.mailDownloaderServices;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import com.pramati.crawler.mailDownloader.HTMLRequester;
import com.pramati.crawler.mailDownloader.MailDownloader;
import com.pramati.crawler.uti.Utility;

public class MailDownloadService implements MailDownloader {

	String URL;
	public static AtomicInteger i=new AtomicInteger();

	public MailDownloadService(String uRL) {
		super();
		URL = uRL;
	}

	public boolean saveMailFromURL() throws IOException {
		HTMLRequester hreq = new HTMLRequestService();
		Set<String> mailURL = hreq.htmlURLExtractor(URL+"?0", "table#msglist", "");
		System.out.println("Mail URL after param" + mailURL.size());
		Set<String> temp = new HashSet<String>();
		int counter = 1;
		temp.addAll(mailURL);

		while (temp.size() > 1) {
			temp.clear();
			temp = hreq.htmlURLExtractor(URL + "?" + counter++,
					"table#msglist", "");
			
			if (temp != null && temp.size()>1) {
				if(counter==3)
				System.out.println("Temp Size" +temp);
				System.out.println("Temp Size" +temp.size());
				mailURL.addAll(temp);
			}
		}
		i.addAndGet(mailURL.size());

		Iterator<String> it = mailURL.iterator();
		while (it.hasNext()) {
			// System.out.println(mailURL.size() + "mailURLs are"+mailURL );

			String testURL = it.next().toString();
			if (testURL.indexOf("%") != -1) {
				testURL = testURL.substring(0, testURL.indexOf("%") - 1)
						+ "/raw/" + testURL.substring(testURL.indexOf("%"));
				String fileName=testURL.substring(testURL.indexOf("%"))+testURL.hashCode();
				Utility.writeToFile(hreq.linkParser(testURL),fileName
						);
			} else {
				String fileName=String.valueOf(System.currentTimeMillis())+testURL.hashCode();
				Utility.writeToFile(hreq.linkParser(testURL),fileName
						);
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

package com.pramati.crawler.mycrawler;

import java.io.IOException;

import com.pramati.crawler.mailDownloader.MailDownloadController;
import com.pramati.crawler.mailDownloaderServices.MailDownloadControllerImp;
import com.pramati.crawler.mailDownloaderServices.MailDownloadService;

public class crawler {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MailDownloadController mController = new MailDownloadControllerImp();
			mController.downloadMails();
			System.out.println(MailDownloadService.i);
		} catch (InterruptedException e) {
			System.err.println("Problem with URL");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

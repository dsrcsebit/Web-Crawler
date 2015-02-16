package com.pramati.crawler.uti;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.concurrent.CountDownLatch;

import com.pramati.crawler.mailDownloaderImp.HTMLExtractorImp;
import com.pramati.crawler.mailDownloaderImp.MailDownloader;

public class Utility {
	/**
	 * 
	 *
	 * Enum data for Years
	 * 
	 */
	public enum TABLETITLE {

		YEAR_2012("Year 2012"), YEAR_2013("Year 2013"), YEAR_2014("Year 2014"), YEAR_2015(
				"Year 2015");
		private final String value;

		private TABLETITLE(final String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}

		@Override
		public String toString() {
			return getValue();
		}

	};

	public static final String URL_TO_CRWL = "http://mail-archives.apache.org/mod_mbox/maven-users/";

	public static final String FILE_LOC = "/home/deepaks/Documents/Mail/";

	public static final String TABLE_ID = "table.year";

	public static boolean isExist(String url) {
		return new File(FILE_LOC, url).exists();
	}

	/**
	 * 
	 *
	 * @param textToWrite
	 *            the text data to write to file called fileName
	 * 
	 *            Utility method to save the data in local file System
	 * 
	 */

	public static void writeToFile(String textToWrite, String fileName)
			throws IOException {
		Writer w = null;
		try {

			File file;
			file = new File(fileName);
			FileOutputStream is = new FileOutputStream(file);
			OutputStreamWriter osw = new OutputStreamWriter(is);
			w = new BufferedWriter(osw);
			w.write(textToWrite);

		} finally {

			if (w != null) {
				w.close();
			}
		}

	}

	/**
	 * 
	 *
	 * @param fileLoc
	 *            drop the file from fileLoc
	 * 
	 */

	public static void dropExistingFiles(String fileLoc) throws IOException,
			InterruptedException {
		CountDownLatch cdl = new CountDownLatch(1);
		File file = new File(fileLoc);
		deleteFolder(file, cdl);
		cdl.await();

		file.mkdir();
		cdl.countDown();
	}

	/**
	 * 
	 *
	 * @param fileLoc
	 *            drop the file from fileLoc
	 * 
	 */

	public static void createFolder(String fileLoc) throws IOException,
			InterruptedException {
		/*
		 * CountDownLatch cdl = new CountDownLatch(1); File file = new
		 * File(fileLoc); deleteFolder(file, cdl); cdl.await();
		 */
		File file = new File(fileLoc);
		file.mkdir();
		/* cdl.countDown(); */
	}

	/**
	 * 
	 *
	 * Helper method to delete directory data
	 * 
	 */
	private static void deleteFolder(File folder, CountDownLatch cdl) {
		File[] files = folder.listFiles();
		if (files != null) { // some JVMs return null for empty dirs
			for (File f : files) {
				if (f.isDirectory()) {
					deleteFolder(f, cdl);
				} else {
					f.delete();
				}
			}
		}
		folder.delete();
		cdl.countDown();
	}

	public static MailDownloader newMailDownloader(String url) {
		return new MailDownloader(url, new HTMLExtractorImp());
	}

}

package com.pramati.crawler.uti;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.concurrent.CountDownLatch;

public class Utility {
	/**
	 * 
	 *
	 * Enum data for Years
	 * 
	 */
	public enum TABLETITLE {

		YEAR_ONE("Year 2012"), YEAR_TWO("Year 2013"), YEAR_THREE("Year 2014"), YEAR_FOUR(
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

}

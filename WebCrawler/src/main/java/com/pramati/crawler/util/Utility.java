package com.pramati.crawler.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pramati.crawler.maildownloaderimpl.HTMLExtractorImp;
import com.pramati.crawler.maildownloaderimpl.MailDownloader;

public class Utility {

	public static final Utility utilInstance = new Utility();

	public static Utility getInstance() {
		return utilInstance;
	}

	private Utility() {

	}

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

	private String urlToCrawl;
	// = "http://mail-archives.apache.org/mod_mbox/maven-users/";

	private String fileLoc;
	// = "/home/deepaks/Documents/Mail/";

	private String tableId;
	// = "table.year";

	private int numberofThreads;
	// =6;

	private long connectionTimeout;

	private String testURLforParseData;

	private String yearString;

	// =160000;

	public String getTestURLforParseData() {
		return testURLforParseData;
	}

	public void setTestURLforParseData(String testURLforParseData) {
		this.testURLforParseData = testURLforParseData;
	}

	public String getUrlToCrawl() {
		return urlToCrawl;
	}

	public void setUrlToCrawl(String uRL_TO_CRWL) {
		urlToCrawl = uRL_TO_CRWL;
	}

	public String getFileLoc() {
		return fileLoc;
	}

	public void setFileLoc(String fILE_LOC) {
		fileLoc = fILE_LOC;
	}

	public String getTableID() {
		return tableId;
	}

	public void setTableID(String tABLE_ID) {
		tableId = tABLE_ID;
	}

	public int getNumberofThreads() {
		return numberofThreads;
	}

	public void setNumberofThreads(int numberofThreads) {
		this.numberofThreads = numberofThreads;
	}

	public long getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(long connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public boolean isExist(String url) {
		return new File(fileLoc, url).exists();
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

	public void writeToFile(String textToWrite, String fileName)
			throws IOException {
		Writer w = null;
		try {

			File file;
			file = new File(fileName);
			FileOutputStream is = new FileOutputStream(file);
			OutputStreamWriter osw = new OutputStreamWriter(is, "UTF-8");
			w = new BufferedWriter(osw);
			w.write(textToWrite);

		} finally {

			if (w != null) {
				w.close();
			}
		}

	}

	/*	*//**
	 * 
	 *
	 * @param fileLoc
	 *            drop the file from fileLoc
	 * 
	 */
	/*
	 * 
	 * public void dropExistingFiles(String fileLoc) throws IOException,
	 * InterruptedException { CountDownLatch cdl = new CountDownLatch(1); File
	 * file = new File(fileLoc); deleteFolder(file, cdl); cdl.await();
	 * 
	 * file.mkdir(); cdl.countDown(); }
	 */

	/**
	 * 
	 *
	 * @param fileLoc
	 *            drop the file from fileLoc
	 * 
	 */

	public boolean createFolder(String fileLoc) throws IOException,
			InterruptedException {
		/*
		 * CountDownLatch cdl = new CountDownLatch(1); File file = new
		 * File(fileLoc); deleteFolder(file, cdl); cdl.await();
		 */
		File file = new File(fileLoc);
		return file.mkdir();
		/* cdl.countDown(); */
	}

	public static ApplicationContext getAppContext() {
		return new ClassPathXmlApplicationContext("Spring-Module.xml");
	}

	/**
	 * 
	 *
	 * Helper method to delete directory data
	 * 
	 */
	/*
	 * private void deleteFolder(File folder, CountDownLatch cdl) { File[] files
	 * = folder.listFiles(); if (files != null) { // some JVMs return null for
	 * empty dirs for (File f : files) { if (f.isDirectory()) { deleteFolder(f,
	 * cdl); } else { f.delete(); } } } folder.delete(); cdl.countDown(); }
	 */

	public static MailDownloader newMailDownloader(String url) {
		return new MailDownloader(url, new HTMLExtractorImp());
	}

	public void readPropertiesFile(String fileName) throws IOException {

		Properties prop = new Properties();
		InputStream input = null;
		// Utility util = new Utility();

		try {

			// String filename = "config.properties";
			input = getClass().getClassLoader().getResourceAsStream(fileName);
			if (input == null) {
				return;
			}

			prop.load(input);
			setYearString(prop.getProperty("yearstring", "Year 2015"));
			setTestURLforParseData(prop
					.getProperty(
							"testURLforParseData",
							"http://mail-archives.apache.org/mod_mbox/maven-users/201501.mbox/raw/%3cCAAkqXprgZDMh_AqBDOZx3ppd-=Ya37oJSqZN9LP+cpZKnPm40Q@mail.gmail.com%3e"));
			setFileLoc(prop.getProperty("file.loc",
					"/home/deepaks/Documents/Mail/"));
			setConnectionTimeout(Long.parseLong(prop.getProperty(
					"connectionTimeout", "160000")));
			setNumberofThreads(Integer.parseInt(prop.getProperty(
					"numberofThreads", "6")));
			setUrlToCrawl(prop.getProperty("url.crawl",
					"http://mail-archives.apache.org/mod_mbox/maven-users/"));
			setTableID(prop.getProperty("table.id", "table.year"));

		} finally {
			if (input != null) {

				input.close();
			}
		}

	}

	public String getYearString() {
		return yearString;
	}

	public void setYearString(String yearString) {
		this.yearString = yearString;
	}
}

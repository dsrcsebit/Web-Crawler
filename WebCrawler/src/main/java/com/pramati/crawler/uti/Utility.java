package com.pramati.crawler.uti;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.file.Files;
import java.util.concurrent.CountDownLatch;

public class Utility {

	public static String URL_TO_CRWL = "http://mail-archives.apache.org/mod_mbox/maven-users/";

	public static String FileLocation = "/home/deepaks/Documents/Mail/";

	public static URL getStartingURLFromUser() throws MalformedURLException {
		System.out.println("Please enter the URL");
		// Scanner sc=new Scanner(InputStreamRea);

		// TODO
		return new URL("Dimmu");
	}

	public static void writeToFile(String textToWrite, String fileName) {
		Writer w = null;
		try {
			// Whatever the file path is.

			File file;
			file = new File(FileLocation + fileName);
			FileOutputStream is = new FileOutputStream(file);
			OutputStreamWriter osw = new OutputStreamWriter(is);
			w = new BufferedWriter(osw);
			w.write(textToWrite);

		} catch (SocketTimeoutException e) {
			System.err.println("Socket Timeout Problem writing to the file");
		} catch (IOException e) {
			System.err.println("Problem writing to the file");
		} finally {
			try {
				if (w != null) {
					w.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void dropExistingFiles(CountDownLatch cd) throws IOException {
		File file = new File(FileLocation);

		deleteFolder(file);
		file.mkdir();
		cd.countDown();
	}

	private static void deleteFolder(File folder) {
		File[] files = folder.listFiles();
		if (files != null) { // some JVMs return null for empty dirs
			for (File f : files) {
				if (f.isDirectory()) {
					deleteFolder(f);
				} else {
					f.delete();
				}
			}
		}
		folder.delete();
	}

}

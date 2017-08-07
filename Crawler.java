package com.web.crawler;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {
	// USER_AGENT is used so the web server considers the crawler as a normal web browser.
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
	List<String> links = new LinkedList<String>();
	private List<String> secondLevelLinks = new LinkedList<String>();
	private Document htmlDocument;
	String filePath = "E:/Test/Emailoutput.txt";
	
	/**
	 * This method performs the crawling. It makes an HTTP request, checks the
	 * response, and then gathers up all the links on the page.
	 */
	public void crawl(String url, String year) {
		try {
			
			// get the links of http://mail-archives.apache.org/mod_mbox/maven-users/ url in set
			LinkedHashSet<String> linksSet = crawl(url);
			Iterator<String> setIterator = linksSet.iterator();
			while (setIterator.hasNext()) {
				try {
					String url1 = setIterator.next();
					// adding link which has required year
					if (url1.contains(year) &&  (!url1.contains("thread") && !url1.contains("author"))) {
						links.add(url1);
					}
				} catch (NoSuchElementException e) {
					continue;
				}
			}
			
			// iterating over arraylist of 2015 year and crawling every href link
			for (int i = 0; i < links.size(); i++) {
				crawlWithDepth(links.get(i));
			}
		} catch (Exception ioe) {
			System.out.println("Caught IO Exception");
		}
	}
	

	/**
	 * @param url
	 * Fetch the mail body and redirect to file
	 */
	public void crawlWithDepth(String url) {
		PrintStream out = null;
		try {
			out = new PrintStream(new FileOutputStream(filePath));
			System.setOut(out);
			
			// crawling child links
			LinkedHashSet<String> set1 = crawl(url);
			Iterator<String> it = set1.iterator();
			
			while (it.hasNext()) {
				String url1 = it.next();
				secondLevelLinks.add(url1);
			}
			
			// redirecting mail body to file
			for (int i = 0; i < set1.size(); i++) {
				htmlDocument = Jsoup.connect(secondLevelLinks.get(i)).get();
				Element results = htmlDocument.body();
				System.out.println(results.text());
			}
			
		} catch (IOException ioe) {
			System.out.println("Caught IO Exception");
		} 
	}

	/**
	 * @param url
	 * @return
	 * Crawl the URL and return HTML document links 
	 */
	public LinkedHashSet<String> crawl(String url) {
		
		PrintStream out = null;
		LinkedHashSet<String> toCrawlList = null;
		try {
			out = new PrintStream(new FileOutputStream(filePath));
			System.setOut(out);
			
			toCrawlList = new LinkedHashSet<String>();
			Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
			Document htmlDocument = connection.get();
			
			Elements linksOnPage = htmlDocument.select("a[href]");
			for (Element link : linksOnPage) {
				toCrawlList.add(link.absUrl("href"));
			}
		} catch (IOException e) {
			System.out.println("Caught IO Exception " +e);
		} 
		
		return toCrawlList;
	}
	
	public List<String> getLinks() {
		return this.links;
	}

	public Document getHtmlDocument() {
		return htmlDocument;
	}

	public void setHtmlDocument(Document htmlDocument) {
		this.htmlDocument = htmlDocument;
	}
}

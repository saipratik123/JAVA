package com.web.crawler;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class CrawlerService {
	private static final int MAX_PAGES_TO_SEARCH = 10;
	private Set<String> pagesVisited = new HashSet<String>();
	private List<String> pagesToVisit = new LinkedList<String>();

	public void fetchEmails(String url, String year) {
		while (this.pagesVisited.size() < MAX_PAGES_TO_SEARCH) {
			String currentUrl;
			Crawler crawler = new Crawler();
			if (this.pagesToVisit.isEmpty()) {
				currentUrl = url;
				this.pagesVisited.add(url);
			} else {
				currentUrl = this.nextUrl();
			}

			crawler.crawl(currentUrl, year);

			this.pagesToVisit.addAll(crawler.getLinks());
		}

		System.out.println("\n**Done** Visited " + this.pagesVisited.size()
				+ " web page(s)");
	}

	/**
	 * Returns the next URL to visit (in the order that they were found). We
	 * also do a check to make sure this method doesn't return a URL that has
	 * already been visited.
	 */
	private String nextUrl() {
		String nextUrl;
		do {
			nextUrl = this.pagesToVisit.remove(0);
		} while (this.pagesVisited.contains(nextUrl));

		this.pagesVisited.add(nextUrl);
		return nextUrl;
	}
}

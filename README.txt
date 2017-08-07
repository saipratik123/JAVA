The logic is implemented with the help of 3 classes
1. CrawlerTest.java
2. CrawlerService.java
3. Crawler.java

Brief Idea:
-----------

CrawlerTest.java calls the fetchEmails() method of CralwerService.java by passing the url and the year. This class ensures unique urls are picked before the crawl(url, year) method of crawler class is called. In the crawl(url, year) method, all the links of the year (2015) are selected and stored in a list. The List is iterated and each link is sent to crawlWithDepth method where in the related sub links are fetched and their respective bodies i.e. the emails are fetched and dumped into an output file. An overridden method crawl() is used to crawl the URL and return the html documents.

Steps to execute:
----------------
Either import the project into eclipse workspace or carry out below steps:

1. Create a java project (In eclipse)
2. Create package : com.web.crawler
3. Paste the above three classes and run as java application.
4. The resulting output is dumped into a file placed on the local machine's path : E:/Test/Emailoutput.txt. 

I am attaching a portion of output for your reference.

Thanks,
Sai
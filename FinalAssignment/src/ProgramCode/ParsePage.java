package ProgramCode;
/* parsePage parses through a web page and saves all the urls and email ids in collections
 * Jonathan Hopkins
 * 4/28/2020
 * parsePage.java
 */

import java.net.*;
import java.util.*;
import java.util.regex.*;
import java.io.*;

public class ParsePage {
	private WebCollection<URL> urlCollection = new WebCollection<URL>(); // Collection of urls from a webPage
	private WebCollection<String> emailCollection = new WebCollection<String>(); // Collection of emails from a webPage
	private URL webPage; // webPage to be parsed through
	
	private final Pattern emailPattern = Pattern.compile("@[a-zA-Z]++.");
	private final Pattern urlPattern = Pattern.compile("http://|www|https://");
	
	
	
	public ParsePage(URL webPage) { // Constructor
		this.webPage = webPage;
	}
	
	public URL getWebPage() { // webPage getter
		return webPage;
	}
	
	public void setWebPage(URL webPage) { // webPage setter
		this.webPage = webPage;
	}
	
	public WebCollection<URL> getURLCollection(){ // urlCollection getter
		return urlCollection;
	}
	
	public void setURLCollection(WebCollection<URL> urlCollection) { // urlCollection setter
		this.urlCollection = urlCollection;
	}
	
	public WebCollection<String> getEmailCollection(){ // emailCollection getter
		return emailCollection;
	}
	
	public void setEmailCollection(WebCollection<String> emailCollection) { // emailCollection setter
		this.emailCollection = emailCollection;
	}
	
	private void urlParse(String str) throws MalformedURLException {
		/* Parses through a string that contains an URL
		 * Gets rid of unnecessary characters
		 */
		if(str.startsWith("http"))
			urlCollection.add(new URL(str));
		else {
			for(int i = 0;i<str.length();i++) {
				if(str.charAt(i) == '"');{
					int first = i;
					int last = str.indexOf('"', first);
					if(last > 0) {
						String urlSubString = str.substring(first, last);
						Matcher URLMatcher = urlPattern.matcher(urlSubString);
						if(URLMatcher.find())
							try {
								urlCollection.add(new URL(urlSubString));
							} catch(MalformedURLException e) {
								try {
								urlCollection.add(new URL("https"+urlSubString));
								}
								catch(MalformedURLException e1) {
									
								}
							}
						
						i = last;
					}
				}
			}
		}
	}
	
	private void emailParse(String str) {
		/* Parses through a string that contains an email
		 * Gets rid of the unnecessary characters in the string
		 */
		if(str.contains("href")){
			if(str.contains("mailto:")) {
				int start = str.indexOf(":") + 1;
				int end = str.indexOf('.', start) + 4;
				emailCollection.add(str.substring(start,end));
			}
			else {
				int start = str.indexOf('"') + 1;
				int end = str.lastIndexOf('"');
				emailCollection.add(str.substring(start, end));
			}
		}
		else if(str.contains("mailto:")) {
			int start = str.indexOf(":") + 1;
			int end = str.indexOf('.', start) + 4;
			emailCollection.add(str.substring(start,end));
		}
		else {
			emailCollection.add(str);
		}
	}
	
	public void parse() throws MalformedURLException, IOException {
		/* Parses through the page in order to find emailIDs and urls
		 * Adds any found elements to their respective collections
		 */
		InputStream stream = webPage.openStream();
		Scanner pageInput = new Scanner(stream);
		while(pageInput.hasNext()) {
			String webString = pageInput.next();
			Matcher emailMatcher = emailPattern.matcher(webString);
			Matcher URLMatcher = urlPattern.matcher(webString);
			if(emailMatcher.find()) {
				emailParse(webString);
			}
			if(URLMatcher.find()) 
				urlParse(webString);
		}
		stream.close();
	}
}

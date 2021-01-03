
/* TDConsole runs the program in basic console form
 * Asks for an url and puts all emailIDs and urls in collections
 * Prompts the user to pick from the unvisited urls to do another search
 * In the end it prints out all of the urls you visited, collected, and emails collected
 * Jonathan Hopkins
 * 5/1/2020
 * TDConsole
 */

package ConsoleVersion;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import ProgramCode.ParsePage;
import ProgramCode.WebCollection;

public class TDConsole {

	private WebCollection<URL> urlCollection; // Collection for urls found on webPages
	private WebCollection<URL> urlParsedCollection; // Collection for urls found on webPages that have already been parsed
	private WebCollection<String> emailCollection; // Collection for emailIDs found on webPages
	
	public TDConsole() { // Constructor
		urlCollection = new WebCollection<URL>();
		urlParsedCollection = new WebCollection<URL>();
		emailCollection = new WebCollection<String>();
	}
	
	public void printURLChoices() { // Prints out url choices for the user to parse next
		System.out.print("Pick one of these urls to parse next:");
		System.out.println(urlCollection.toString());
		System.out.println("Enter quit to quit program");
	}
	
	public String toString() { // toString prints out the URLs visited, URLs collected, and email collected
		return ("URLs visited:" + urlParsedCollection.toString() +
				"\nURLS Collected:" + urlCollection.toString() +
				"\nEmails Collected:" + emailCollection.toString());
	}
	
	public static void main(String[] args) throws MalformedURLException, IOException {
		/* Main runs the program in basic console version
		 * 
		 */
		System.out.println("Enter url to start collection");
		TDConsole user = new TDConsole();
		Scanner userInput = new Scanner(System.in);
		String userString = userInput.next();
		URL userURL = new URL(userString);
		user.urlCollection.add(userURL);
		
		while(!userString.equals("quit")) {
			userURL = new URL(userString);
			if(user.urlCollection.contains(userURL)) {
				user.urlParsedCollection.add(userURL);
				if(user.urlCollection.contains(userURL)) {
					user.urlCollection.remove(userURL);
				}
				try {
					ParsePage parser = new ParsePage(userURL);
					parser.parse();
					user.urlCollection.addAll(parser.getURLCollection());
					user.emailCollection.addAll(parser.getEmailCollection());
					user.urlParsedCollection.checkDuplicate(user.urlCollection);
				} catch (FileNotFoundException e) {
					System.out.println("URL is broken, contains erros, or is not found. Pick a new one from the list");
				}
			}
			else {
				System.out.println("URL not from the list. Enter a new valid URL");
			}
			user.printURLChoices();
			userString = userInput.next();
		}
		System.out.println(user.toString());
	}
}
	

/* Runs the program in GUI version
 * Asks for an url and puts all emailIDs and urls in collections
 * Prompts the user to pick from the unvisited urls to do another search
 * In the end it prints out all of the urls you visited, collected, and emails collected
 * 
 * Ver 1.04
 * Still some issues with parsing emails and how the GUI reacts to too many links
 * Want to clean up GUI and   implement adaptable GUI sizes
 * 
 * Jonathan Hopkins
 * 5/2/2020
 * TDGui.java
 */

package GuiVersion;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import javax.swing.*;
import ProgramCode.*;

public class TDGui {
	
	private WebCollection<URL> urlCollection; // Collection for urls found on webPages
	private WebCollection<URL> urlParsedCollection; // Collection for urls found on webPages that have already been parsed
	private WebCollection<String> emailCollection; // Collection for emailIDs found on webPages
	
	private JFrame startPage; //First Gui page 
	private JFrame onGoingPage; //Second and repeating Gui page 
	private JFrame resultPage; //Last Gui page
	
	public TDGui() { // Constructor
		urlCollection = new WebCollection<URL>();
		urlParsedCollection = new WebCollection<URL>();
		emailCollection = new WebCollection<String>();
	}
	
	public void startPage() {
		/* Creates the first Gui page of the program
		 * Lets the user enter a URL to be able to parse
		 * Lets the user know if a url entered is not valid
		 */
		startPage = new JFrame("Jon's Final Project: Start Page");
		
		JLabel prompt = new JLabel("Enter Valid URL: ");
		prompt.setFont(new Font("Serif",Font.PLAIN,20));
		
		
		JTextField textField = new JTextField("");
		textField.setPreferredSize(new Dimension(200,20));
		
		JButton enterButton = new JButton("Enter");
		enterButton.addActionListener(new ActionListener() {
			boolean enterError = false;
			public void actionPerformed(ActionEvent e) {
				String userStr = textField.getText();
				try {
					URL userURL = new URL(userStr);
					urlCollection.add(userURL);
					startPage.setVisible(false);
					onGoingPage(userURL);
				}
				catch(MalformedURLException E) {
					if(!enterError) {
						enterError = true;
						startPage.add(new JLabel("Invalid URL, try again"));
						startPage.setVisible(true);
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		startPage.setSize(550,180);
		startPage.add(prompt);
		startPage.add(textField);
		startPage.add(enterButton);
		startPage.setLayout(new FlowLayout(FlowLayout.CENTER));
		startPage.setVisible(true);	
	}
	
	public void onGoingPage(URL targetURL) throws MalformedURLException, IOException {
		/* Creates the on going page of the program
		 * Lets the user chose from the list of urls that have been collected
		 */
		onGoingPage = new JFrame("Jon's Final Project: Ongoing Page");
		//Parsing and collection handling
		if(urlCollection.contains(targetURL)) {
			urlParsedCollection.add(targetURL);
			urlCollection.remove(targetURL);
		}
		try {
			ParsePage parser = new ParsePage(targetURL);
			parser.parse();
			urlCollection.addAll(parser.getURLCollection());
			emailCollection.addAll(parser.getEmailCollection());
			urlParsedCollection.checkDuplicate(urlCollection);
		} catch (IOException e) {
			urlParsedCollection.remove(targetURL);
			System.out.println("URL is broken, contains errors, or is not found. Pick a new one from the list");
		}
		//GUI handling
		//Choice List
		DefaultListModel<String> stringList = new DefaultListModel<String>();
		Scanner stringScan = new Scanner(urlCollection.toString());
		while(stringScan.hasNext()) {
			String addition = stringScan.nextLine();
			stringList.addElement(addition);
		}
		JList<String> choiceList = new JList<String>(stringList);
		//Parse Button
		JButton parseButton = new JButton("Parse Selected Webpage");
		parseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(choiceList.getSelectedIndex() != -1)
					try {
						onGoingPage.setVisible(false);
						onGoingPage(new URL(choiceList.getSelectedValue()));
					} catch (MalformedURLException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
			}
		});
		//Result Button
		JButton resultButton = new JButton("Results");
		resultButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onGoingPage.setVisible(false);
				resultPage();
			}
		});
		onGoingPage.setSize(1400,800);
		onGoingPage.add(choiceList);
		onGoingPage.add(parseButton);
		onGoingPage.add(resultButton);
		
		onGoingPage.setLayout(new FlowLayout(FlowLayout.CENTER));
		onGoingPage.setVisible(true);
	}
	
	public void resultPage() {
		/* Creates the final page of the program
		 * Lists all the emails collected, urls collected, and urls visited
		 */
		resultPage = new JFrame("Jon's Final Project: Result Page");
		//Three Result 
		JTextArea parsedResults = new JTextArea("URLs Visited:" + urlParsedCollection.toString());
		parsedResults.setEditable(false);
		JTextArea urlCollectedResults = new JTextArea("URLs Collected:" + urlCollection.toString());
		urlCollectedResults.setEditable(false);
		JTextArea emailResult = new JTextArea("Emails Collected:" + emailCollection.toString());
		emailResult.setEditable(false);
		
		resultPage.setSize(1400,1400);
		resultPage.add(parsedResults);
		resultPage.add(urlCollectedResults);
		resultPage.add(emailResult);
		
		resultPage.setLayout(new FlowLayout(FlowLayout.LEFT));
		resultPage.setVisible(true);
		System.out.println(toString()); // Prints output in console
	}
	
	public String toString() { // toString prints out the URLs visited, URLs collected, and email collected
		return ("URLs visited:" + urlParsedCollection.toString() +
				"\nURLS Collected:" + urlCollection.toString() +
				"\nEmails Collected:" + emailCollection.toString());
	}
	
	public static void main(String[] args) { // Main initiates and runs the program
		TDGui user = new TDGui();
		user.startPage();
	}

}

/*
URLs visited:
	http://faculty.juniata.edu
	http://jcsites.juniata.edu/faculty/kruse
	http://jcsites.juniata.edu/faculty/kruse/cs240/syllabus.htm
	http://www.si.com
URLS Collected:
	http://cs.illinois.edu/news/cs-illinois-professor-receives-rd-100-award-simulation-software
	http://docs.oracle.com/javase/8/
	http://docs.oracle.com/javase/8/docs/api/
	http://jcsites.juniata.edu/faculty/kruse/attendanceConductProfessionalism.htm
	http://jcsites.juniata.edu/faculty/kruse/ma116/ma116syl.htm
	http://jcsites.juniata.edu/faculty/kruse/ma303/MathModelingSum2016.htm
	http://jcsites.juniata.edu/faculty/kruse/office.htm
	http://jcsites.juniata.edu/faculty/kruse/policies.htm
	http://player.vimeo.com/video/33357253?title=0&amp;byline=0&amp;portrait=0
	http://w3.georgiasouthern.edu/ijsotl/v7n2.html
	http://www-unix.mcs.anl.gov/appliedmath/Flow/noncon.html
	http://www.dmlp.org/legal-guide/recording-phone-calls-and-conversations
	http://www.juniata.edu
	http://www.juniata.edu/about/news/archive.php?action=SHOWARTICLE&id=6649
	http://www.juniata.edu/facultybio/bio.html?id=KRUSE
	http://www.juniata.edu/offices/juniata-voices/media/volume-16/vol16-Kruse.pdf
	http://www.juniata.edu/services/news/?action=SHOWARTICLE&amp;id=5121
	http://www.juniata.edu/services/news/?action=SHOWARTICLE&amp;id=5211
	http://www.juniata.edu/services/news/?action=SHOWARTICLE&amp;id=5219
	http://www.juniata.edu/services/news/index.html?SHOWARTICLE+4866
	http://www.juniata.edu/services/sotl/
	http://www.mathsisfun.com/games/towerofhanoi.html
	http://www.mcs.anl.gov/~fischer/cfd/noncon.html
	http://www.nafsa.org/about/default.aspx?id=16295
	http://www.si2.com
	http://www.youtube.com/watch?v=Cw5MHsO-JI8&sns=em
	http://www.youtube.com/watch?v=FbUul7KxG1k&feature=share&list=UUrk6w8_zZaxKf-S5Jl3Qd-g
	https://cra.org/ccc/
	https://www.youtube.com/watch?v=B1AzIlp1m4E&feature=youtu.be
	https://www.youtube.com/watch?v=OZrXukZraHY&feature=youtu.be
Emails Collected:
	%20kruse@juniata.edu
	Jerry-Kruse@juniata.edu,
	Jerry.Kruse@juniata.edu
	LCKruse@yahoo.com
	abc@juniata.edu
	abc@yahoo.com
	abcd@juniata.edu
	abcd@yahoo.com
	gwk2@cfm.brown.edu
	gwk3@cfm.brown.edu
	gwk@brown.edu
	gwk@cfm.brown.edu
	kruse@juniata.edu
	kruseb@msn.com
*/

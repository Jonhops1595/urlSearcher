package TestCode;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import ProgramCode.SortedArrayCollection;
public class TDCollection {

	private SortedArrayCollection<String> IntegerCollection;
	
	public TDCollection() {
		IntegerCollection = new SortedArrayCollection<String>();
	}
	
	public static int compareStrings(String first, String second) {
		return first.compareTo(second);
	}
	
	
	public static void main(String[] args) throws FileNotFoundException {
		TDCollection user = new TDCollection();
		File file = new File("TestNameFile");
		
		Scanner input = new Scanner(file);
		while (input.hasNext()) {
			String line = input.next();
			user.IntegerCollection.add(line);
		}
		input.close();
		System.out.println(user.IntegerCollection.toString());
		user.IntegerCollection.remove("Abby");
		System.out.println(user.IntegerCollection.toString());
		
	}
}

package ProgramCode;
/* WebCollection is a an extension of the SortedArrayCollection
 * A collection specifically built for the FinalAssignment program 
 * 
 * Why a SortedArrayCollection?
 * I choose to use a SortedArrayCollection since its time for its contains method is O(log base 2 n)
 * Contains() is used a lot throughout this program in order to assure that there are no duplicates in either the primary or the secondary collections
 * I also use the binary search in the add() and remove() to get the indexes of where an element should be added or where the element is that needs to be removed 
 * 
 * Jonathan Hopkins
 * 5/2/2020
 * WebCollection.java
 */
public class WebCollection<T> extends SortedArrayCollection<T> { // Constructor

	public WebCollection() {
		super();
	}
	
	public void addAll(WebCollection<T> secondary) {
		/* Adds all elements not found in the primary collection from the secondary collection
		 * 
		 */
		for(int i = 0;i < secondary.size();i++) {
			if(!this.contains(secondary.elements[i])) {
				this.add(secondary.elements[i]);
			}
		}
	}
	
	public void checkDuplicate(WebCollection<T> secondary) {
		/* Checks if there are any elements in secondary that are in the primary collection
		 * Deletes all duplicate elements in the secondary collection
		 */
		for(int i = 0;i < secondary.size();i++) {
			T element = secondary.elements[i];
			if(this.contains(element)) {
				secondary.remove(element);
				i--;
			}
		}
	}
	
}

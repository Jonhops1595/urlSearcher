package ProgramCode;
/* A Sorted Array Collection that implements the Collection Interface
 * Heavily derived from the book example
 * Jonathan Hopkins
 * 4/28/2020
 * SortedArrayCollection.java
 */
public class SortedArrayCollection<T> implements CollectionInterface<T> {

	private final int DEFCAP = 30; // default capacity of the array
	protected T[] elements; // array to hold the collection's elements
	private int numElements = 0; // number of elements in the collection
	// used by find method
	private boolean found; // true if target is found, otherwise false
	private int location; // index of the target element
	
	public SortedArrayCollection() { // Constructor
		elements = (T[]) new Object[DEFCAP];
	}
	
	public SortedArrayCollection(int size) { //Constructor with parameter for capacity
		elements = (T[]) new Object[size];
	}
	
	private boolean find(T target) {
	// Binary searches elements for an occurrence of an element e such that
	// e.equals(target). If successful, sets instance variables found to true
	// and location to the index of e. If not successful, sets found to false
		location = 0;
		found = false;
		return binarySearch(target, 0, size() -1);
	}
	
	private boolean binarySearch(T target, int first, int last) { // Helper method for find()
		int midpoint = (first + last) / 2;
		if(first > last) {
			location = first;
			return false;
		}
		else
			try {
				if(((Comparable) target).compareTo(elements[midpoint]) == 0) {
					location = midpoint;
					found  = true;
					return true;
				}
				else if(((Comparable) target).compareTo(elements[midpoint]) > 0)
					return binarySearch(target, midpoint + 1, last);
				else
					return binarySearch(target, first, midpoint - 1);	
			}
			catch(ClassCastException e) {
				if(((Comparable) target.toString()).compareTo(elements[midpoint].toString()) == 0) {
					location = midpoint;
					found  = true;
					return true;
				}
				else if(((Comparable) target.toString()).compareTo(elements[midpoint].toString()) > 0)
					return binarySearch(target, midpoint + 1, last);
				else
					return binarySearch(target, first, midpoint - 1);	
			}
	}
	
	
	public boolean add(T element) {
	// Attempts to add element to this collection.
	// Returns true if successful, false otherwise.
		if (isFull())
			enlarge();
		find(element);
		for(int index = size(); index > location; index--)
			elements[index] = elements[index - 1];
		elements[location] = element;
		numElements++;
		return true;
	}
	
	public boolean remove (T target) {
	// Removes an element e from this collection such that e.equals(target)
	// and returns true; if no such element exists, returns false.
		find(target);
		if (found) {
			for(int i = location;i < size();i++) {
				elements[i] = elements[i + 1];
			}
			elements[numElements - 1] = null;
			numElements--;
		}
		return found;
	}
	
	public boolean contains (T target) {
	// Returns true if this collection contains an element e such that
	// e.equals(target); otherwise, returns false.
		find(target);
		return found;
	}
	
	public T get(T target) {
	// Returns an element e from this collection such that e.equals(target);
	// if no such element exists, returns null.
		find(target);
		if (found)
			return elements[location];
		else
			return null;
	}
	
	public boolean isFull() {
	// Returns true if this collection is full; otherwise, returns false.
	return (numElements == elements.length);
	}
	
	public boolean isEmpty() {
	// Returns true if this collection is empty; otherwise, returns false.
	return (numElements == 0);
	}
	
	public int size() {
	// Returns the number of elements in this collection.
	return numElements;
	}
	
	private void enlarge() {
	// Copies over the array and doubles the size when the collection is filled
		T[] enlargedCollection = (T[]) new Object[size() * 2];
		for(int i = 0; i<size(); i++) {
			enlargedCollection[i] = elements[i];
		}
		elements = enlargedCollection;
	}
	
	
	public String toString() { //toString to print all the elements in the collection
		String output = "";
		for(int i = 0;i < size(); i++)
			output = output + "\n\t" + elements[i].toString();
		return output;
	}
	
}

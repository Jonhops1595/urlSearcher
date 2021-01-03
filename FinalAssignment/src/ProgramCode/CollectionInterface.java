package ProgramCode;
/* A Collection Interface to hold objects
 * Heavily derived from book example
 * Jonathan Hopkins
 * 4/28/2020
 * CollectionInterface.java
 */
public interface CollectionInterface<T> {
	
	boolean add(T element);
	/* Attempts to add element to the collection
	 * Returns true if successful, false if fail
	 */

	T get(T target);
	/* Returns an element e from the collection if the target is found (e.equals(target))
	 * Returns null if target element is not found
	 */
	
	boolean contains(T target);
	/* Returns true if target element is contained in the collection
	 * Returns false if target element is not in the collection
	 */
	
	boolean remove(T target);
	/* Removes an element e from the collection if it is contained in the collection
	 * Returns true if removed, returns false if no such element exists
	 */
	
	boolean isFull();
	// Returns true if the collection is full otherwise, returns false
	
	boolean isEmpty();
	// Returns true if the collection contains no elements otherwise, returns false
	
	int size();
	// Returns the amount of elements contained in the collection
}

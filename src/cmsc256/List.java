/**
 *   Debra Duke  
 *   Computer Science Department
 *   College of Engineering
 *   Virginia Commonwealth University
 */
package cmsc256;


public interface List<E> { 
  
    /**
     * 	Remove all contents from the list, so it is once again empty
     */
    public void clear();

   
    /**
     * 	Inserts element at the given position 
     * @param 	it
     * @param 	position
     * @return	true if successful
     * @throws IllegalArgumentException if position is < 0 or > number of elements in the list
     */
    public boolean insert(E it, int position);

    // 
    /**
     * 	Appends element to the end of the list
     * @param 	it
     * @return	true if successful
     */
    public boolean add(E it);

    
    /**
     * @param 	position
     * @return	Remove and return the element at position
     * @throws IllegalArgumentException if position is < 0 or > number of elements in the list
     */
    public E remove(int position);

 
    /**
     * @return	the number of elements in this list
     */
    public int size();
    
    
    /**
     * @return	true if this list has no elements
     */
    public boolean isEmpty();

    
    /**
     * @param 	target
     * @return 	true if the target element is in this list
     * 			false otherwise
     */
    public boolean contains(E target);


    /**
     * @return	the element at the given position
     * @throws IllegalArgumentException if position is < 0 or > number of elements in the list
     */
    public E getValue(int position);
}
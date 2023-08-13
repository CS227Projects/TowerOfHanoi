
public class Ring {
	private int size;
	
	/*
	 * @param size
	 * the ring size. Used to determine whether or not a ring can be placed on another
	 */
	public Ring(int size) {
		this.size = size;
	}
	
	/*
	 * @returns the size of a ring
	 */
	public int getSize() {
		return size;
	}
	
	/*
	 * @param r
	 * the ring we want to compare to
	 * returns true if the current ring is smaller than the parameter ring
	 * used to determine if a stack can be validly made
	 */
	public boolean isSmallerThan(Ring r) {
		return this.getSize() < r.getSize();
	}
	
	/*
	 * Prints the size and nothing else. Useful for printing the whole tower in the Towers class
	 */
	public String toString() {
		return "" + size;
	}
}

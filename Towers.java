import java.util.ArrayList;
public class Towers {
	private static int moves = 0;
	private ArrayList<Ring> towerOne = new ArrayList<Ring>();
	private ArrayList<Ring> towerTwo = new ArrayList<Ring>();
	private ArrayList<Ring> towerThree = new ArrayList<Ring>();
	private ArrayList<ArrayList<Ring>> towers = new ArrayList<ArrayList<Ring>>();
	private int ringCount;
	
	/*
	 * @param count
	 * the amount of rings to be used in the game. 
	 * all rings start on towerOne, or the farthest left tower.
	 * 
	 * towerOne-towerThree are arraylists of rings with the smallest on top
	 * 
	 *  towers is an array of all the towers, 
	 *  which is used to iterate through the towers in the solver and move methods
	 */
	public Towers(int count) {
		ringCount = count;
		for (int i = count; i > 0; i--) {
			Ring x = new Ring(i);
			towerOne.add(x);
		}
		towers.add(towerOne);
		towers.add(towerTwo);
		towers.add(towerThree);
	}
	
	/*
	 * in order to win all rings must be on the right most tower
	 * in numerical order with smallest on top.
	 * 
	 * Since the move method won't allow stacking in non-ascending order 
	 * as long as all rings are on the right (aka towerThree's height = total ring count)
	 * it can be assumed they are in correct order
	 */
	public boolean checkWin() {
		return ringCount == towerThree.size();
	}
	
	/*
	 * @param tower
	 * the stack of rings we want to find the top ring of
	 * 
	 * returns the size of the top-most (or last in the array) 
	 * ring of the param tower
	 */
	public int getTop(ArrayList<Ring> tower) {
		int last = tower.size()-1;
		if (last < 0) {
			return -1;
		}
		return tower.get(last).getSize();
	}
	
	/*
	 * @param currentLoc
	 * the tower we want to take the top ring off
	 * 
	 * @param newLoc 
	 * the tower we want to give a ring to
	 * 
	 * makes sure that the tower we're taking from has a ring to give.
	 * then finds the size of the top of both the giving tower and the receiving tower
	 * 
	 * makes sure the receiving tower's top ring has a larger ring size before transferring
	 * 
	 * @return true if the movement was successful
	 * 
	 * @return false if the sizing makes the exchange invalid
	 */
	private boolean move(ArrayList<Ring> currentLoc, ArrayList<Ring> newLoc) {
		int topIndex = getTop(currentLoc);
		if (topIndex == -1) {
			return false;
		}
		int newTopIndex = getTop(newLoc);
		if (newTopIndex == -1) {
			newLoc.add(currentLoc.get(topIndex));
			currentLoc.remove(topIndex);
			moves ++;
			return true;
		}
		if (currentLoc.get(topIndex).isSmallerThan(newLoc.get(newTopIndex))) {
			newLoc.add(currentLoc.get(topIndex));
			currentLoc.remove(topIndex);
			moves ++;
			return true;
		}
		return false;
	}
	
	/*
	 * @param currentLoc
	 * the index of the giving tower in the towers ArrayList
	 * 
	 * @param newLoc
	 * the index of the receiving tower in ArrayList
	 * 
	 * method allows the use of indices rather than tower arraylist objects
	 *  to make the move method easier to use
	 */
	public boolean move(int currentLoc, int newLoc) {
		return move(towers.get(currentLoc), towers.get(newLoc));
	}
	
	/*
	 * returns the amount of moves that have been performed
	 */
	public int moveCount() {
		return moves;
	}
	
	/*
	 * Algorithm for solving:
	 * Two step move process - if there's an odd number of rings, every other move shifts the smallest ring one tower to the left
	 * if even ring count the smallest ring is shifted to the right one tower
	 * 
	 * After moving the first ring there's only one other valid move between the two towers
	 * The method takes the two towers it knows doesn't contains the smallest ring and tries moving them to each other
	 * if the first move method attempt works the second won't be initiated to prevent them canceling each other out
	 * 
	 * repeats until checkWin is true, meaning all rings stacked in right-most tower
	 */
	public void solver() {
		boolean odd = ringCount % 2 == 1;
		//determines a right shift (+1) or left shift (+2) based on even or odd
		int shift = 1;
		if (odd) {
			shift = 2;
		}
		while (!checkWin()) {
			for (int i = 0; i < 3; i++) {
				//determines if the top of the tower at index i is the smallest ring (1)
				int top = getTop(towers.get(i));
				if (top == 1) {
					// the index after a +1 or -1 shift (i+2)%3 = -1 and makes sure we dont go out of index
					int smallRingTower = (i+shift)%3;
					move(i, smallRingTower);
					System.out.println(this);
					//since we know the smallestRing index we use the indices left and right
					// moving one of these towers onto the other has to be the only other valid move
					int otherOne = (smallRingTower+2)%3;
					int otherTwo = (smallRingTower+1)%3;	
					//tries one option, and if it works doesn't try other to prevent canceling out.
					// if it fails the other is tried
					if(!move(otherOne, otherTwo)) {
						move(otherTwo, otherOne);
					}
					System.out.println(this);
					break;
					// break out of loop to prevent unwanted movement of 1 ring again
				}
			}
		}
	}
	/*
	 * Loops in reverse so the smallest values are on top.
	 * Prints the values of each tower at index i with a | dividing them
	 * Prints index labels underneath each tower.
	 */
	public String toString() {
		String response = "";
		for (int i = ringCount-1; i >= 0; i--) {
			if (towerOne.size() > i) {
				response += towerOne.get(i);
			}
			else {
				response += " ";
			}
			response += " | ";
			if (towerTwo.size() > i) {
				response += towerTwo.get(i);
			}
			
			else {
				response += " ";
			}
			response += " | ";
			
			if (towerThree.size() > i) {
				response += towerThree.get(i);
			}
			
			else {
				response += " ";
			}
			response += " | ";
			
			response += "\n";
		}
		for (int i = 0; i < 3; i++) {
			response += i + "---";
		}
		response += "\n Moves used: " + moves + "\n";
		return response;
	}
	
}

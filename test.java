import java.util.Scanner;
public class test {
	public static void main(String args[]){
		Towers game = new Towers(10);
		Scanner scan = new Scanner(System.in);
		System.out.println("Play game or use a solver? (g/s)");
		String response = scan.next();
		if (response.equals("g")) {
			while(!game.checkWin()) {
				System.out.println(game);
				boolean valid = false;
				while (!valid) {
					System.out.println("Which column would you like to move a ring from:");
					int currentLoc = scan.nextInt();
					System.out.println("Which column would you like to move it to?");
					int newLoc = scan.nextInt();
					valid = game.move(currentLoc, newLoc);
					if (!valid) {
						System.out.println("Invalid move!");
						System.out.println(game);
					}
				}
			}
			System.out.println("You won in " + game.moveCount() + " moves!");
			System.out.println(game);
		}
		
		else {
			game.solver();
		}
		scan.close();
		
	}
}

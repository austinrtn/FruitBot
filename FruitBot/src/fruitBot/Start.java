// NAME: FruitBot
// AUTHOR: Austin Kolongowski
// VERSION: 1.3

package fruitBot;

import java.util.Scanner;

public class Start {

	/*
	 * The start class contains the main method. This is where all of the user input
	 * is processed. The program is not limited to fruits, as vegetables can be
	 * entered as well and Fruit objs can contain information for vegetables.
	 */

	public static final String FILE_NAME = "./FruitMemory.txt"; // File name of where the database is stored
	private static boolean runProgram = true; // Sentinel for loop
	private static FileManage fileManager = new FileManage();
	private static Scanner input = new Scanner(System.in);

	private String name = ""; // Name of userFruit
	private String color = null; // Color of userFruit
	private String shape = null; // Shape of userFruit
	private boolean seeds = false; // If userFruit has seeds
	private int weight = 0; // weight of userFruit

	private Fruit userFruit; // the Fruit obj that is built from the user input
	private Fruit askFruit; // the Fruit that the computer guesses based off of user input

	// Main method, also contains the loop for user input.
	public static void main(String[] args) {
		Start run = new Start();

		System.out.println("Hello, I am FruitBot! I like to learn about fruit and vegetables.");

		while (runProgram) {
			System.out.println("\nEnter guess to get started, or enter exit to close the program.");

			String cmd = input.next();

			if (cmd.equalsIgnoreCase("guess"))
				run.guess();
			else if (cmd.equalsIgnoreCase("exit"))
				runProgram = false;
			else
				System.out.println("Sorry, that is incorrect input");
		}
	}

	private void guess() {
		inputFruit(); // Gets the user input and creates a Fruit (userFruit) obj based on the
						// entered information
		guessFruit(); // Takes userFruit and compares it to previously entered data to return the best
						// matching Fruit obj
		exitMsg();
	}

	private void inputFruit() {
		System.out.println("\nDescribe the fruit/vegtable.");
		System.out.println("Color: ");
		color = input.next();
		System.out.println("Shape: ");
		shape = input.next();
		System.out.println("Does it have seeds?[Y/N]");
		String seed = input.next();
		if (seed.equalsIgnoreCase("y") || seed.equalsIgnoreCase("yes"))
			seeds = true;

		System.out.println("Weight(grams): ");
		weight = Integer.parseInt(input.next());

		userFruit = new Fruit(name, color, shape, seeds, weight);
		askFruit = fileManager.run(userFruit);

	}

	private void guessFruit() {
		// Guesses fruit
		System.out.println("Is your fruit a " + askFruit.getName() + "[Y/N]");
		String answer = input.next();

		// If FruitBot guesses correctly, it will take the name of the Fruit that it
		// guessed and add it to userFruit.
		if (answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("yes"))
			userFruit.setName(askFruit.getName());

		// If FruitBot guesses incorrectly, it will ask you which fruit was the user
		// describing. A list of all the names of all the fruit in the database is
		// printed out. If the name of the described fruit has never been entered into
		// the database, the user will be asked to enter the name of the fruit.

		else {
			System.out.println("\nWhich fruit/vegtable is it?\n");
			int i;
			for (i = 0; i < FileManage.getNameList().size(); i++)
				System.out.println("[" + i + "] " + FileManage.getNameList().get(i));

			System.out.println("[" + i + "] new Fruit");
			int indexOfFruitList = input.nextInt();

			if ((indexOfFruitList < FileManage.getNameList().size()))
				userFruit.setName(FileManage.getNameList().get(indexOfFruitList));

			else {
				System.out.println("What is the name of the fruit?");
				String nameFruit = input.next();
				nameFruit = nameFruit.toLowerCase();
				userFruit.setName(nameFruit);
			}
		}
		fileManager.writeFile(userFruit);
	}

	private void exitMsg() {

		System.out.println("\nThank you for teaching me fruits!\nWould you like to exit? [Y/N]");
		String exit = input.next();
		if (exit.equalsIgnoreCase("y") || exit.equalsIgnoreCase("yes")) {
			input.close();
			runProgram = false;
		} else
			System.out.println("\n\n");
	}

}

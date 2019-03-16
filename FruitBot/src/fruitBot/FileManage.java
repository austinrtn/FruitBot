package fruitBot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class FileManage {

	/*
	 * 
	 */

	private static String fileName = Start.FILE_NAME;
	private static ArrayList<Fruit> fruitList; // List of Fruit objs from database
	private static ArrayList<String> nameList = new ArrayList<String>(); // List of names of fruit

	public static ArrayList<String> getNameList() {
		return nameList;
	}

	public Fruit run(Fruit userFruit) {
		fruitList = new ArrayList<Fruit>();
		readFile();
		compare(userFruit);

		return getRatedFruit();
	}

	private void readFile() {
		Scanner fileReader = null;

		try { // Creates Scanner to read the database txt file
			fileReader = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		//////////////////////////
		// Build Fruit Ojbs from DB
		//////////////////////////

		/*
		 * Fruit objs are built from the database by searching for syntax within the
		 * FruitMemory file.
		 */

		String name = null;
		String color = null;
		String shape = null;
		boolean seeds = false;
		int weight = 0;

		while (fileReader.hasNext()) {
			String str = fileReader.next();

			boolean endObj = false;
			if (str.contains(";")) { // if str contains a semicolon, the obj can be built.
				endObj = true;
				str = str.replace(";", "");
			}

			if (str.contains("N:")) { // Name
				str = str.replace("N:", "");
				name = str;
			} else if (str.contains("C:")) { // Color
				str = str.replace("C:", "");
				color = str;
			} else if (str.contains("S:")) { // Shape
				str = str.replace("S:", "");
				shape = str;
			} else if (str.contains("B:")) { // has seeds
				if (str.contains("true"))
					seeds = true;
			} else if (str.contains("W:")) { // Weight
				str = str.replace("W:", "");
				weight = Integer.parseInt(str);
			}

			if (endObj) { // Adds Fruits and Names of fruits to corresponding list.
				Fruit f = new Fruit(name, color, shape, seeds, weight);
				fruitList.add(f);
				if (nameList.contains(f.getName()) == false)
					nameList.add(f.getName());
			}
		}
		fileReader.close();
	}

	private void compare(Fruit fruit) {
		/*
		 * The compare method compares each Fruit from the fruitList to the Fruit obj
		 * that the user has entered (Start.userFruit). The more similar the Fruit obj
		 * from fruitList is to userFruit, the higher it is rated. The highest rated
		 * fruit is returned.
		 */
		for (int i = 0; i < fruitList.size(); i++) {
			int rate = 0;
			Fruit compFruit = fruitList.get(i);
			// Compare color()
			if (fruit.getColor().equalsIgnoreCase(compFruit.getColor()))
				rate += 100;
			// Compare shape()
			if (fruit.getShape().equalsIgnoreCase(compFruit.getShape()))
				rate += 100;
			// Compare seeds()
			if (fruit.hasSeeds() == compFruit.hasSeeds())
				rate += 100;
			// compare weight
			int weight = fruit.getWeight() - compFruit.getWeight();
			if (weight < 0)
				weight *= -1;
			rate += 100 - weight;

			compFruit.setRating(rate);
		}
	}

	private Fruit getRatedFruit() {
		Fruit fruitReturn = fruitList.get(0); // The fruit that will be returned

		for (int x = 1; x < fruitList.size(); x++) {
			Fruit fruit2 = fruitList.get(x); // The fruit that is being compared
			if (fruitReturn.getRating() < fruit2.getRating())
				fruitReturn = fruit2; // If the rating for fruit2 is higher than fruitReturn, fruitReturn equals
										// fruit2
										
		}
		return fruitReturn;
	}

	// Enters Fruit obj to FruitMemory(database)
	public void writeFile(Fruit userFruit) {
		try (FileWriter fw = new FileWriter(fileName, true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw)) {

			out.println(formatFruit(userFruit));

		} catch (IOException e) {
			System.out.println(e);
		}
	}

	// Takes Fruit objs and 'translates' them to Strings with the correct syntax for the database
	private String formatFruit(Fruit fruit) {
		String str = "";
		str += "\nN:" + fruit.getName() + " ";
		str += "C:" + fruit.getColor() + " ";
		str += "S:" + fruit.getShape() + " ";
		str += "B:" + fruit.hasSeeds() + " ";
		str += "W:" + Integer.toString(fruit.getWeight()) + " ";
		str += ";";

		return str;
	}

}

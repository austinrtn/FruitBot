package fruitBot;

public class Fruit {

	/*
	 * The Fruit class is where Fruit objects created (vegetables are welcome too).
	 * There are 6 attributes of a Fruit obj, Name, Color, Shape, If it has seeds,
	 * its weight in grams, and its rating. The rating of a fruit is determined in
	 * the FileManage class by creating a list of the Fruit obj in the database and
	 * comparing each fruit to the userFruit obj in the start class. The more
	 * similar the Fruit obj is to userFruit, the higher the rating
	 */

	private String name;
	private String color;
	private String shape;
	private boolean seeds;
	private int weight; // in grams

	private int rating;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getColor() {
		return color;
	}

	public String getShape() {
		return shape;
	}

	public boolean hasSeeds() {
		return seeds;
	}

	public int getWeight() {
		return weight;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public int getRating() {
		return rating;
	}

	public Fruit(String name, String color, String shape, boolean seeds, int weight) {
		this.name = name.toLowerCase();
		this.color = color.toLowerCase();
		this.shape = shape.toLowerCase();
		this.seeds = seeds;
		this.weight = weight;
	}

}
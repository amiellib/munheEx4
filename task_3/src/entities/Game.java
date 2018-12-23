package entities;

import java.util.ArrayList;

/**
 * 
 * @author Shilo Gilor and Amiel Liberman
 *
 */
public class Game {

	ArrayList<Fruit> fruit_list = new ArrayList<Fruit>();
	ArrayList<Packman> packman_list = new ArrayList<Packman>();
	double speed_rate = 10;
	/**
	 * get friut_list
	 * @return fruit_list
	 */
	public ArrayList<Fruit> getFruit_list() {
		return fruit_list;
	}
	/**
	 * get packman_list
	 * @return packman_list
	 */
	public ArrayList<Packman> getPackman_list() {
		return packman_list;
	}
	/**
	 * get speed rate
	 * @return speed_rate
	 */
	public double getSpeed_rate() {
		return speed_rate;
	}

	/**
	 * 
	 * @param fruit_list is the games fruit list
	 */
	public void setFruit_list(ArrayList<Fruit> fruit_list) {
		this.fruit_list = fruit_list;
	}
	/**
	 * @param packman_list is the games packmans list
	 */
	public void setPackman_list(ArrayList<Packman> packman_list) {
		this.packman_list = packman_list;
	}

	/**
	 * 
	 * @param speed_rate is the speed rate that the display of packman movement
	 */
	public void setSpeed_rate(double speed_rate) {
		this.speed_rate = speed_rate;
	}
	/**
	 * copy the Game and return it 
	 * @return copy of Game
	 */
	public Game copy()
	{
		Game temp_game = new Game();
		for (Packman this_packman : packman_list)
		{
			temp_game.packman_list.add(new Packman (this_packman.getPackman_id() ,this_packman.getGps() , this_packman.getSpeed() ,this_packman.getRange()) );
		}
		for (Fruit this_fruit : fruit_list)
		{
			temp_game.fruit_list.add(new Fruit (this_fruit.getFruit_id() , this_fruit.getGps() , this_fruit.getWeight()));
		}	
		return temp_game;
	}
	/**
	 * @return string of Game info
	 */
	@Override
	public String toString() {
		return "Game [fruit_list=" + fruit_list + ", packman_list=" + packman_list + ", speed_rate=" + speed_rate + "]";
	}
	


	
}

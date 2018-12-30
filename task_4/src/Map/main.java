package Map;
import java.io.IOException;
import javax.swing.JFrame;

import Geom.Point3D;
import entities.Map;

public class main
{
	public static void main(String[] args) throws IOException
	{
		
		Map map = new Map(new Point3D (32.101898 , 35.202369 , 0) , new Point3D (32.105728 , 35.212416 , 0), "src/resources/Ariel1.png");	
		GUI_Map game = new GUI_Map(map);
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.setSize(1433,642);
		game.setVisible(true);
		game.setLocationRelativeTo(null);
	}
}

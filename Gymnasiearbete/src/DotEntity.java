import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;

public class DotEntity extends Entity{
	int nrOfDots = 0;
	public DotEntity(Image image) {
		super(image);

		Random r = new Random();
		int Lowx =  100;
		int Highx = 1340;
		int Resultx = r.nextInt(Highx-Lowx) + Lowx;

		int Lowy = 100;
		int Highy = 700;
		int Resulty = r.nextInt(Highy-Lowy) + Lowy;

		xPos = Resultx;
		yPos = Resulty;

	}

	//update

	@Override
	public void move(long deltaTime) {
		// TODO Auto-generated method stub

	}



}

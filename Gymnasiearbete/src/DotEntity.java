import java.awt.Image;
import java.util.Random;


public class DotEntity extends Entity{
	public DotEntity(Image image) {
		super(image);

		Random r = new Random();
		int Lowx =  100;
		int Highx = 1340;
		int Resultx = r.nextInt(Highx-Lowx) + Lowx;

		int Lowy = 100;
		int Highy = 600;
		int Resulty = r.nextInt(Highy-Lowy) + Lowy;

		xPos = Resultx;
		yPos = Resulty;

	}



	@Override
	public void move(long deltaTime) {
		// TODO Auto-generated method stub

	}



}

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;

public class TextScreen extends Entity{
	
	private ShipEntity ship;  


	public TextScreen(ShipEntity ship) {
		super(null);
		this.ship = ship;
		// TODO Auto-generated constructor stub
	}
	

	@Override
	public void move(long deltaTime) {
		// TODO Auto-generated method stub
		
	}
	
	public void draw(Graphics2D g) {
		g.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 100));
    	g.drawString("Gameover", 500, 300);
        g.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 70));
    	g.drawString("Score: " + (ship.missileArray.size() + ship.getCollisionCheckpowerup()), 565, 500);
    	
        g.setColor(Color.RED);
        g.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 80));
    	g.drawString("Press Enter to play again", 280, 600);
    }
	

}

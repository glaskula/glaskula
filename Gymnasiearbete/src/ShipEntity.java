import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class ShipEntity extends Entity{

	public MissileEntity missile = null;

	public ShipEntity (Image image, double xPos, double yPos, int speed){
		super(image, xPos, yPos, speed);
	}

	public void draw(Graphics2D g){
	    if(missile != null && missile.getActive()){
	        missile.draw(g);   
	    }
	    super.draw(g);
	}
	
	public boolean tryToFire(){
		if(missile == null || !missile.getActive()){
			missile = new MissileEntity(new ImageIcon("images/missile.png").getImage().getScaledInstance(90, 90,Image.SCALE_DEFAULT), xPos-11, yPos, 10);
			missile.setActive(true);
			return true;
		}
		else {
			return false;
		}
	}

	public void move(long deltaTime) {
        if(missile != null && missile.getActive()){
            missile.move(deltaTime);
        }

		
		double speedFactor = 1;

		if(dx != 0 && dy != 0) {
			speedFactor = 1.41;
		}
		
		xPos += dx*(deltaTime/1000000018.0)*speed/speedFactor;
		yPos += dy*(deltaTime/1000000018.0)*speed/speedFactor;
	}


}

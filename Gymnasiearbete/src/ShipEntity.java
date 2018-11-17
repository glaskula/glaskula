import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class ShipEntity extends Entity{

	public MissileEntity missile = null;

	public ArrayList<MissileEntity> missileArray = new ArrayList<>();

	public ShipEntity (Image image, double xPos, double yPos, int speed){
		super(image, xPos, yPos, speed);
	}

	public void draw(Graphics2D g){
		if(!missileArray.isEmpty()) {
			for(int i = 0; i < missileArray.size(); i++)
				missileArray.get(i).draw(g);
		}
		super.draw(g);
	}

	public boolean tryToFire(){
		missile = new MissileEntity(new ImageIcon("images/missile.png").getImage(), xPos-11, yPos, 10);
		missile.setActive(true);

		missileArray.add(missile);

		return true;
	}
	
    public void checkCollisionWhithMissiles(){
    	if(!missileArray.isEmpty()) {
			for(int i = 0; i < missileArray.size(); i++) {
				if(collision(missileArray.get(i))) {
					missileArray.get(i).dx = missileArray.get(i).dx * -1;
					missileArray.get(i).dy = missileArray.get(i).dy * -1;
				}
			}
		}

    }
    
    

	public void move(long deltaTime) {
		if(!missileArray.isEmpty()) {
			for(int i = 0; i < missileArray.size(); i++)
				missileArray.get(i).move(deltaTime);
		}


		double speedFactor = 1;

		if(dx != 0 && dy != 0) {
			speedFactor = Math.sqrt(2);
		}

		xPos += dx*(deltaTime/1000000018.0)*speed/speedFactor;
		yPos += dy*(deltaTime/1000000018.0)*speed/speedFactor;
	}


}

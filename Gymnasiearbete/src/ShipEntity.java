import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class ShipEntity extends Entity{

	public MissileEntity missile = null;  
	private int powerup = 0;
	private int collisionCheck = 1;
	private int collisionCheckpowerup = 0;
	private Image hud1;
	private Image hud2;

	public int getCollisionCheckpowerup() {
		return collisionCheckpowerup;
	}

	public void setCollisionCheckpowerup(int collisionCheckpowerup) {
		this.collisionCheckpowerup = collisionCheckpowerup;
	}

	public int getCollisionCheck() {
		return collisionCheck;
	}

	public void setCollisionCheck(int collisionCheck) {
		this.collisionCheck = collisionCheck;
	}



	public ArrayList<MissileEntity> missileArray = new ArrayList<>();


	public ShipEntity (Image image, double xPos, double yPos, int speed){
		super(image, xPos, yPos, speed);
	}

	public void draw(Graphics2D g){

		hud1 = new ImageIcon("images/Hud1.png").getImage();
		hud2 = new ImageIcon("images/Hud2.png").getImage();
		
		if(!missileArray.isEmpty()) {
			for(int i = 0; i < missileArray.size(); i++)
				missileArray.get(i).draw(g);
		}

		if(powerup == 1) {
			g.drawImage(hud2, 0, 706, null);
		}
		else g.drawImage(hud1, 0, 706, null);

		super.draw(g);
	}

	public boolean tryToFire(){
		missile = new MissileEntity(new ImageIcon("images/missile.png").getImage(), xPos, yPos, 10);
		missile.setActive(true);

		missileArray.add(missile);

		return true;
	}

	public void checkCollisionWhithMissiles(){
		if(!missileArray.isEmpty()) {
			for(int i = 0; i < missileArray.size(); i++) {
				if(collision(missileArray.get(i)) && powerup == 1) {
					missileArray.remove(missileArray.get(i));
					powerup = 0;
					collisionCheckpowerup++;
				}
				else if (collision(missileArray.get(i)) && powerup == 0){
					collisionCheck = 0;
				}
			}
		}

	}

	public int getPowerup() {
		return powerup;
	}

	public void setPowerup(int powerup) {
		this.powerup = powerup;
	}

	public void powerupcheck(){
		if(powerup < 1)
			powerup++;
	}

	public void move(long deltaTime) {
		if(!missileArray.isEmpty()) {
			for(int i = 0; i < missileArray.size(); i++)
				missileArray.get(i).move(deltaTime);
		}

		double speedFactor = 1;

		if(dx != 0 && dy != 0) {
			speedFactor = 1.41;
		}

		xPos += dx*(deltaTime/1000000018.0)*speed/speedFactor;
		yPos += dy*(deltaTime/1000000018.0)*speed/speedFactor;
	}


}

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;

public class MissileEntity extends Entity {
    	
	
    public MissileEntity(Image image, double xPos, double yPos, int speed) {
        super(image, xPos, yPos, speed);
        
        dx = 0;
        dy = 0;
        
        while(dy == 0 && dx == 0) {
	        Random r = new Random();
	        int Lowx = -1;
	        int Highx = 2;
	        int Resultx = r.nextInt(Highx-Lowx) + Lowx;
	        
	        int Lowy = -1;
	        int Highy = 2;
	        int Resulty = r.nextInt(Highy-Lowy) + Lowy;
	        
	        if(dy == 0 && dx == 0) {
	
	        }
	        
	    	dy = Resulty;
	    	dx = Resultx;
	        
	        this.setActive(false);
        }
    }

    @Override
    public void move(long deltaTime) {
    	double speedFactor = 1;
        yPos += dy*(deltaTime/11114991.0)*speed/speedFactor;
        xPos += dx*(deltaTime/11114991.0)*speed/speedFactor;
        
		if(dx != 0 && dy != 0) {
			speedFactor = 1.41;
		}
        
        if (yPos >= 710 || yPos <= -5) {
        	dy = dy * -1;
        }
        if (xPos >= 1360 || xPos <= 0) {
        	dx = dx * -1;
        }
        
    }
    
}

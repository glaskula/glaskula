import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;

public class MissileEntity extends Entity {
    	
	
    public MissileEntity(Image image, double xPos, double yPos, int speed ) {
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
	        	
	        int spawnlocationX = 0;
	        int spawnlocationY = 0;
	        
	        if(Resulty == 1 && Resultx == 0) {
	        	spawnlocationX = 0;
	        	spawnlocationY = 100;
	        }
	        else if(Resulty == 1 && Resultx == 1) {
	        	spawnlocationX = 100;
	        	spawnlocationY = 100;
	        }
	        else if(Resulty == 0 && Resultx == 1) {
	        	spawnlocationX = 100;
	        	spawnlocationY = 0;
	        }
	        else if(Resulty == -1 && Resultx == 1) {
	        	spawnlocationX = 100;
	        	spawnlocationY = -100;
	        }
	        else if(Resulty == -1 && Resultx == 0) {
	        	spawnlocationX = 0;
	        	spawnlocationY = -100;
	        }
	        else if(Resulty == -1 && Resultx == -1) {
	        	spawnlocationX = -100;
	        	spawnlocationY = -100;
	        }
	        else if(Resulty == 0 && Resultx == -1) {
	        	spawnlocationX = -100;
	        	spawnlocationY = 0;
	        }
	        else if(Resulty == 1 && Resultx == -1) {
	        	spawnlocationX = -100;
	        	spawnlocationY = 100;
	        }
	        
	    	dy = Resulty;
	    	dx = Resultx;
	        
	    	
	    	this.xPos = xPos + spawnlocationX;
	    	this.yPos = yPos + spawnlocationY;
	    	
	    	
        }
    }

    @Override
    public void move(long deltaTime) {
    	double speedFactor = 1;
        
		if(dx != 0 && dy != 0) {
			speedFactor = 1.41;
		}
        
        yPos += dy*(deltaTime/11114991.0)*speed/speedFactor;
        xPos += dx*(deltaTime/11114991.0)*speed/speedFactor;
        
        
        if (yPos >= 710 || yPos <= -5) {
        	dy = dy * -1;
        }
        if (xPos >= 1360 || xPos <= 0) {
        	dx = dx * -1;
        }
      
    }


    
}

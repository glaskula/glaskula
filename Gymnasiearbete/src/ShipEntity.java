import java.awt.Image;

import javax.swing.ImageIcon;

public class ShipEntity extends Entity{
	
	public MissileEntity missile = null;

 	public ShipEntity (Image image, double xPos, double yPos, int speed){
      	super(image, xPos, yPos, speed);
 	}
 	
    public boolean tryToFire(){
        if(missile == null || !missile.getActive()){
            missile = new MissileEntity(new 
             ImageIcon("images/missile.png").getImage(), xPos+13, yPos, 10);
            missile.setActive(true);
            return true;
        }else
            return false;
    }
 
 	public void move(long deltaTime){
 	    xPos += dx*(deltaTime/1000000000.0)*speed;
 	}


}

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class GameView extends JFrame implements KeyListener{  
      
    Scanner keyboard = new Scanner(System.in);
    private Canvas gameCanvas = new Canvas();  
     
    private BufferStrategy backBuffer;
  
    private HashMap<String, Boolean> keyDown = new HashMap<>();  
   
      
    private Image hud1;
    private Image hud2;
    private Image Background;
	private int ammo = 0;
    

	public int getAmmo() {
		return ammo;
	}

	public void setAmmo(int ammo) {
		this.ammo = ammo;
	}

	public GameView(){  
        super("Ett välstrukturerat och extremt bra gymnasiearbete");   
        addKeyListener(this);  
  
        keyDown.put("left", false);  
        keyDown.put("right", false); 
        keyDown.put("space", false);
        keyDown.put("up", false);
        keyDown.put("down", false);
        keyDown.put("enter", false);
        keyDown.put("escape", false);
        

		Background = new ImageIcon("images/Background.jpg").getImage();
		hud1 = new ImageIcon("images/Hud1.png").getImage();
		hud2 = new ImageIcon("images/Hud2.png").getImage();

        createWindow();   
    }  
    
    public void BackgroundSound() {
    	File sound = new File("Sound/BackgroundSound.wav");
  	      try{
  	         new Thread(){
  	            public void run(){
  	          	PlaySound(sound);
  	            }
  	         }.start();
  	      }catch(Throwable e){
  	         e.printStackTrace();
  	      }
    }
    
    public void MissileSound() {
    	File sound = new File("Sound/MissileSound.wav");
  	      try{
  	         new Thread(){
  	            public void run(){
  	          	PlaySound(sound);
  	            }
  	         }.start();
  	      }catch(Throwable e){
  	         e.printStackTrace();
  	      }
    }
    
    public void DotSound() {
    	File sound = new File("Sound/DotSound.wav");
  	      try{
  	         new Thread(){
  	            public void run(){
  	          	PlaySound(sound);
  	            }
  	         }.start();
  	      }catch(Throwable e){
  	         e.printStackTrace();
  	      }
    }
      
    public void createWindow(){  
    	gameCanvas = new Canvas();
    	gameCanvas.setSize(1440, 800);
        gameCanvas.setFocusable(false);  
  
        this.add(gameCanvas);  
        this.setMinimumSize(new Dimension(1440, 800));
        this.pack(); 
        this.setVisible(true);  
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);  
		this.setResizable(false);
        gameCanvas.createBufferStrategy(2); 
        backBuffer = gameCanvas.getBufferStrategy();
    }  
  
    public void render(ShipEntity ship,  ArrayList<DotEntity> dotList, ArrayList<PowerUpEntity> powerupList){  
    	
        Graphics2D g = (Graphics2D)backBuffer.getDrawGraphics();
        
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
        
        g.drawImage(Background, 0, 0, null);
        ship.draw(g);
        if(ship.getPowerup() == 1) {
        	g.drawImage(hud2, 0, 706, null);
        }
        else g.drawImage(hud1, 0, 706, null);
        
    	if(!dotList.isEmpty()) {
    		for(int i = 0; i < dotList.size(); i++)
    			dotList.get(i).draw(g);
    	}
    	
      	if(!powerupList.isEmpty()) {
    		for(int i = 0; i < powerupList.size(); i++)
    			powerupList.get(i).draw(g);
    	}
    	
        g.setColor(Color.BLACK);
        g.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 80));
    	g.drawString(ammo + "/3", 1300, 780);
        g.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 60));
    	g.drawString("Score: " + (ship.missileArray.size() + ship.getCollisionCheckpowerup()), 60, 770);
    	
    	if(ship.getCollisionCheck() == 0) {
            g.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 100));
        	g.drawString("Gameover", 500, 300);
            g.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 70));
        	g.drawString("Score: " + (ship.missileArray.size() + ship.getCollisionCheckpowerup()), 565, 500);
        	
            g.setColor(Color.RED);
            g.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 80));
        	g.drawString("Press Enter to play again", 280, 600);
        	
        	
            if(keyDown.get("enter")) {
            	new GameView().setVisible(false);
                new GameView();  
            }
    	}
    	
    	
        g.dispose();
        backBuffer.show();
    }  
    
    static void PlaySound(File sound) {
    	try {
    		Clip clip = AudioSystem.getClip();
    		clip.open(AudioSystem.getAudioInputStream(sound));
    		clip.start();
    		
    		Thread.sleep(clip.getMicrosecondLength()/1000);
    	}catch(Exception e) {
    		
    	}
    }
  
    public HashMap<String, Boolean> getKeyDown(){
    	return keyDown;
    }
  
    /** Spelets tangentbordslyssnare */  
    public void keyPressed(KeyEvent e) {  
    	//	System.out.println("KeyPress");
        int key = e.getKeyCode();  
  
        if(key == KeyEvent.VK_LEFT)  
            keyDown.put("left", true);  
        else if(key == KeyEvent.VK_RIGHT)  
            keyDown.put("right", true);  
        else if(key == KeyEvent.VK_SPACE)  
            keyDown.put("space", true);
        else if(key == KeyEvent.VK_UP)  
            keyDown.put("up", true);  
        else if(key == KeyEvent.VK_DOWN)  
            keyDown.put("down", true);  
        else if(key == KeyEvent.VK_ENTER)  
            keyDown.put("enter", true);  
        
        else if(key == KeyEvent.VK_ESCAPE)  
            keyDown.put("escape", true);  
        
        if(key == KeyEvent.VK_D)  
            keyDown.put("left", true);  
        else if(key == KeyEvent.VK_G)  
            keyDown.put("right", true);  
        else if(key == KeyEvent.VK_R)  
            keyDown.put("up", true);  
        else if(key == KeyEvent.VK_F)  
            keyDown.put("down", true);  
    }  
  
    public void keyReleased(KeyEvent e) {  
        int key = e.getKeyCode();  
  
        if(key == KeyEvent.VK_LEFT)  
            keyDown.put("left", false);  
        else if(key == KeyEvent.VK_RIGHT)  
            keyDown.put("right", false);  
        else if(key == KeyEvent.VK_SPACE) {  
            keyDown.put("space", false); 
        }else if(key == KeyEvent.VK_UP)  
            keyDown.put("up", false); 
        else if(key == KeyEvent.VK_DOWN)  
            keyDown.put("down", false); 
        else if(key == KeyEvent.VK_ENTER)  
            keyDown.put("enter", false);  
        
        if(key == KeyEvent.VK_D)  
            keyDown.put("left", false);  
        else if(key == KeyEvent.VK_G)  
            keyDown.put("right", false);  
        else if(key == KeyEvent.VK_R)  
            keyDown.put("up", false);  
        else if(key == KeyEvent.VK_F)  
            keyDown.put("down", false);
        
        else if(key == KeyEvent.VK_ESCAPE)  
            keyDown.put("escape", false);
       
    }  
    public void keyTyped(KeyEvent e) {  
    	  
    }

	public Canvas getGameCanvas() {
		return gameCanvas;
	}  
      

} 


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

public class GameMain extends JFrame implements KeyListener{  
      
    Scanner keyboard = new Scanner(System.in);
    private Canvas gameCanvas = new Canvas();  
     
    private boolean gameRunning = true;  
    private BufferStrategy backBuffer;
    private long lastUpdateTimefps;
    private long lastUpdateTime;
    private long lastUpdateTimesound;
    private long lastUpdateTimepowerup;
    private int ammo = 0;
  
    private HashMap<String, Boolean> keyDown = new HashMap<>();  
      
    private ShipEntity ship;  
    private DotEntity dot;  
    private PowerUpEntity powerup;  
    
    private Image upright;
    private Image upleft;
    private Image downright;
    private Image downleft;
    private Image down;
    private Image left;  
    private Image right;
    private Image shipImg;
    private Image dotImg;
    private Image powerupImg;
    
    
    ArrayList<DotEntity> dotList = new ArrayList<>();
    ArrayList<PowerUpEntity> powerupList = new ArrayList<>();
  
    public GameMain(){  
        super("Ett välstrukturerat och extremt bra gymnasiearbete");   
        addKeyListener(this);  
  
        keyDown.put("left", false);  
        keyDown.put("right", false); 
        keyDown.put("space", false);
        keyDown.put("up", false);
        keyDown.put("down", false);
        keyDown.put("enter", false);
        
        lastUpdateTime = System.currentTimeMillis();

        createWindow();  
        loadObjects();
        gameLoop();  
    }  
      
    public void loadObjects(){        
        /** 
         * Om du skall exportera till en körbar jar-fil skall raden ändras till 
         * ship = new ImageIcon(getClass().getResource("/ship.png")).getImage(); 
         */  
        shipImg = new ImageIcon("images/player.png").getImage();  
        left = new ImageIcon("images/playerleft.png").getImage(); 
        right = new ImageIcon("images/playerright.png").getImage();  
        down = new ImageIcon("images/playerdown.png").getImage(); 
        upright = new ImageIcon("images/playerupright.png").getImage(); 
        upleft = new ImageIcon("images/playerupleft.png").getImage(); 
        downright = new ImageIcon("images/playerdownright.png").getImage(); 
        downleft = new ImageIcon("images/playerdownleft.png").getImage(); 
        dotImg = new ImageIcon("images/dotImg.png").getImage();

          
        double x = gameCanvas.getWidth()/2 - shipImg.getWidth(null)/2;  
        double y = gameCanvas.getHeight() - shipImg.getHeight(null);  
          
        ship = new ShipEntity(shipImg, x, y, 1000);  
        dot = new DotEntity(dotImg);  
        powerup = new PowerUpEntity(powerupImg);  
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
  
    public void update(long deltaTime){
    	int height = ship.getImage().getHeight(null);
    	int width = ship.getImage().getWidth(null);
    	
        if(keyDown.get("right") && (keyDown.get("left"))) {
        	ship.setDirectionX(0);
        } 
        
        else if(keyDown.get("right") && ship.xPos < (gameCanvas.getWidth() - width)) {
            ship.setDirectionX(1);
            ship.setImage(right);
        }    
        else if(keyDown.get("left") && ship.xPos > 0) {
        	ship.setImage(left);
            ship.setDirectionX(-1);
        }
        else {
            ship.setDirectionX(0);
        }
        
        if(keyDown.get("up") && (keyDown.get("down"))) {
        	ship.setDirectionY(0);
        }
        else if(keyDown.get("up") && ship.yPos > 0) {
            ship.setDirectionY(-1);
            ship.setImage(shipImg);
        }
        
        else if(keyDown.get("down") && ship.yPos < (gameCanvas.getHeight() - height)) {
            ship.setDirectionY(1);
            ship.setImage(down);
        }
        else {
            ship.setDirectionY(0);
        }
        
    	if(keyDown.get("right") && (keyDown.get("up")) && ship.xPos < (gameCanvas.getWidth() - width) && ship.yPos < (gameCanvas.getHeight() - height) && 0 < ship.yPos) {
   		 	ship.setImage(upright);
        } 
    	
    	if(keyDown.get("left") && (keyDown.get("up")) && ship.xPos > 0 && ship.yPos < (gameCanvas.getHeight() - height) && 0 < ship.yPos) {
   		 	ship.setImage(upleft);
        } 
    	
    	if(keyDown.get("right") && (keyDown.get("down")) && ship.xPos < (gameCanvas.getWidth() - width) && ship.yPos < (gameCanvas.getHeight() - height) && 0 < ship.yPos) {
   		 	ship.setImage(downright);
        } 
    	
    	if(keyDown.get("left") && (keyDown.get("down")) && ship.xPos > 0 && ship.yPos < (gameCanvas.getHeight() - height) && 0 < ship.yPos) {
   		 	ship.setImage(downleft);
        } 
    	
    	if(keyDown.get("space") && ammo == 3){
        	ship.tryToFire();
            MissileSound();
        	ammo = 0;
    	}
    	
		long deltaTimedot = System.currentTimeMillis() - lastUpdateTime;
        if (deltaTimedot > 3000 && dotList.size() < 3) {
            dot = new DotEntity(dotImg);  
    		dotList.add(dot);
    		lastUpdateTime = System.currentTimeMillis();
        }
    	
    	ship.checkCollisionWhithMissiles();
    	
    	for(int i = 0; i < dotList.size(); i++) {
    		if(ship.collision(dotList.get(i)) && ammo < 3) {
    			dotList.remove(dotList.get(i));
    			ammo++;
                DotSound();
    		}
    	}

		long deltaTimepowerup = System.currentTimeMillis() - lastUpdateTimepowerup;
        if (deltaTimepowerup > 3000 && powerupList.size() < 3) {
            powerup = new PowerUpEntity(powerupImg);  
    		powerupList.add(powerup);
    		lastUpdateTimepowerup = System.currentTimeMillis();
        }
        
    	for(int i = 0; i < powerupList.size(); i++) {
    		if(ship.collision(powerupList.get(i))) {
    			powerupList.remove(powerupList.get(i));
    		}
    	}
    	
		long deltaTimestart = System.currentTimeMillis() - lastUpdateTimesound;
        if (deltaTimestart > 13000) { 
            BackgroundSound();
    		lastUpdateTimesound = System.currentTimeMillis();
        }
    	
    	
        ship.move(deltaTime);
    }
  
    public void render(){  
        Graphics2D g = (Graphics2D)backBuffer.getDrawGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
        ship.draw(g);

        
    	if(!dotList.isEmpty()) {
    		for(int i = 0; i < dotList.size(); i++)
    			dotList.get(i).draw(g);
    	}
    	
        g.setColor(Color.BLACK);
        g.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 80));
    	g.drawString(ammo + "/3", 1300, 780);
        g.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 60));
    	g.drawString("Score: " + ship.missileArray.size(), 0, 50);
    	
    	if(ship.getCollisionCheck() == 0) {
            g.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 100));
        	g.drawString("Gameover", 500, 300);
            g.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 80));
        	g.drawString("Score: " + ship.missileArray.size(), 565, 500);
        	
            g.setColor(Color.RED);
            g.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 80));
        	g.drawString("Press Enter to play again", 280, 600);
        	
        	
            if(keyDown.get("enter")) {
            	new GameMain().setVisible(false);
                new GameMain();  
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
  
    public void gameLoop(){
        lastUpdateTimefps = System.nanoTime();

        while(gameRunning){
            long deltaTime = System.nanoTime() - lastUpdateTimefps;

            if(deltaTime > 33333){
                lastUpdateTimefps = System.nanoTime();

                if(ship.getCollisionCheck() == 1) {
                	update(deltaTime);
                }
                render();
            }
        }
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
       
    }  
    public void keyTyped(KeyEvent e) {  
    	  
    }  
      
    public static void main(String[] args) {  
        new GameMain();  
    }  

} 


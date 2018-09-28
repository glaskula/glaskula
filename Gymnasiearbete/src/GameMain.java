import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class GameMain extends JFrame implements KeyListener{  
      
    private Canvas gameCanvas;  
    private boolean gameRunning = true;  
    private BufferStrategy backBuffer;
    private long lastUpdateTime;
  
    private HashMap<String, Boolean> keyDown = new HashMap<>();  
      
    private ShipEntity ship;  
    
    ArrayList<Entity> spriteList = new ArrayList<>();
  
    public GameMain(){  
        super("Space Invader");  
        addKeyListener(this);  
  
        keyDown.put("left", false);  
        keyDown.put("right", false); 
        keyDown.put("space", false);
        
        createWindow();  
        loadObjects();  
        gameLoop();  
    }  
      
    public void loadObjects(){        
        /** 
         * Om du skall exportera till en körbar jar-fil skall raden ändras till 
         * ship = new ImageIcon(getClass().getResource("/ship.png")).getImage(); 
         */  
        Image shipImg = new ImageIcon("images/player.png").getImage();  
          
        double x = gameCanvas.getWidth()/2 - shipImg.getWidth(null)/2;  
        double y = gameCanvas.getHeight() - shipImg.getHeight(null);  
          
        ship = new ShipEntity(shipImg, x, y, 800);  
    }  
      
    public void createWindow(){  
        gameCanvas = new Canvas();  
        gameCanvas.setSize(800, 600);  
        gameCanvas.setFocusable(false);  
  
        this.add(gameCanvas);  
        this.pack(); 
        this.setVisible(true);  
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);  
		this.setResizable(false);
        gameCanvas.createBufferStrategy(2); 
        backBuffer = gameCanvas.getBufferStrategy();
    }  
  
    public void update(long deltaTime){
        if(keyDown.get("right") && ship.xPos < (gameCanvas.getWidth() - 64))
            ship.setDirectionX(1);
        else if(keyDown.get("left") && ship.xPos > 0)
            ship.setDirectionX(-1);
        else
            ship.setDirectionX(0);
        
        if(keyDown.get("space")){
        	ship.tryToFire();
        }
        ship.move(deltaTime);
    }
  
    public void render(){  
        Graphics2D g = (Graphics2D)backBuffer.getDrawGraphics();

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 800, 600);
        ship.draw(g);

        g.dispose();
        backBuffer.show();
    }  
  
    public void gameLoop(){
        lastUpdateTime = System.nanoTime();

        while(gameRunning){
            long deltaTime = System.nanoTime() - lastUpdateTime;

            if(deltaTime > 33333){
                lastUpdateTime = System.nanoTime();
                update(deltaTime);
                render();
            }
        }
    }
    
    public void checkCollisionAndRemove(){
        ArrayList<Entity> removeList = new ArrayList<>();

        // alien <-> missile
        if(ship.missile != null && ship.missile.getActive()){
             // Egen kod här!
        }

        spriteList.removeAll(removeList); // Alt namnet på arraylist
    }
  
    /** Spelets tangentbordslyssnare */  
    public void keyPressed(KeyEvent e) {  
        int key = e.getKeyCode();  
  
        if(key == KeyEvent.VK_LEFT)  
            keyDown.put("left", true);  
        else if(key == KeyEvent.VK_RIGHT)  
            keyDown.put("right", true);  
        else if(key == KeyEvent.VK_SPACE)  
            keyDown.put("space", true);  
    }  
  
    public void keyReleased(KeyEvent e) {  
        int key = e.getKeyCode();  
  
        if(key == KeyEvent.VK_LEFT)  
            keyDown.put("left", false);  
        else if(key == KeyEvent.VK_RIGHT)  
            keyDown.put("right", false);  
        else if(key == KeyEvent.VK_SPACE)  
            keyDown.put("space", false); 
    }  
    public void keyTyped(KeyEvent e) {  
    	  
    }  
      
    public static void main(String[] args) {  
        new GameMain();  
    }  
} 


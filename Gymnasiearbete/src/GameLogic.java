import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class GameLogic {


	private boolean gameRunning = true;  
	private long lastUpdateTimefps;
	private long lastUpdateTime;
	private long lastUpdateTimesound;
	private long lastUpdateTimepowerup;
	private int ammo = 0;

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

	private GameView gameView;


	ArrayList<DotEntity> dotList = new ArrayList<>();
	ArrayList<PowerUpEntity> powerupList = new ArrayList<>();

	public GameLogic(GameView gameView){
		
		this.gameView = gameView;
		lastUpdateTime = System.currentTimeMillis();

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
		powerupImg = new ImageIcon("images/player.png").getImage();

		
		int x = gameView.getGameCanvas().getWidth()/2 - shipImg.getWidth(null)/2;  
		int y = gameView.getGameCanvas().getHeight() - (shipImg.getHeight(null) + 100);  

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

	public void update(long deltaTime){
		int height = ship.getImage().getHeight(null) + 100;
		int width = ship.getImage().getWidth(null);

		if(gameView.getKeyDown().get("right") && (gameView.getKeyDown().get("left"))) {
			ship.setDirectionX(0);
		} 

		else if(gameView.getKeyDown().get("right") && ship.xPos < (gameView.getGameCanvas().getWidth() - width)) {
			ship.setDirectionX(1);
			ship.setImage(right);
		}    
		else if(gameView.getKeyDown().get("left") && ship.xPos > 0) {
			ship.setImage(left);
			ship.setDirectionX(-1);
		}
		else {
			ship.setDirectionX(0);
		}

		if(gameView.getKeyDown().get("up") && (gameView.getKeyDown().get("down"))) {
			ship.setDirectionY(0);
		}
		else if(gameView.getKeyDown().get("up") && ship.yPos > 0) {
			ship.setDirectionY(-1);
			ship.setImage(shipImg);
		}

		else if(gameView.getKeyDown().get("down") && ship.yPos < (gameView.getGameCanvas().getHeight() - height)) {
			ship.setDirectionY(1);
			ship.setImage(down);
		}
		else {
			ship.setDirectionY(0);
		}

		if(gameView.getKeyDown().get("right") && (gameView.getKeyDown().get("up")) && ship.xPos < (gameView.getGameCanvas().getWidth() - width) && ship.yPos < (gameView.getGameCanvas().getHeight() - height) && 0 < ship.yPos) {
			ship.setImage(upright);
		} 

		if(gameView.getKeyDown().get("left") && (gameView.getKeyDown().get("up")) && ship.xPos > 0 && ship.yPos < (gameView.getGameCanvas().getHeight() - height) && 0 < ship.yPos) {
			ship.setImage(upleft);
		} 

		if(gameView.getKeyDown().get("right") && (gameView.getKeyDown().get("down")) && ship.xPos < (gameView.getGameCanvas().getWidth() - width) && ship.yPos < (gameView.getGameCanvas().getHeight() - height) && 0 < ship.yPos) {
			ship.setImage(downright);
		} 

		if(gameView.getKeyDown().get("left") && (gameView.getKeyDown().get("down")) && ship.xPos > 0 && ship.yPos < (gameView.getGameCanvas().getHeight() - height) && 0 < ship.yPos) {
			ship.setImage(downleft);
		} 

		if(gameView.getKeyDown().get("space") && ammo == 3){
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
		if (deltaTimepowerup > 30000 && ship.missileArray.size() >= 2) {
			powerup = new PowerUpEntity(powerupImg);  
			powerupList.add(powerup);
			lastUpdateTimepowerup = System.currentTimeMillis();
		}

		for(int i = 0; i < powerupList.size(); i++) {
			if(ship.collision(powerupList.get(i))) {
				powerupList.remove(powerupList.get(i));
				ship.powerupcheck();
				DotSound();
			}
		}

		long deltaTimestart = System.currentTimeMillis() - lastUpdateTimesound;
		if (deltaTimestart > 13000) { 
			BackgroundSound();
			lastUpdateTimesound = System.currentTimeMillis();
		}

		ship.move(deltaTime);
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
				gameView.render(ship);
			}
		}
	}

}

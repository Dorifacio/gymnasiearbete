package se.qori.game;
import se.egy.graphics.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.ImageIcon;
import java.io.File;

public class GameMenuTest implements KeyListener{
	boolean gameRunning = true;

	private HashMap<String, Boolean> keyDown = new HashMap<String, Boolean>();

	private TileMap map;

	//players
	private ArrayList<Drawable> renderList = new ArrayList<Drawable>();



	private Entity player;

	private Entity playero;
	//players

	//Menu
	private Entity titel;
	private Entity playButton;
	private Entity playButtonB;
	private Entity creditsButton;
	private Entity creditsButtonB;
	//Menu

	//Menu keys
	private int meny = 1;
	//Menu keys

	//credits screen
	private Entity credits;
	//credits screen

	//map
	private Image[] tiles;
	//map

	//screen
	private GameScreen gameScreen = new GameScreen("Game", 1920, 1080, false); // OBS! Skall Vara false.
	//screen

	//text 
	public Font font = null;

	private TxtContainer msg;
	//text

	int camX = 0;
	int camY = 0;

	public GameMenuTest(){
		loadImages();
		try {
			String path = getClass().getResource("/droidlover.ttf").getFile();
			path =  URLDecoder.decode(path,"utf-8");

			font = Font.createFont(Font.TRUETYPE_FONT, new File(path));
			font = font.deriveFont(32f); // Typsnittsstorlek
		} catch (Exception e) {
			e.printStackTrace();
		}

		msg = new TxtContainer("Game Over", 30, 50, font, Color.GREEN);

		map = new TileMap("EpicMap3.txt", 32, tiles);

		gameScreen.setBackground(map);

		gameScreen.setKeyListener(this);

		keyDown.put("left", false);
		keyDown.put("right", false);
		keyDown.put("up", false);
		keyDown.put("down", false);

		//ESC

		keyDown.put("esc", false);

		//PLayer 2

		keyDown.put("a", false);
		keyDown.put("d", false);
		keyDown.put("w", false);
		keyDown.put("s", false);

		gameLoop();
	}
	public void gravity() {
		int ny = (int)player.getY();
		ny = ny -10;
	}

	public void loadImages(){
		//players

		Image playerImg = new ImageIcon(getClass().getResource("/Katto.png")).getImage();		
		player = new Entity(playerImg, 300, 45);

		Image player2Img = new ImageIcon(getClass().getResource("/playerImg.png")).getImage();		
		playero = new Entity(player2Img, 10, 10);

		renderList.add(player);
		renderList.add(playero);	

		//players


		//Menu
		Image Title = new ImageIcon(getClass().getResource("/Title.png")).getImage();		
		titel = new Entity(Title, 10, 100);
		renderList.add(titel);

		Image PLayB = new ImageIcon(getClass().getResource("/ButtonB.png")).getImage();		
		playButtonB = new Entity(PLayB, 50, 500);
		renderList.add(playButtonB);

		Image PLay = new ImageIcon(getClass().getResource("/Play.png")).getImage();		
		playButton = new Entity(PLay, 60, 510);
		renderList.add(playButton);


		Image CreditsB = new ImageIcon(getClass().getResource("/ButtonB.png")).getImage();		
		creditsButtonB = new Entity(CreditsB, 50, 700);
		renderList.add(creditsButtonB);

		Image Credits = new ImageIcon(getClass().getResource("/Credits.png")).getImage();		
		creditsButton = new Entity(Credits, 60, 710);
		renderList.add(creditsButton);	
		//Menu
		
		//credits
		Image Creditscreen = new ImageIcon(getClass().getResource("/Creditsscreen.png")).getImage();		
		credits = new Entity(Creditscreen, 0, 0);
		renderList.add(credits);	
		//credits


		Image tileSet = new ImageIcon(getClass().getResource("/tileset-mario.png")).getImage();
		tiles = ImageTools.slice(tileSet, 16, 16, 0);
	}

	public void update(long delta){	

		//Meny sudas bort
		if (meny == 0) {
			renderList.remove(titel);
			renderList.remove(playButton);
			renderList.remove(playButtonB);
			renderList.remove(creditsButton);
			renderList.remove(creditsButtonB);
		}
		
		//Player sudas bort
		if (meny == 1) {
			renderList.remove(player);
			renderList.remove(playero);
			renderList.remove(credits);
		}

		//Player1
		int nx = (int)player.getX();
		int ny = (int)player.getY();
		int w = player.getWidth();
		
		//PLayer2
		int mx = (int)playero.getX();
		int my = (int)playero.getY();
		int w2 = playero.getWidth();

		// PLayer 1:
		if (keyDown.get("left")) {
			nx = nx - 5;
		}
		if (keyDown.get("right") ) {
			nx = nx + 5;;
		}
		if (keyDown.get("up")) {
			ny = ny - 5;
		}
		if (keyDown.get("down")) {
			ny = ny + 5;
		}

		//ESC 

		if(keyDown.get("esc")) {
			gameRunning = false;
		}

		// Player 2:

		if (keyDown.get("a")) {
			mx = mx - 5;
		}
		if (keyDown.get("d") ) {
			mx = mx + 5;;
		}
		if (keyDown.get("w")) {
			my = my - 5;
		}
		if (keyDown.get("s")) {
			my = my + 5;
		}


		boolean move = true;

		if(player.collision(playero)) {
			System.out.println("Game over");
			move = false;
		}


		boolean move2 = true;

		if(playero.collision(player)) {
			System.out.println("Krock");
			move2 = false;
		}
		
		/*if (keyDown.get("r")) {
			move = true;
			move2 = true;
		}*/

		//PLAyer 1 KOLLISION

		Tile[] tiles = map.getTilesFromSquare(nx, ny, w);

		for(int i = 0; i < 4; i++) {
			if(tiles[i].VALUE != 0x0E) { // 0x0E vid mario, 0xA vid 16 bilder, 1 vid färger
				if(tiles[i].VALUE == 0x1A) {
					map.setTile(tiles[i].COLUMN, tiles[i].ROW, 0x0E);
				}else {
					move = false;
					break;
				}
			}
		}

		//PLayer 2

		Tile[] tiles2 = map.getTilesFromSquare(mx, my, w2);
		for(int i = 0; i < 4; i++) {
			if(tiles2[i].VALUE != 0x0E) { // 0x0E vid mario, 0xA vid 16 bilder, 1 vid färger
				if(tiles2[i].VALUE == 0x1A) {
					map.setTile(tiles2[i].COLUMN, tiles2[i].ROW, 0x0E);
				}else {
					move2 = false;
					break;
				}
			}
		}

		if(move) {
			player.setX(nx);
			player.setY(ny);
		}

		if(move2) {
			playero.setX(mx);
			playero.setY(my);
		}

		if(player.getX() >= 300) {
			camX = (int)player.getX() - 300;
		}
		if(camX > 416){
			camX = 416;
		}
		gameScreen.cameraMoveTo(camX, 0);
	}

	public void render(){

		gameScreen.render(renderList);

		if (player.collision(playero)) {
			renderList.add(msg);
		}

	}

	public void gameLoop(){
		long last = System.nanoTime();
		while(gameRunning){
			long delta = (System.nanoTime() - last);
			last = System.nanoTime();
			update(delta);

			render();

			try { Thread.sleep(4); } catch (Exception e) {}; // Fördröjning
		}

		try{
			gameScreen.close();
		}catch(GameCloseException e) {
			System.out.println(e);
		}
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			keyDown.put("left", true);
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			keyDown.put("right", true);
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			keyDown.put("down", true);
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			keyDown.put("up", true);
		}

		//ESC

		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			keyDown.put("esc", true);
		}
		
		//RESTART
		
		if (e.getKeyCode() == KeyEvent.VK_R) {
			keyDown.put("r", true);
		}

		//Player 2

		if (e.getKeyCode() == KeyEvent.VK_A) {
			keyDown.put("a", true);
		}
		if (e.getKeyCode() == KeyEvent.VK_D) {
			keyDown.put("d", true);
		}
		if (e.getKeyCode() == KeyEvent.VK_S) {
			keyDown.put("s", true);
		}
		if (e.getKeyCode() == KeyEvent.VK_W) {
			keyDown.put("w", true);
		}
		
		//Meny
		if (e.getKeyCode() == KeyEvent.VK_1) {
			keyDown.put("1", true);
		}
		if (e.getKeyCode() == KeyEvent.VK_2) {
			keyDown.put("2", true);
		}

	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			keyDown.put("left", false);
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			keyDown.put("right", false);
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			keyDown.put("down", false);
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			keyDown.put("up", false);
		}

		//Player 2 

		if (e.getKeyCode() == KeyEvent.VK_A) {
			keyDown.put("a", false);
		}
		if (e.getKeyCode() == KeyEvent.VK_D) {
			keyDown.put("d", false);
		}
		if (e.getKeyCode() == KeyEvent.VK_S) {
			keyDown.put("s", false);
		}
		if (e.getKeyCode() == KeyEvent.VK_W) {
			keyDown.put("w", false);
		}
		
		//Meny
		if (e.getKeyCode() == KeyEvent.VK_1) {
			keyDown.put("1", false);
		}
		if (e.getKeyCode() == KeyEvent.VK_2) {
			keyDown.put("2", false);
		}
	}

	public static void main(String[] args) {
		new GameMenuTest();
	}
}

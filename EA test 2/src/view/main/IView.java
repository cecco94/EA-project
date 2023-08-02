package view.main;

import java.awt.AWTException;
import java.awt.Graphics2D;
import java.awt.Robot;
import java.awt.image.BufferedImage;

import controller.Gamestate;
import controller.IController;
import model.IModel;
import view.gameBegin.StartTitle;
import view.inputs.MouseInputs;
import view.mappa.Tileset;
import view.menu.avatarSelection.AvatarMenu;
import view.menu.mainMenu.MainMenu;
import view.menu.optionMenu.OptionMenu;
import view.sound.SoundManager;

// per ora la classe più importante, gestisce tutte le altre classi della view, che fanno riferimento 
// a lei per cambiare cose importanti come il volume o il gamestate. permette inoltre alle altre classi 
// di comunicare in modo indiretto

public class IView {
	private IController controller;
	private IModel model;
//	private MessaggioSubliminale ms = new MessaggioSubliminale();
	private Robot robot;
	private MouseInputs mi;
	private GamePanel gp;
	private GameWindow gw;
	private SoundManager sound;
	//stati del gioco
	private StartTitle start;
	private MainMenu menu;
	private AvatarMenu avatar;
	private OptionMenu opzioni;
	//mappa e tiles
	private Tileset tileset;
	
	public IView(IController control) {
		controller = control;
		initViewClasses();
	//	setStartMusic();
	}

	private void initViewClasses() {
		sound = new SoundManager();
		mi = new MouseInputs(this);
		gp = new GamePanel(this, mi);
		menu = new MainMenu(this);
		start = new StartTitle(this);
		avatar = new AvatarMenu(this);
		opzioni = new OptionMenu(this);
		tileset = new Tileset();
		
		//inizializza le cose più delicate
		gw = new GameWindow(gp);
		setCursorManager();
		gp.setFocusable(true);
		gp.requestFocus();
	}

	private void setCursorManager() {
		try {
			robot = new Robot();
		} 
		catch (AWTException e) {
			e.printStackTrace();
		}
	}

	private void setStartMusic(){
		sound.playMusic(SoundManager.MENU_MUSIC);
		sound.setMusicVolume(0.2f);
	}

	//chiede al pannello di creare il suo ambiente grafico, g, che poi userà per disegnare il frame successivo
	public void draw() {	
		gp.repaint();	
	}

	public void prepareNewFrame(Graphics2D g2) {
		switch(Gamestate.state) {
		case START_TITLE:
			start.drawYourself(g2);
			break;
		case MAIN_MENU:
			menu.drawYourself(g2);
			break;
		case SELECT_AVATAR:
			avatar.drawAvatarMenu(g2);
			break;
		case LOAD_GAME: 
			drawFirstLayer(g2);
			drawSecondLayer(g2);
		//	drawTileset(g2);
			break;
		case OPTIONS:
			opzioni.draw(g2);
			break;
		case QUIT:
			System.exit(0);
			break;
		case PLAYING:
			//g2.drawImage(tileset.getTile(7).getImage(), 0, 0, null);
			break;			
		}
	//	ms.disegnaMessaggioSubliminale(g2);
	}
	
	private void drawSecondLayer(Graphics2D g2) {
		int[][] strato = model.getMappa().getStrato(0, 1);
		for(int riga = 0; riga < strato.length; riga++) {
			for(int colonna = 0; colonna < strato[riga].length; colonna++) {
				int numeroTile = strato[riga][colonna];
				if(numeroTile != 0) {
					BufferedImage tileDaDisegnare = tileset.getTile(numeroTile -1 ).getImage();
					g2.drawImage(tileDaDisegnare, colonna* GamePanel.TILES_SIZE , riga*GamePanel.TILES_SIZE, null);
				}
			}
		}
		
	}

	private void drawTileset(Graphics2D g2) {		//for debug
	//	int yPosition = 0;
		for(int i = 6; i < 10; i++) {
			g2.drawImage(tileset.getTile(i).getImage(), 0, i*GamePanel.TILES_SIZE, null);
		//	yPosition++;
		}
		
	}

	private void drawFirstLayer(Graphics2D g2) {		//for debug
		int[][] strato = model.getMappa().getStrato(0, 0);
		for(int riga = 0; riga < strato.length; riga++) {
			for(int colonna = 0; colonna < strato[riga].length; colonna++) {
				int numeroTile = strato[riga][colonna];
				if(numeroTile != 0) {
					BufferedImage tileDaDisegnare = tileset.getTile(numeroTile -1 ).getImage();
					g2.drawImage(tileDaDisegnare, colonna* GamePanel.TILES_SIZE , riga*GamePanel.TILES_SIZE, null);
				}
			}
		}
			
		
	}

	public void changeGameState(Gamestate newState) {
		controller.setGameState(newState);
	}

	// per usare i tasti direzionali nei vari menu, ho fatto in modo che, usando i tasti,
	// si possa spostare la freccina e quindi i bottoni reagiscono allo spostamento della freccina.
	// in questo modo ho riciclato i metodi dei bottoni che gestiscono il mouse per 
	// gestire anche i tasti
	public void setCursorPosition(int X, int Y) {
		robot.mouseMove(gp.getLocationOnScreen().x + X, gp.getLocationOnScreen().y + Y);
	}
	
	// questi metodi servono per far comunicare tra loro le varie classi
	public GamePanel getGamePanel() {
		return gp;
	}
	
	public MainMenu getMenu() {
		return this.menu;
	}
	
	public StartTitle getStart() {
		return this.start;
	}
	
	public AvatarMenu getAvatarMenu(){
		return this.avatar;
	}
	
	public OptionMenu getOptions() {
		return this.opzioni;
	}
	
	public void playMusic(int i) {
		sound.playMusic(i);
	}
	
	public void stopMusic() {
		sound.stopMusic();
	}
	
	public void playSE(int i) {
		sound.playSE(i);
	}

	public void setMusicVolume(float v) {
		sound.setMusicVolume((float)v);
	}

	public void setSEVolume(float v) {
		sound.setSEVolume((float)v);
	}
	
	public void setModel(IModel mod) {
		model = mod;
	}
	
}

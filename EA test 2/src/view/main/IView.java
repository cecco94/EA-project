package view.main;

import java.awt.AWTException;
import java.awt.Graphics2D;
import java.awt.Robot;

import controller.Gamestate;
import model.Map;
import view.gameBegin.StartTitle;
import view.inputs.MouseInputs;
import view.menu.avatarSelection.AvatarMenu;
import view.menu.mainMenu.MainMenu;
import view.menu.optionMenu.OptionMenu;
import view.sound.SoundManager;

// per ora la classe più importante, gestisce tutte le altre classi della view, che fanno riferimento 
// a lei per cambiare cose importanti come il volume o il gamestate. permette inoltre alle altre classi 
// di comunicare in modo indiretto

public class IView {
	
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
//	private MapManager map;
	
	public IView() {
		initViewClasses();
		setStartMusic();
	}

	private void initViewClasses() {
		sound = new SoundManager();
		mi = new MouseInputs(this);
		gp = new GamePanel(this, mi);
		menu = new MainMenu(this);
		start = new StartTitle(this);
		avatar = new AvatarMenu(this);
		opzioni = new OptionMenu(this);
	//	map = new MapManager();
		gw = new GameWindow(gp);
		setCursorManager();
		gp.setFocusable(true);
		gp.requestFocus();
	//	gw.requestFocus();
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
			
			break;
		case OPTIONS:
			opzioni.draw(g2);
			break;
		case QUIT:
			System.exit(0);
			break;
		case PLAYING:
		//	map.draw(g2);
			break;			
		}
	//	ms.disegnaMessaggioSubliminale(g2);
	}
	
	public void changeGameState(Gamestate newState) {
		Gamestate.state = newState;
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
	
}

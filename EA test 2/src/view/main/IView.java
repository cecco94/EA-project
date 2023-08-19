package view.main;

import java.awt.AWTException;
import java.awt.Graphics2D;
import java.awt.Robot;

import controller.Gamestate;
import controller.IController;
import model.IModel;
import view.Transition;
import view.gameBegin.StartTitle;
import view.inputs.MouseInputs;
import view.mappa.TilesetView;
import view.menu.avatarSelection.AvatarMenu;
import view.menu.mainMenu.MainMenu;
import view.menu.optionMenu.OptionMenu;
import view.playState.PlayStateView;
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
	private PlayStateView play;
	private Transition transition;
	//mappa e tiles
	private TilesetView tileset;
	
	public IView(IController control, IModel mod) {
		controller = control;
		model = mod;
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
		tileset = new TilesetView();
		play = new PlayStateView(model, tileset, this);
		transition = new Transition(Gamestate.SELECT_AVATAR, Gamestate.PLAYING, this);
		//inizializza le cose più delicate
		gw = new GameWindow(gp, this);
		gw.setVisible(true);
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
		sound.loopMusic(SoundManager.MENU_MUSIC);
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
		case PLAYING:
			play.draw(g2);
			break;
		case TRANSITION:
			transition.draw(g2);
			break;
		case TRANSITION_ROOM:
			play.draw(g2);
			play.getUI().drawTransition(g2);
		default:
			break;			
		}
//		ms.disegnaMessaggioSubliminale(g2);
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
	public IController getController() {
		return controller;
	}
	
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
	
	public PlayStateView getPlay() {
		return play;
	}
	
	public void playMusic(int i) {
		sound.loopMusic(i);
	}
	
	public void stopMusic() {
		sound.stopMusic();
	}
	
	public void playSE(int i) {
		sound.playSE(i);
	}

	public void setMusicVolume(float v) {
		sound.setMusicVolume(v);
	}

	public void setSEVolume(float v) {
		sound.setSEVolume((float)v);
	}

	public float getMusicVolume() {
		return sound.getVolume();
	}
	
	public Transition getTransition() {
		return transition;
	}
	
}

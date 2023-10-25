package view;

import java.awt.AWTException;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;

import controller.IController;
import controller.main.Gamestate;
import controller.playState.Hitbox;
import model.IModel;
import model.mappa.Rooms;
import view.gameBegin.StartTitle;
import view.inputs.MouseInputs;
import view.main.GamePanel;
import view.main.GameWindow;
import view.menu.avatarSelection.AvatarMenu;
import view.menu.mainMenu.MainMenu;
import view.menu.optionMenu.OptionMenu;
import view.menu.pauseMenu.PauseScreen;
import view.playState.PlayStateView;
import view.playState.mappaView.TilesetView;

// permette al model ed al controlle di accedere ai dati e ai metodi della view e viceversa
// classe a cui fanno riferimento tutte le altre classi della view

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
	private OptionMenu option;
	private PlayStateView play;
	private TransitionState transition;
	private PauseScreen pause;
	private CutsceneView cutscene;
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
		option = new OptionMenu(this);
		tileset = new TilesetView();
		play = new PlayStateView(tileset, this);
		transition = new TransitionState(Gamestate.SELECT_AVATAR, Gamestate.PLAYING, this);
		pause = new PauseScreen(this);
		cutscene = new CutsceneView(this);
		
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
		sound.setSEVolume(0.2f);
	}

	//chiede al pannello di creare il suo ambiente grafico, g, che poi userà per disegnare il frame successivo
	public void draw() {	
		gp.repaint();	
	}
	
	//usa g2 per disegnare
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
			option.draw(g2);
			break;
		case PLAYING:
			play.draw(g2);
			break;
		case TRANSITION_STATE:
			transition.draw(g2);
			break;
		case PAUSE:
			play.draw(g2);
			pause.draw(g2);
			break;
		case DIALOGUE:
			play.draw(g2);
			play.getUI().drawDialogue(g2);
			break;
		case BOSS_CUTSCENE:
			cutscene.drawCutscene(g2);
			break;
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
		return this.option;
	}
	
	public PlayStateView getPlay() {
		return play;
	}
	
	public PauseScreen getPause() {
		return pause;
	}
	
	public SoundManager getSoundManager() {
		return sound;
	}
	
	public IModel getModel() {
		return model;
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
		return sound.getMusicVolume();
	}
	
	public TransitionState getTransition() {
		return transition;
	}
	
	//siccome la posizione è espressa in float, facciamo un cast in int in modo da poter usare drawimage
	public static Rectangle fromHitboxToRectangle(Hitbox h) {
		return new Rectangle((int)h.x, (int)h.y, h.width, h.height);
	}
	
	public int getCurrentRoomIndex() {
		return Rooms.currentRoom.mapIndex;
	}
	
	public int getCurrentRoomMusicIndex() {
		return Rooms.currentRoom.musicIndex;
	}
	
	public void clearBulletView() {
		play.getBullets().clear();
	}
	
	public void resetPlayerParring() {
		play.getPlayer().resetParry();
	}
	
	public void showMessageInUI(String text) {
		play.getUI().setMessage(text);
		play.getUI().setShowMessage(true);
	}
	
	public void addBulletView() {
		play.addBullet();
	}
	
	public void removeBulletView(int index) {
		play.removeBullet(index);
	}
	
	public void drawExclamationAboveEnemy(int index) {
		play.getUI().activeExclamation(index);
	}
	
	public void removeEnemy(int index) {
		play.removeEnemy(index);
	}
	
}

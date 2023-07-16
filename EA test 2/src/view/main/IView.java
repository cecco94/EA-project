package view.main;

import java.awt.AWTException;
import java.awt.Graphics2D;
import java.awt.Robot;

import controller.Gamestate;
import view.inputs.MouseInputs;
import view.menuIniziale.MainMenu;
import view.menuOpzioni.OptionMenu;
import view.selectAvatar.AvatarMenu;
import view.sound.SoundManager;
import view.startTitle.StartTitle;

public class IView {
	
//	private MessaggioSubliminale ms = new MessaggioSubliminale();
	private Robot robot;
	private MouseInputs mi;
	private GamePanel gp;
	private GameWindow gw;
	private SoundManager sound;
	private StartTitle start;
	private MainMenu menu;
	private AvatarMenu avatar;
	private OptionMenu opzioni;
	
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
		gw = new GameWindow(gp);
		try {
			robot = new Robot();
		} 
		catch (AWTException e) {
			e.printStackTrace();
		}
		gp.setFocusable(true);
		gp.requestFocus();
	}

	private void setStartMusic(){
		sound.playMusic(SoundManager.MENU_MUSIC);
	}

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
			System.out.println("load game");
			break;
		case OPTIONS:
			opzioni.draw(g2);
			break;
		case QUIT:
			System.exit(0);
			break;
		case PLAYING:
			System.out.println("play");
			break;			
		}
	//	ms.disegnaMessaggioSubliminale(g2);
	}
	
	public void changeGameState(Gamestate newState) {
		Gamestate.state = newState;
	}

	public void setCursorPosition(int X, int Y) {
		robot.mouseMove(gp.getLocationOnScreen().x + X, gp.getLocationOnScreen().y + Y);
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

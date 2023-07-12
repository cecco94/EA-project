package view;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import controller.Gamestate;
import view.inputs.MouseInputs;
import view.mainMenu.MainMenu;
import view.selectAvatar.AvatarMenu;
import view.sound.SoundManager;
import view.startTitle.StartTitle;

public class IView {

	private int frequenzaMessaggioSubliminale;
	private BufferedImage messaggioSubliminale;
	
	private MouseInputs mi;
	private GamePanel gp;
	private GameWindow gw;
	private SoundManager sound;
	private StartTitle start;
	private MainMenu menu;
	private AvatarMenu avatar;
	
	public IView() {
		caricaMessaggioSubliminale();
		initViewClasses();
		setStartMusic();
	}
	
	private void caricaMessaggioSubliminale() {
		messaggioSubliminale = null;
		BufferedImage temp = null;
		try {
			temp = ImageIO.read(getClass().getResourceAsStream("/subliminale.png"));
			messaggioSubliminale = ViewUtils.scaleImage(temp, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initViewClasses() {
		this.sound = new SoundManager();
		this.mi = new MouseInputs(this);
		this.gp = new GamePanel(this, mi);
		this.menu = new MainMenu(this);
		this.start = new StartTitle();
		this.avatar = new AvatarMenu(this);
		this.gw = new GameWindow(gp);
		gp.setFocusable(true);
		gp.requestFocus();
		
	}

	private void setStartMusic(){
		sound.play(SoundManager.MENU_MUSIC);
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
			System.out.println("opzioni");
			break;
		case QUIT:
			System.exit(0);
			break;
		case PLAYING:
			System.out.println("play");
			break;			
		}
	//	disegnaMessaggioSubliminale(g2);
	}
	
	public void changeGameState(Gamestate newState) {
		Gamestate.state = newState;
	}
	
	private void disegnaMessaggioSubliminale(Graphics2D g2) {
		frequenzaMessaggioSubliminale++;
		if(frequenzaMessaggioSubliminale == 90) {
			g2.drawImage(messaggioSubliminale, 0, 0, null);
			frequenzaMessaggioSubliminale = 0;
		}			
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
	
	public void playSE() {
		sound.play(SoundManager.COIN);
	}
	
	public void stopMusic() {
		sound.stop();
	}
	
}

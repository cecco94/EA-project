package view;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

import controller.main.Gamestate;
import view.main.GamePanel;

//per non passare bruscamente da uno stato all'altro, disegna sfumandolo il vecchio stato 
//per tre secondi e sfuma il valore della musica
public class TransitionState {

	private float counter;	
	private final float transitionDuration = 360; //120 fps quindi sono 3 secondi
			
	private float opacity;
	private Gamestate prev;
	private Gamestate next;
	private IView view;
	
	private float volume;
	private float volumeBeforeTransition;
	private boolean volumeSaved = false;
	
	public TransitionState(Gamestate p, Gamestate n, IView v) {
		setPrev(p);
		setNext(n);
		view = v;
		volume = view.getMusicVolume();
	}
	
	public void draw(Graphics2D g2) {
		counter++;
		saveOldVolume();
		
		if (counter < transitionDuration) {
			
			//disegna il vecchio stato
			view.changeGameState(prev);
			view.prepareNewFrame(g2);
			
			//disegna un rect nero sempre più visibile che copre il vecchio stato
			opacity = counter/transitionDuration;
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
			g2.setColor(Color.black);
			g2.fillRect(0, 0, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
			
			//sfuma il volume della musica
			volume = view.getMusicVolume() - opacity + 0.01f;			
			view.setMusicVolume(volume);
			
			view.changeGameState(Gamestate.TRANSITION_STATE);
			
		}
		else 
			resetTransition();
			
	}

	private void resetTransition() {
		//ripristina lo stato iniziale della classe
		counter = 0;
		volumeSaved = false;
		view.stopMusic();

		//se sta andando dal menu al play
		if(next == Gamestate.PLAYING && prev == Gamestate.SELECT_AVATAR) {
			view.playMusic(view.getCurrentRoomMusicIndex());
			view.changeGameState(next);
		}
		
		//se sta cambiando stanza
		else if(next == Gamestate.PLAYING && prev == Gamestate.PLAYING) {
			view.getController().getPlay().resumeGameAfterTransition();
			view.playMusic(view.getCurrentRoomMusicIndex());
			view.changeGameState(next);
		}
		
		//se sta tornando al menu iniziale
		else if(next == Gamestate.MAIN_MENU) { 
			view.playMusic(SoundManager.MENU_MUSIC);
			view.changeGameState(next);
			resetNextPrev();
		}
		
		view.setMusicVolume(volumeBeforeTransition);
		volume = view.getMusicVolume();	
		
	}

	private void saveOldVolume() {
		if (!volumeSaved) {
			volumeBeforeTransition = view.getMusicVolume();
			volumeSaved = true;
		}
	}

//	//da cambiare
	private void resetNextPrev() {
		next = Gamestate.PLAYING;
		prev = Gamestate.SELECT_AVATAR;
	}
	
	public Gamestate getPrev() {
		return prev;
	}

	public void setPrev(Gamestate prev) {
		this.prev = prev;
	}

	public Gamestate getNext() {
		return next;
	}

	public void setNext(Gamestate next) {
		this.next = next;
	}
	
}

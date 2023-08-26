package view;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;

import controller.main.Gamestate;
import model.mappa.Stanze;
import view.sound.SoundManager;

//per non passare bruscamente da uno stato all'altro, disegna sfumandolo il vecchio stato 
//per tre secondi e sfuma il valore della musica
public class TransitionState {

	private float counter = 360;	
	private final float transitionDuration = 360; //120 fps quindi sono 3 secondi
			
	private float opacity;
	private Gamestate prev;
	private Gamestate next;
	private IView view;
	
	private float volume;
	private float volumeBeforeTransition;
	private boolean saved = false;
	
	public TransitionState(Gamestate p, Gamestate n, IView v) {
		setPrev(p);
		setNext(n);
		view = v;
		volume = view.getMusicVolume();
	}
	
	public void draw(Graphics2D g2) {
		counter--;
		saveOldVolume();
		
		if (counter > 0) {
			
			//diventa sempre più trasparente 
			opacity = counter/transitionDuration;
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
			
			//disegna il vecchio stato
			view.changeGameState(prev);
			view.prepareNewFrame(g2);
			
			//sfuma il volume della musica
			volume = volume*opacity; 
						
			view.setMusicVolume(volume);
			
			view.changeGameState(Gamestate.TRANSITION_STATE);
			
		}
		else {
			//ripristina lo stato iniziale della classe
			counter = 360;
			saved = false;
			
			//cambia la musica
			view.stopMusic();
			if(next == Gamestate.MAIN_MENU) 
				view.playMusic(SoundManager.MENU_MUSIC);
			
			else 
				view.playMusic(Stanze.stanzaAttuale.indiceMusica);
			
			view.setMusicVolume(volumeBeforeTransition);
			volume = view.getMusicVolume();
			
			//cambia il gamestate
			view.changeGameState(next);
			resetNextPrev();
			
		}
	
	}

	private void saveOldVolume() {
		if (!saved) {
			volumeBeforeTransition = view.getMusicVolume();
			saved = true;
		}
	}

	//da cambiare
	private void resetNextPrev() {
		if(next == Gamestate.MAIN_MENU) {
			next = Gamestate.PLAYING;
			prev = Gamestate.SELECT_AVATAR;
		}
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

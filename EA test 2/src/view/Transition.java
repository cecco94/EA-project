package view;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;

import controller.Gamestate;
import view.main.IView;

//per non passare bruscamente da uno stato all'altro, disegna sfumandolo il vecchio stato 
//per tre secondi e sfuma il valore della musica
public class Transition {

	private float counter = 360;	
	private final float transitionDuration = 360; //120 fps quindi sono 3 secondi
			
	private float opacity;
	private float volume;
	private Gamestate prev;
	private Gamestate next;
	private IView view;
	
	public Transition(Gamestate p, Gamestate n, IView v) {
		setPrev(p);
		setNext(n);
		view = v;
		volume = view.getMusicVolume();
	}
	
	public void draw(Graphics2D g2) {
		//diventa sempre più trasparente e la musica diminuisce
		counter--;
		if (counter > 0) {
			opacity = counter/transitionDuration;
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
			//disegna il vecchio stato
			view.changeGameState(prev);
			view.prepareNewFrame(g2);
			//sfuma il volume della musica
			volume = volume*opacity + 0.01f;
			view.setMusicVolume(volume);
			
			view.changeGameState(Gamestate.TRANSITION);
			
		}
		else {
			view.stopMusic();
			view.changeGameState(next);
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

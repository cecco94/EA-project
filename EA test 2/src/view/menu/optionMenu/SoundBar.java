package view.menu.optionMenu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import view.IView;
import view.menu.AbstractMenu;
import view.menu.AbstractMenuButton;

public class SoundBar extends AbstractMenuButton {
	
	//sono statici, in questo modo tutte le soundbar hanno lo stesso volume
	private static float volumeSetted;
	private static float volumeSESetted;
	
	private float maxVolume;
	int type;
	public final static int MUSIC = 0, SE = 1;
	
	public SoundBar(AbstractMenu op, Rectangle r, IView v, int tipo) {
		super.setBounds(r.x, r.y, r.width, r.height);
		maxVolume = r.width;
		
		volumeSetted = v.getSoundManager().getMusicVolume()*maxVolume;       
		volumeSESetted = v.getSoundManager().getSEVolume()*maxVolume;
		
		view = v;
		type = tipo;		//per capire se dobbiamo modificare la musica o gli effetti
	}	
	
	@Override
	public void draw(Graphics2D g2) {
		drawCornice(g2);
		
		if(mouseOver == false) 
			g2.setColor(Color.red);
		else 
			g2.setColor(Color.white);
		
		
		if(type == MUSIC)
			g2.fillRoundRect(bounds.x, bounds.y, (int)volumeSetted, bounds.height, 10, 10);
		else 
			g2.fillRoundRect(bounds.x, bounds.y, (int)volumeSESetted, bounds.height, 10, 10);
	}

	private void drawCornice(Graphics2D g2) {
		g2.setColor(Color.red);
		g2.drawRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, 10, 10);
	}
	
	//setta il volume in base alla posizione del mouse rispetto all'inizio della soundbar
	public void reactToMouse(MouseEvent e) {
		int newVolume = e.getX() - bounds.x;
		
		changeRectDimension(newVolume);
		changeVolume(newVolume);
	}
	
	public void reactToDrag(MouseEvent e) {
		if(checkIfMouseIsIn(e)) {
			changeRectDimension(e.getX() - bounds.x);
			
			if(type == MUSIC)
				changeVolume(volumeSetted);
			
			else 
				changeVolume(volumeSESetted);
		}
	}
	
	// cambia la dimensione del rettangolo che indica il volume in base al volume 
	private void changeRectDimension(int x) {
		
		if(type == MUSIC) {
			if (x < 0)
				volumeSetted = 0;
			else if (x > maxVolume)
				volumeSetted = maxVolume;
			else
				volumeSetted = x;
		}
		
		else {
			if (x < 0)
				volumeSESetted = 0;
			else if (x > maxVolume)
				volumeSESetted = maxVolume;
			else
				volumeSESetted = x;
		}
		
	}
	
	private void changeVolume(float x) {
		float volumePercentuale = (float)x/maxVolume;
		
		if(type == MUSIC)
			view.setMusicVolume(volumePercentuale);
		else 
			view.setSEVolume(volumePercentuale);
	}
	
	public void reactToKeyRight() {
		if(type == MUSIC) {
			changeRectDimension((int)volumeSetted + (int)(maxVolume/20));
			changeVolume(volumeSetted + maxVolume/20);
		}
		
		else {
			changeRectDimension((int)volumeSESetted + (int)(maxVolume/20));
			changeVolume(volumeSESetted + maxVolume/20);
		}
	}
	
	public void reactToKeyLeft() {
		if(type == MUSIC) {
			changeRectDimension((int)volumeSetted - (int)(maxVolume/20));
			changeVolume(volumeSetted - maxVolume/20);
		}
		
		else {
			changeRectDimension((int)volumeSESetted - (int)(maxVolume/20));
			changeVolume(volumeSESetted - maxVolume/20);
		}
	}
	
	public float getMusicVolume() {
		return volumeSetted;
	}
	
	public float getSEVolume() {
		return volumeSESetted;
	}
	
}

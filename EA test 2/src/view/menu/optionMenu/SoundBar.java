package view.menu.optionMenu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import view.main.IView;
import view.menu.AbstractMenuButton;

public class SoundBar extends AbstractMenuButton {
	
	private int volumeSetted;
	private int maxVolume;
	int type;
	public final static int MUSIC = 0;
	public static final int SE = 1;
	
	public  SoundBar(OptionMenu op, Rectangle r, IView v, int tipo) {
		super.setBounds(r.x, r.y, r.width, r.height);
		maxVolume = r.width;
		volumeSetted = (int)(maxVolume*0.2);
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
		
		g2.fillRoundRect(bounds.x, bounds.y, volumeSetted, bounds.height, 10, 10);
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
			changeVolume(volumeSetted);
		}
	}
	
	// cambia la dimensione del rettangolo che indica il volume in base al volume 
	private void changeRectDimension(int x) {
		if (x < 0)
			volumeSetted = 0;
		else if (x > maxVolume)
			volumeSetted = maxVolume;
		else
			volumeSetted = x;
	}
	
	private void changeVolume(int x) {
		float volumePercentuale = (float)x/maxVolume;
		
		if(type == MUSIC)
			view.setMusicVolume(volumePercentuale);
		else 
			view.setSEVolume(volumePercentuale);
	}
	
	public void reactToKeyRight() {
		changeRectDimension(volumeSetted + maxVolume/20);
		changeVolume(volumeSetted + maxVolume/20);
	}
	
	public void reactToKeyLeft() {
		changeRectDimension(volumeSetted - maxVolume/20);
		changeVolume(volumeSetted - maxVolume/20);
	}
	
	public int getVolume() {
		return volumeSetted;
	}

}

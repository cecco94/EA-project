package model.mappa.events;

import java.awt.Rectangle;

import model.IModel;
import view.main.GamePanel;

public abstract class Event {

	protected Rectangle bounds;
	protected IModel model;
	protected String message;
	protected boolean interact, endInteraction;
	
	
	public Event(Rectangle r, IModel m) {
		bounds = r;
		model = m;
		
		bounds.x *= GamePanel.TILES_SIZE;
		bounds.y *= GamePanel.TILES_SIZE;
		
		bounds.width *= GamePanel.SCALE;
		bounds.height *= GamePanel.SCALE;
	}
	
	public abstract void Interact();
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	public boolean isEndInteraction() {
		return endInteraction;
	}
	
	public boolean checkPlayer(Rectangle hitboxPlayer) {
	//	System.out.println("posizione x " + bounds.x/GamePanel.TILES_SIZE + " posiz y " + bounds.y/GamePanel.TILES_SIZE + " larga " + bounds.width +  " altezza " + bounds.height);
		return hitboxPlayer.intersects(bounds);
	}
}

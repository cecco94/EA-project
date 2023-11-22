package model.mappa.events;



import controller.playState.Hitbox;
import model.IModel;

public abstract class Event {

	protected int index;
	protected Hitbox bounds;
	protected IModel model;
	protected String message;
	protected boolean endInteraction;
	
	
	public Event(Hitbox r, IModel m, int ind) {
		bounds = r;
		model = m;
		index = ind;
		
		bounds.x *= model.getController().getTileSize();
		bounds.y *= model.getController().getTileSize();
		
		bounds.width  *= model.getController().getGameScale();
		bounds.height *= model.getController().getGameScale();
	}
	
	public abstract void interact();
	
	public Hitbox getBounds() {
		return bounds;
	}
	
	public boolean isEndInteraction() {
		return endInteraction;
	}
	
	public boolean checkPlayer(Hitbox hitbox) {
		return hitbox.intersects(bounds);
	}
	
	public String toString() {
		String dataEvent = "dataEvent " + endInteraction + ", " + index;
		return dataEvent;
	}
}

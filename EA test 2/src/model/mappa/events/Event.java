package model.mappa.events;



import controller.playState.Hitbox;
import model.IModel;

public abstract class Event {

	protected Hitbox bounds;
	protected IModel model;
	protected String message;
	protected boolean interact, endInteraction;
	
	
	public Event(Hitbox r, IModel m) {
		bounds = r;
		model = m;
		
		bounds.x *= model.getController().getTileSize();
		bounds.y *= model.getController().getTileSize();
		
		bounds.width  *= model.getController().getGameScale();
		bounds.height *= model.getController().getGameScale();
	}
	
	public abstract void Interact();
	
	public Hitbox getBounds() {
		return bounds;
	}
	
	public boolean isEndInteraction() {
		return endInteraction;
	}
	
	public boolean checkPlayer(Hitbox hitbox) {
		return hitbox.intersects(bounds);
	}
}

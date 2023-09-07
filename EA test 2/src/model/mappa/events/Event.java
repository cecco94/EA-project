package model.mappa.events;



import controller.playState.Hitbox;
import model.IModel;
import view.main.GamePanel;

public abstract class Event {

	protected Hitbox bounds;
	protected IModel model;
	protected String message;
	protected boolean interact, endInteraction;
	
	
	public Event(Hitbox r, IModel m) {
		bounds = r;
		model = m;
		
		bounds.x *= GamePanel.TILES_SIZE;
		bounds.y *= GamePanel.TILES_SIZE;
		
		bounds.width *= GamePanel.SCALE;
		bounds.height *= GamePanel.SCALE;
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

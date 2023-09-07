package model.mappa.events;


import controller.playState.Hitbox;
import model.IModel;

public class Light extends Event {

	
	public Light(Hitbox r, IModel m) {
		super(r, m);
		message = "hai acceso la luce!";		
	}

	@Override
	public void Interact() {
		model.getView().getPlay().getUI().setDark(false);
		model.getView().getPlay().getUI().setMessage(message);
		model.getView().getPlay().getUI().setShowMessage(true);
		model.getView().getPlay().getUI().setDark(false);
		endInteraction = true;
	}
	
	
}

package model.mappa.events;

import java.awt.Rectangle;

import model.IModel;

public class Light extends Event{

	
	public Light(Rectangle r, IModel m) {
		super(r, m);
		message = "hai acceso la luce!";		
	}

	@Override
	public void Interact() {
		model.getView().getPlay().getUI().setBuio(false);
		model.getView().getPlay().getUI().setScritta(message);
		model.getView().getPlay().getUI().setShowMessage(true);
		model.getView().getPlay().getUI().setBuio(false);
		endInteraction = true;
	}
	
//	@Override
//	public void update() {
//		if(play.getPlayer().getHitbox().intersects(bounds)) {
//			
//			play.getController().getView().getPlay().getUI().setScritta("premi E per interagire");
//			play.getController().getView().getPlay().getUI().setShowMessage(true);
//			
//			if(play.getPlayer().isInteracting()) {
//				play.getController().getView().getPlay().getUI().setBuio(false);
//				play.getController().getView().getPlay().getUI().setScritta(message);
//				play.getController().getView().getPlay().getUI().setShowMessage(true);
//
//			}
//		}
//	}

		
	

	
	
}

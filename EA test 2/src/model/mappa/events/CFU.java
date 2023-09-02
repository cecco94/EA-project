package model.mappa.events;

import java.awt.Rectangle;

import model.IModel;
import view.sound.SoundManager;

public class CFU extends Event{

	public CFU(Rectangle r, IModel m) {
		super(r, m);
		message = "ti stai avvicinando alla laurea!";

	}

	@Override
	public void Interact() {
		model.getController().getPlay().getPlayer().addCFU();
		
		model.getView().playSE(SoundManager.CFU);
		
		model.getView().getPlay().getUI().setMessage(message);
		model.getView().getPlay().getUI().setShowMessage(true);
		
		endInteraction = true;
	}

}

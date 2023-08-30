package model.mappa.events;

import java.awt.Rectangle;

import model.IModel;
import view.sound.SoundManager;

public class Notes extends Event {

	public Notes(Rectangle r, IModel m) {
		super(r, m);
		message = "hai trovato degli appunti!";

	}

	@Override
	public void Interact() {
		model.getController().getPlay().getPlayer().addNotes();
		
		model.getView().playSE(SoundManager.APPUNTI);
		model.getView().getPlay().getUI().setScritta(message);
		model.getView().getPlay().getUI().setShowMessage(true);
		
		endInteraction = true;
	}

}

package model.mappa.events;

import controller.playState.Hitbox;
import model.IModel;
import view.SoundManager;

public class Notes extends Event {

	public Notes(Hitbox r, IModel m, int i) {
		super(r, m, i);
		message = "hai trovato degli appunti!";

	}

	@Override
	public void interact() {
		model.getController().getPlay().getPlayer().addNotes();
		
		model.getView().playSE(SoundManager.APPUNTI);
		model.getView().showMessageInUI(message);
		
		endInteraction = true;
	}

}

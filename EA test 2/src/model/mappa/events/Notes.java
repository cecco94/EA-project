package model.mappa.events;



import controller.playState.Hitbox;
import model.IModel;
import view.SoundManager;

public class Notes extends Event {

	public Notes(Hitbox r, IModel m) {
		super(r, m);
		message = "hai trovato degli appunti!";

	}

	@Override
	public void Interact() {
		model.getController().getPlay().getPlayer().addNotes();
		
		model.getView().playSE(SoundManager.APPUNTI);
		model.getView().getPlay().getUI().setMessage(message);
		model.getView().getPlay().getUI().setShowMessage(true);
		
		endInteraction = true;
	}

}

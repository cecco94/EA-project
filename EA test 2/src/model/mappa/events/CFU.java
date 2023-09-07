package model.mappa.events;



import controller.playState.Hitbox;
import model.IModel;
import view.SoundManager;

public class CFU extends Event{

	public CFU(Hitbox r, IModel m) {
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

package model.mappa.events;



import controller.playState.Hitbox;
import model.IModel;
import view.SoundManager;

public class Caffe extends Event{

	public Caffe(Hitbox r, IModel m) {
		super(r, m);
		message = "il caffe' aumenta la tua concentrazione!";
	}

	@Override
	public void interact() {
		model.getController().getPlay().getPlayer().addLife();
		
		model.getView().playSE(SoundManager.CAFFE);
		
		model.getView().getPlay().getUI().setMessage(message);
		model.getView().getPlay().getUI().setShowMessage(true);
		
		endInteraction = true;
		
	}

}

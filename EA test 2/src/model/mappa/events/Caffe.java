package model.mappa.events;

import java.awt.Rectangle;

import model.IModel;
import view.sound.SoundManager;

public class Caffe extends Event{

	public Caffe(Rectangle r, IModel m) {
		super(r, m);
		message = "il caffe' aumenta la tua concentrazione!";
	}

	@Override
	public void Interact() {
		model.getController().getPlay().getPlayer().addLife();
		
		model.getView().playSE(SoundManager.CAFFE);
		
		model.getView().getPlay().getUI().setScritta(message);
		model.getView().getPlay().getUI().setShowMessage(true);
		
		endInteraction = true;
		
	}

}

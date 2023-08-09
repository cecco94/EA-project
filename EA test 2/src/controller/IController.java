package controller;

import controller.playState.PlayStateController;
import controller.playState.PlayerController;
import model.IModel;
import view.main.IView;

public class IController {

	private IView view;
	private IModel model;
	private PlayStateController play;
	private PlayerController player;
	
	
	public IController() {
		play = new PlayStateController(this);
	}
	
	public void setView(IView v) {
		view = v;
	}
	
	public void setModel(IModel m) {
		model = m;
	}
	
	public IModel getModel() {
		return model;
	}
	
	public void setGameState(Gamestate newState) {
		Gamestate.state = newState;
	}
	
	public void updateGame() {
		switch(Gamestate.state) {
		case QUIT:
			System.exit(0);
			break;
		case PLAYING:
			play.update();
			break;			
		}

	}

	public PlayStateController getPlay() {	
		return play;
	}
	
	
}

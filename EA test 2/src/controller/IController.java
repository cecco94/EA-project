package controller;

import model.IModel;
import view.main.IView;

public class IController {

	private IView view;
	private IModel model;
	
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
}

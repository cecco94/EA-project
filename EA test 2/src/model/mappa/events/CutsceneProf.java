package model.mappa.events;

import controller.main.Gamestate;
import controller.playState.Hitbox;
import model.IModel;

public class CutsceneProf extends Event {

	public CutsceneProf(Hitbox r, IModel m) {
		super(r, m);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void Interact() {
		model.getController().getPlay().getPlayer().setCfu(0);
		model.getController().setGameState(Gamestate.BOSS_CUTSCENE);
	}

}

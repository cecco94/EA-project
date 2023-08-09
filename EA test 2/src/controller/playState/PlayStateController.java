package controller.playState;

import controller.IController;

//controlla ciò che accade nel gioco durante il play state
public class PlayStateController {
	
	private PlayerController player;
	private Collisions collisionCheck;
	private IController control;
	
	public PlayStateController(IController c) {
		control = c;
		collisionCheck = new Collisions(c); 
		player = new PlayerController(collisionCheck);
	}

	public PlayerController getPlayer() {
		return player;
	}
	
	public void update() {
		player.updatePos();
	}
	
	
}

package controller.playState;

import controller.IController;

//controlla ciò che accade nel gioco durante il play state
public class PlayStateController {
	
	private PlayerController player;
	private Collisions collisionCheck;
	private IController controller;
	
	public PlayStateController(IController c) {
		controller = c;
		collisionCheck = new Collisions(c); 
		player = new PlayerController(collisionCheck, controller);
	}

	public PlayerController getPlayer() {
		return player;
	}
	
	public void update() {
		int stanza = controller.getModel().getMappa().getMappaAttuale();
		player.updatePos(stanza);
		player.isAbovePassaggio();
	}
	
	public void setPlayerPos(int x, int y) {
		player.getHitbox().x = x;
		player.getHitbox().y = y;	
	}
	
	public void setMap(int newMap) {
		controller.getModel().getMappa().setMappaAttuale(newMap);
	}

	public void resumeGameAfterTransition() {
		controller.getModel().setNewRoom();
		player.getHitbox().x = controller.getModel().getNewXPos();
		player.getHitbox().y = controller.getModel().getNewYPos();	
		player.resetBooleans();
	}
	
	
}

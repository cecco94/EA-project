package controller.playState.entityController;

import controller.playState.PlayerController;
import view.main.GamePanel;

public class Projectile extends EntityController {

	public Projectile(int t, int x, int y, int w, int h) {
		super(t, x, y, w, h);
		speed = (int)(GamePanel.SCALE*2);
	}

	@Override
	public void update(PlayerController player) {
		updatePosition();
		checkSolid();
	}

	private void checkSolid() {
		
		
	}

	private void updatePosition() {
		hitbox.x += speed; 
		
	}

}

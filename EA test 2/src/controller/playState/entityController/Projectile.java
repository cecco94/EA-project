package controller.playState.entityController;

import java.awt.Rectangle;

import controller.playState.PlayStateController;
import view.main.GamePanel;

public class Projectile extends EntityController {

	public int indexInList;
	
	public Projectile(Rectangle r, PlayStateController p, int i) {
		super(r, p);
		speed = (int)(GamePanel.SCALE);
		indexInList = i;
	}

	@Override
	public void update(PlayerController player) {
		checkSolid();
		updatePosition();
	}

	private void checkSolid() {
		if(!play.getCollisionChecker().canMoveRight(hitbox)) {
			play.getPlayer().removeProjectile(indexInList);
			play.getController().getView().getPlay().getPlayer().removeProjectile(indexInList);
			System.out.println("hit");
		}
	}

	private void updatePosition() {
		hitbox.x += speed; 	
	}

	public Rectangle getHitbox() {
		return hitbox;
	}

}

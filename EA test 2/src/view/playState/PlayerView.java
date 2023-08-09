package view.playState;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import view.main.GamePanel;
import view.main.IView;

public class PlayerView {

	//private Rectangle hitbox;
	private IView view;
	
	public PlayerView(IView v) {
	//	hitbox = new Rectangle(6*GamePanel.TILES_SIZE , 3*GamePanel.TILES_SIZE, GamePanel.TILES_SIZE + GamePanel.TILES_SIZE/4, GamePanel.TILES_SIZE + GamePanel.TILES_SIZE/4);
		view = v;
	}
	
	public void draw(Graphics2D g2) {
		Rectangle hitbox = view.getController().getPlay().getPlayer().getHitbox();
		g2.setColor(Color.yellow);
		g2.fillRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
	}
}

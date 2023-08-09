package controller.playState;

import java.awt.Rectangle;

import controller.IController;
import model.mappa.Map;
import view.main.GamePanel;

public class Collisions {

	public static final int DESTRA = 0;
	public static final int SINISTRA = 1;
	public static final int SU = 2;
	public static final int SOTTO = 3;
	private IController control;
	
	public Collisions(IController c) {
		control = c;
	}
	
	public boolean canMoveHere(Rectangle hitboxPlayer, int direz) {
		boolean canMove = true;
		//in quale quadrato della mappa si trova il punto in alto a sinistra della hitbox
		int colonnaPlayer = hitboxPlayer.x/GamePanel.TILES_SIZE;
		int rigaPlayer = hitboxPlayer.y/GamePanel.TILES_SIZE;

		if(direz == SINISTRA) {
			int tileSopra = control.getModel().getMappa().getStrato(Map.BIBLIOTECA, 1)[rigaPlayer][colonnaPlayer];
			int tileSotto = control.getModel().getMappa().getStrato(Map.BIBLIOTECA, 1)[rigaPlayer+1][colonnaPlayer];

			if(tileSopra > 0) {
				int tileSopraX = control.getModel().getTilesetModel().getTile(tileSopra -1).getHitbox().x;
				int tileSopraY = control.getModel().getTilesetModel().getTile(tileSopra -1).getHitbox().y;
				
				Rectangle tileSopraHitbox = control.getModel().getTilesetModel().getTile(tileSopra -1).getHitbox();
				tileSopraHitbox.x += colonnaPlayer*GamePanel.TILES_SIZE;
				tileSopraHitbox.y += rigaPlayer*GamePanel.TILES_SIZE;
				
				if(hitboxPlayer.intersects(tileSopraHitbox))
					canMove = false;
				
				tileSopraHitbox.x = tileSopraX;
				tileSopraHitbox.y = tileSopraY;
			}
			
		}

		
		return canMove;
	}
	
}

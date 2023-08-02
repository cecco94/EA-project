package model.mappa;

import java.awt.Rectangle;

public class Tile {
	
	private boolean solid;
	private Rectangle hitbox;
	
	public Tile(String solid, String hitbox) {
		this.solid = Boolean.parseBoolean(solid.trim());
		if(this.solid == true)
			inizializzaHitbox(hitbox);	
	}

	private void inizializzaHitbox(String hitbox) {
		String[] valori = null;
		valori = hitbox.trim().split(",");
		int x = Integer.parseInt(valori[0]);
		int y = Integer.parseInt(valori[1]);
		int w = Integer.parseInt(valori[2]);
		int h = Integer.parseInt(valori[3]);
		this.hitbox = new Rectangle(x,y,w,h);
	}

	public boolean isSolid() {
		return solid;
	}

	public Rectangle getHitbox() {
		return hitbox;
	}

	public String toString() {
		String s = null;
		if (solid == true) {
			s = this.solid + "," +
			"( " + hitbox.x + ", " + hitbox.y +
			", " + hitbox.width + ", " + hitbox.height + " )";
		}
		else 
			s = "" + this.solid;
		
		return s;
	}
	
}

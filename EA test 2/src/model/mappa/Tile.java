package model.mappa;

import java.awt.Rectangle;

import view.main.GamePanel;

public class Tile {
	
	private boolean solid;
	private Rectangle hitbox;
	private String name;
	
	public Tile(String name, String solid, String hitbox) {
		this.name = name;
		this.solid = Boolean.parseBoolean(solid.trim());
		if(this.solid == true)
			inizializzaHitbox(hitbox);	
		else 
			this.hitbox = new Rectangle(0,0,0,0);
	}

	private void inizializzaHitbox(String hitbox) {
		String[] valori = null;
		valori = hitbox.trim().split(",");
		int x = (int)(Integer.parseInt(valori[0]) * GamePanel.SCALE );
		int y = (int)(Integer.parseInt(valori[1]) * GamePanel.SCALE );
		int w = (int)(Integer.parseInt(valori[2]) * GamePanel.SCALE );
		int h = (int)(Integer.parseInt(valori[3]) * GamePanel.SCALE );
		this.hitbox = new Rectangle(x,y,w,h);
	}

	public boolean isSolid() {
		return solid;
	}

	public Rectangle getHitbox() {
		return hitbox;
	}

	public String toString() {
		String s = this.name + this.solid + "," +
			"( " + hitbox.x + ", " + hitbox.y +
			", " + hitbox.width + ", " + hitbox.height + " )";
		
		return s;
	}
	
}

package model.mappa;

import java.awt.Rectangle;

import view.main.GamePanel;

public class Tile {
	
	private boolean solid; //controllare se serve
	private Rectangle hitbox;
	private String name;	//molto utile nel debugging
	
	public Tile(String name, String solid, String hitbox) {
		this.name = name;
		this.solid = Boolean.parseBoolean(solid.trim());
		if(this.solid == true)
			initHitbox(hitbox);	
		else 
			this.hitbox = new Rectangle(0,0,0,0);
	}

	private void initHitbox(String hitbox) {
		String[] values = null;
		values = hitbox.trim().split(",");
		int x = (int)(Integer.parseInt(values[0]) * GamePanel.SCALE );
		int y = (int)(Integer.parseInt(values[1]) * GamePanel.SCALE );
		int w = (int)(Integer.parseInt(values[2]) * GamePanel.SCALE );
		int h = (int)(Integer.parseInt(values[3]) * GamePanel.SCALE );
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

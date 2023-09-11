package model.mappa;



import controller.playState.Hitbox;
import model.IModel;

public class Tile {
	
	private boolean solid; //controllare se serve
	private Hitbox hitbox;
	private String name;	//molto utile nel debugging
	
	public Tile(String name, String solid, String hitbox) {
		this.name = name;
		this.solid = Boolean.parseBoolean(solid.trim());
		if(this.solid == true)
			initHitbox(hitbox);	
		else 
			this.hitbox = new Hitbox(0,0,0,0);
	}

	private void initHitbox(String hitbox) {
		String[] values = null;
		values = hitbox.trim().split(",");
		int x = (int)(Integer.parseInt(values[0]) * IModel.getGameScale() );
		int y = (int)(Integer.parseInt(values[1]) * IModel.getGameScale() );
		int w = (int)(Integer.parseInt(values[2]) * IModel.getGameScale() );
		int h = (int)(Integer.parseInt(values[3]) * IModel.getGameScale() );
		this.hitbox = new Hitbox(x,y,w,h);
	}

	public boolean isSolid() {
		return solid;
	}

	public Hitbox getHitbox() {
		return hitbox;
	}

	public String toString() {
		String s = this.name + this.solid + "," +
			"( " + hitbox.x + ", " + hitbox.y +
			", " + hitbox.width + ", " + hitbox.height + " )";
		
		return s;
	}
	
}

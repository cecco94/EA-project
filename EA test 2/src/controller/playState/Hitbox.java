package controller.playState;

public class Hitbox {
	
	//la posizione è esperessa in float in modo da rendere la velocità dei personaggi più regolabile
	public float x, y;
	public int width, height;
	

	 public Hitbox(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public boolean intersects(Hitbox r)  {
	     return r.width > 0 && r.height > 0 && width > 0 && height > 0
	    		 && r.x < x + width && r.x + r.width > x
	             && r.y < y + height && r.y + r.height > y;
	 }

}

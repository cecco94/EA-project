package controller.mappa;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

//IN COSTRUZIONE
public class Tile {

	private BufferedImage image;
	private Rectangle hitbox;
	private boolean solid = false;
	
	public Tile(BufferedImage img, Rectangle hb, Boolean isSolid) {
		image = img;
		hitbox = hb;
		solid = isSolid;
	}
	
	public boolean checkCollision(Rectangle r) {
		if(solid == true)
			return hitbox.intersects(r);
		else 
			return false;
	}
	
	public BufferedImage getImage() {
		return image;
	}
}

package view.mappa;

import java.awt.image.BufferedImage;

public class Tile {
	
	protected BufferedImage image;
	
	public Tile(BufferedImage img) {		
		image = img;
	}

	public BufferedImage getImage() {
		return image;
	}
	
}

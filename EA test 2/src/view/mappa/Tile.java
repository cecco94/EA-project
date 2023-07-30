package view.mappa;

import java.awt.image.BufferedImage;

public class Tile {

	private BufferedImage[] images;
	
	public Tile(BufferedImage img) {		//quando il tile ha una sola immagine
		images = new BufferedImage[1];
		images[0] = img;
	}
	
	public Tile(BufferedImage img1, BufferedImage img2) {	//quando ha una animazione
		images = new BufferedImage[2];
		images[0] = img1;
		images[1] = img2;
	}

	public BufferedImage[] getImages() {
		return images;
	}
	
	
}

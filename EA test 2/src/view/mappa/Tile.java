package view.mappa;

import java.awt.image.BufferedImage;

import view.ViewUtils;
import view.main.GamePanel;

//contiene solo un immagine ed un metodo per mostrarla
public class Tile {
	
	protected BufferedImage image;
	
	public Tile(BufferedImage img) {
		image = img;
		image = ViewUtils.scaleImage(image, GamePanel.TILES_SIZE, GamePanel.TILES_SIZE);
	}

	public BufferedImage getImage() {
		return image;
	}
	
}

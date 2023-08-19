package view.mappa;

import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

import view.ViewUtils;
import view.main.GamePanel;

//ha due immagini che cambiano creando una animazione, estende il tile in modo da poter fare una lista unica di tile
public class TileAnimated extends Tile {

	private BufferedImage secondImage;
	private int counter, frequenza, immagineAttule = 1;
	
	public TileAnimated(BufferedImage img1, BufferedImage img2) {
		super(img1);
		image = ViewUtils.scaleImage(img1, GamePanel.TILES_SIZE, GamePanel.TILES_SIZE);
		secondImage = img2;	
		secondImage = ViewUtils.scaleImage(img2, GamePanel.TILES_SIZE, GamePanel.TILES_SIZE);
		
		int min = 120;	//un secondo, perchè il gioco ha 120 fps
		int max = 360;	//tre secondi
		frequenza = ThreadLocalRandom.current().nextInt(min, max + 1);	//numero casuale
	}

	public TileAnimated(BufferedImage img1, BufferedImage img2, int freq) {
		super(img1);
		image = ViewUtils.scaleImage(img1, GamePanel.TILES_SIZE, GamePanel.TILES_SIZE);
		secondImage = img2;	
		secondImage = ViewUtils.scaleImage(img2, GamePanel.TILES_SIZE, GamePanel.TILES_SIZE);
	
		frequenza = freq;	//numero dato
	}
	
	public BufferedImage getImage() {
		selectImageToShow();
		
		if(immagineAttule == 1)
			return image;
		
		return secondImage;		
	}

	private void selectImageToShow() {
		counter++;
		if (counter >= frequenza) {
			
			if(immagineAttule == 1) 
				immagineAttule = 2;
			else 
				immagineAttule = 1;	
			
			counter = 0;
		}		
	}
}

package view.mappa;

import java.awt.image.BufferedImage;

public class TileAnimated extends Tile {

	private BufferedImage secondImage;
	private int counter, frequenza, immagineAttule = 1;
	
	public TileAnimated(BufferedImage img1, BufferedImage img2, int freq) {
		super(img1);
		secondImage = img2;	
		frequenza = freq;
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

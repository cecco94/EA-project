package view;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ViewUtils {

	public static BufferedImage scaleImage(BufferedImage original, int width, int height) {
		//like a blank canvas
	BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
		//whatever g2 will draw, save it in scaledImage 
	Graphics2D g2 = scaledImage.createGraphics();
	g2.drawImage(original, 0, 0, width, height, null);
	original = scaledImage;
	g2.dispose();	
	
	return scaledImage;	
	}
	
	public static int getCenteredXPos(int width){
		return GamePanel.GAME_WIDTH/2 - width/2;
	}
}

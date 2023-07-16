package view;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import view.main.GamePanel;

public class ViewUtils {

	public static BufferedImage scaleImage(BufferedImage original, float width, float height) {
		BufferedImage scaledImage = new BufferedImage((int)width, (int)height, original.getType());
		Graphics2D g2 = scaledImage.createGraphics();
		g2.drawImage(original, 0, 0, (int)width, (int)height, null);
		g2.dispose();	
		
		return scaledImage;	
	}
	
//	public static BufferedImage setAlphaImage(BufferedImage original, float alpha) {
//		BufferedImage transparentImage = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());
//		Graphics2D g2 = transparentImage.createGraphics();
//		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
//		g2.drawImage(transparentImage, 0, 0, null);	
//		g2.dispose();
//		return transparentImage;
//	}
	
	public static int getCenteredXPos(int width){
		return GamePanel.GAME_WIDTH/2 - width/2;
	}
	
	public static int getXforCenterText(String text, Graphics2D g2) {
		int textLenght = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();	
		return  GamePanel.GAME_WIDTH/2 - textLenght/2;
	}
}

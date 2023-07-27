package view.main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import view.ViewUtils;

public class MessaggioSubliminale {

	private int counterMessaggioSubliminale;
	private BufferedImage messaggioSubliminale;
	
	public MessaggioSubliminale() {
		caricaMessaggioSubliminale();
	}
	
	private void caricaMessaggioSubliminale() {
		messaggioSubliminale = null;
		BufferedImage temp = null;
		try {
			temp = ImageIO.read(getClass().getResourceAsStream("/subliminale.png"));
			messaggioSubliminale = ViewUtils.scaleImage(temp, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void disegnaMessaggioSubliminale(Graphics2D g2) {
		counterMessaggioSubliminale++;
		if(counterMessaggioSubliminale == 120) {
			g2.drawImage(messaggioSubliminale, 0, 0, null);
			counterMessaggioSubliminale = 0;
		}			
	}
	
}

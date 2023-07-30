package view.mappa;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import view.ViewUtils;
import view.main.GamePanel;

public class Tileset {

	private ArrayList<Tile> tiles;		//siccome per noi 0 = niente, la lista deve partire da 1!

	private String percorsoPrimoStrato = "/mappe/primoStrato.png";	
	private BufferedImage sourceImgPrimoStrato;
	private final int numTilePrimoStrato = 7;
	
	
	public Tileset() {
		tiles = new ArrayList<>();
		salvaImmaginiPrimoStrato();	
	}

	private void salvaImmaginiPrimoStrato() {
		sourceImgPrimoStrato = null;
		try {
			sourceImgPrimoStrato = ImageIO.read(getClass().getResourceAsStream(percorsoPrimoStrato));
			sourceImgPrimoStrato = ViewUtils.scaleImage(sourceImgPrimoStrato, GamePanel.TILES_SIZE, GamePanel.TILES_SIZE*numTilePrimoStrato);
			
			for(int i = 0; i < numTilePrimoStrato; i++)
				tiles.add(new Tile(sourceImgPrimoStrato.getSubimage(0, i*GamePanel.TILES_SIZE, GamePanel.TILES_SIZE, GamePanel.TILES_SIZE)));
		}
		catch(Exception e) {
			e.printStackTrace();			
		}
	}
	
	public Tile getTile(int i) {
		return tiles.get(i);
	}
}

package view.mappa;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import view.ViewUtils;
import view.main.GamePanel;
import view.mappa.Tile;

public class Tileset {

	private static ArrayList<Tile> tipiDITile;
	private static BufferedImage sourceImg;
	private static final int numTilePrimoStrato = 5;
	
	
	private void inizializzaTipiDiTile() {
		tipiDITile = new ArrayList<>();
		sourceImg = null;
		salvaImmaginiPrimoStrato();	
	}


	private void salvaImmaginiPrimoStrato() {
		try {
			sourceImg = ImageIO.read(getClass().getResourceAsStream("/mappe/primoStrato/primoStrato.png"));
			sourceImg = ViewUtils.scaleImage(sourceImg, GamePanel.TILES_SIZE, GamePanel.TILES_SIZE*numTilePrimoStrato);
			
			for(int i = 0; i < numTilePrimoStrato; i++)
				tipiDITile.add(new Tile(sourceImg.getSubimage(0, i*GamePanel.TILES_SIZE, GamePanel.TILES_SIZE, GamePanel.TILES_SIZE)));
		}
		catch(Exception e) {
			e.printStackTrace();			
		}
	}
}

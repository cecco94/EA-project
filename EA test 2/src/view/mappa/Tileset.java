package view.mappa;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import view.main.GamePanel;

public class Tileset {

	private ArrayList<Tile> tiles;	//lista completa dei tile, parte da 0, mentre il file parte da 1

	private String percorsoPrimoStrato =   "/mappe/strato1.png";	
	private String percorsoSecondoStrato = "/mappe/strato2NONanimato.png";
	private String percorsoSecondoStratoAnimato = "/mappe/strato2animato.png";
	private String percorsoTerzoStrato =   "/mappe/strato3.png";

	//private final int numTilePrimoStrato = 7;
	//private final int numTileSecondoStrato = 120;		//120;
	//private final int numImmagTileAnimati = 	9;			//6;
	//private final int numTileTerzoStrato = 67;
	
	public Tileset() {
		tiles = new ArrayList<>();
		salvaImmaginiPrimoStrato();	
		salvaImmaginiSecondoStrato();
		salvaImmaginiSecondoStratoAnimato();
		salvaImmaginiTerzoStrato();
	}

	public Tile getTile(int i) {
		return tiles.get(i);
	}
	
	private void salvaImmaginiPrimoStrato() {
		BufferedImage sourceImgStrato = null;
		try {
			sourceImgStrato = ImageIO.read(getClass().getResourceAsStream(percorsoPrimoStrato));
		}
		catch(Exception e) {
			e.printStackTrace();			
		}
		int numTilePrimoStrato = sourceImgStrato.getHeight()/32;
		System.out.println(numTilePrimoStrato);
		
		for(int i = 0; i < numTilePrimoStrato; i++)
			tiles.add(new Tile(sourceImgStrato.getSubimage(0, i*GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE)));
	}

	
	private void salvaImmaginiSecondoStrato() {
		BufferedImage sourceImgStrato = null;	
		try {
			sourceImgStrato = ImageIO.read(getClass().getResourceAsStream(percorsoSecondoStrato));	
		}
		catch(Exception e) {
			e.printStackTrace();			
		}
		int numTileSecondoStrato = sourceImgStrato.getHeight()/32;
		System.out.println(numTileSecondoStrato);
		
		for(int i = 0; i < numTileSecondoStrato; i++)
			tiles.add(new Tile(sourceImgStrato.getSubimage(0, i*GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE)));	
	}
	
	private void salvaImmaginiSecondoStratoAnimato() {
		BufferedImage sourceImgStrato = null;
		try {
			sourceImgStrato = ImageIO.read(getClass().getResourceAsStream(percorsoSecondoStratoAnimato));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		int numTileSecondoStratoAnimato = sourceImgStrato.getHeight()/32;
		System.out.println(numTileSecondoStratoAnimato);
		
		for(int i = 0; i < numTileSecondoStratoAnimato - 1; i += 2)
			tiles.add(new TileAnimated(sourceImgStrato.getSubimage(0, i*GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE),
								sourceImgStrato.getSubimage(0, (i+1) * GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE)) );	
	}
	
	private void salvaImmaginiTerzoStrato() {
		BufferedImage sourceImgStrato = null;
		try {
			sourceImgStrato = ImageIO.read(getClass().getResourceAsStream(percorsoTerzoStrato));
			int numTileTerzoStrato = sourceImgStrato.getHeight()/32;
			System.out.println(numTileTerzoStrato);
			
			for(int i = 0; i < numTileTerzoStrato; i++)
				tiles.add(new Tile(sourceImgStrato.getSubimage(0, i*GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE)));
		}
		catch(Exception e) {
			e.printStackTrace();			
		}
		
	}
	
}

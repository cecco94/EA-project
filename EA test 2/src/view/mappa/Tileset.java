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
	private String percorsoSecondoStratoAnimato = "/mappe/strato2SoloAnimato.png";
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
		BufferedImage temp = null;
		try {
			sourceImgStrato = ImageIO.read(getClass().getResourceAsStream(percorsoPrimoStrato));
		}
		catch(Exception e) {
			e.printStackTrace();			
		}
		int numTilePrimoStrato = sourceImgStrato.getHeight()/32;
	//	System.out.println(numTilePrimoStrato);
		
	//	int contatiletrato = 0;
		for(int i = 0; i < numTilePrimoStrato; i++) {
			temp = sourceImgStrato.getSubimage(0, i*GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE);
			tiles.add(new Tile(temp));
	//		contatiletrato++;
		}
	//	System.out.println(contatiletrato);
	}

	
	private void salvaImmaginiSecondoStrato() {
		BufferedImage sourceImgStrato = null;	
		BufferedImage temp = null;
		try {
			sourceImgStrato = ImageIO.read(getClass().getResourceAsStream(percorsoSecondoStrato));	
		}
		catch(Exception e) {
			e.printStackTrace();			
		}
		int numTileSecondoStrato = sourceImgStrato.getHeight()/32;
	//	System.out.println(numTileSecondoStrato);
		
	//	int contatilestrato = 0;
		for(int i = 0; i < numTileSecondoStrato; i++) {
			temp = sourceImgStrato.getSubimage(0, i*GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE);
			tiles.add(new Tile(temp));
		//	contatilestrato++;
		}
	//	System.out.println(contatilestrato);
	}
	
	private void salvaImmaginiSecondoStratoAnimato() {
		BufferedImage sourceImgStrato = null;
		BufferedImage temp1 = null;
		BufferedImage temp2 = null;

		try {
			sourceImgStrato = ImageIO.read(getClass().getResourceAsStream(percorsoSecondoStratoAnimato));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		int numTileSecondoStratoAnimato = sourceImgStrato.getHeight()/64;		//non 32 come prima, perchè ogni tile ha due immagini, sono 9 tile
	//	System.out.println(numTileSecondoStratoAnimato);
		
	//	int contatilestrato = 0;
		for(int i = 0; i < numTileSecondoStratoAnimato*2; i += 2) {
			temp1 = sourceImgStrato.getSubimage(0, i*GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE);
			temp2 = sourceImgStrato.getSubimage(0, (i+1)*GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE);
			tiles.add(new TileAnimated(temp1, temp2));
		//	contatilestrato++;
		}
	//	System.out.println(contatilestrato);
	}
	
	private void salvaImmaginiTerzoStrato() {
		BufferedImage sourceImgStrato = null;
		try {
			sourceImgStrato = ImageIO.read(getClass().getResourceAsStream(percorsoTerzoStrato));
			int numTileTerzoStrato = sourceImgStrato.getHeight()/32;
		//	System.out.println(numTileTerzoStrato);
			
		//	int contatilestrato = 0;
			for(int i = 0; i < numTileTerzoStrato; i++) {
				tiles.add(new Tile(sourceImgStrato.getSubimage(0, i*GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE)));
		//		contatilestrato++;
			}
		//	System.out.println(contatilestrato);
		}
		catch(Exception e) {
			e.printStackTrace();	
			
		}
	//	System.out.println(tiles.size());
	}
	
}

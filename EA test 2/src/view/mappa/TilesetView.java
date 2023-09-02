package view.mappa;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import view.main.GamePanel;

public class TilesetView {

	private ArrayList<Tile> tiles;	//lista completa dei tile, parte da 0, mentre il file parte da 1

	private String firstLayerPath =   "/mappe/strato1.png";	
	private String secondLayerPath = "/mappe/strato2NONanimato.png";
	private String secondAnimatedLayerPath = "/mappe/strato2SoloAnimato.png";
	private String thirdLayerPath =   "/mappe/strato3.png";
	
	public TilesetView() {
		tiles = new ArrayList<>();
		saveFirstLayerImages();	
		saveSecondLayerImages();
		saveSecondAnimatedLayerImages();
		saveThirdLayerImages();
	}

	public Tile getTile(int i) {
		return tiles.get(i);
	}
	
	private void saveFirstLayerImages() {
		BufferedImage imgZero = null;		//in questo modo la numerazione coincide con quella di tiled
		
		BufferedImage sourceLayerImage = null;
		BufferedImage temp = null;
		try {
			imgZero = ImageIO.read(getClass().getResourceAsStream("/mappe/000.png"));
			
			sourceLayerImage = ImageIO.read(getClass().getResourceAsStream(firstLayerPath));
		}
		catch(Exception e) {
			e.printStackTrace();			
		}
		tiles.add(new Tile(imgZero));
		
		int numberTileFirstLayer = sourceLayerImage.getHeight()/32;
		for(int i = 0; i < numberTileFirstLayer; i++) {
			temp = sourceLayerImage.getSubimage(0, i*GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE);
			tiles.add(new Tile(temp));
		}
	}

	private void saveSecondLayerImages() {
		BufferedImage sourceLayerImage = null;	
		BufferedImage temp = null;
		try {
			sourceLayerImage = ImageIO.read(getClass().getResourceAsStream(secondLayerPath));	
		}
		catch(Exception e) {
			e.printStackTrace();			
		}
		int numberTileSecondLayer = sourceLayerImage.getHeight()/32;
		for(int i = 0; i < numberTileSecondLayer; i++) {
			temp = sourceLayerImage.getSubimage(0, i*GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE);
			tiles.add(new Tile(temp));
		}
	}
	
	private void saveSecondAnimatedLayerImages() {
		BufferedImage sourceLayerImage = null;
		BufferedImage temp1 = null;
		BufferedImage temp2 = null;

		try {
			sourceLayerImage = ImageIO.read(getClass().getResourceAsStream(secondAnimatedLayerPath));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		int numberTileSecondAnimatedLayer = sourceLayerImage.getHeight()/64;		//non 32 come prima, perchè ogni tile ha due immagini, sono 9 tile
		
		//la tv va a parte perchè i 4 tiles cambiano insieme
		int freqTvAnimation = 240;
		for(int i = 0; i < 8; i+=2) {
			temp1 = sourceLayerImage.getSubimage(0, i*GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE);
			temp2 = sourceLayerImage.getSubimage(0, (i+1)*GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE);
			tiles.add(new TileAnimated(temp1, temp2, freqTvAnimation));
		}
		
		for(int i = 8; i < numberTileSecondAnimatedLayer*2; i += 2) {
			temp1 = sourceLayerImage.getSubimage(0, i*GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE);
			temp2 = sourceLayerImage.getSubimage(0, (i+1)*GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE);
			tiles.add(new TileAnimated(temp1, temp2));
		}
	}
	
	private void saveThirdLayerImages() {
		BufferedImage sourceLayerImage = null;
		try {
			sourceLayerImage = ImageIO.read(getClass().getResourceAsStream(thirdLayerPath));
			}
		catch(Exception e) {
			e.printStackTrace();		
		}
		int numberTileThirdLayer = sourceLayerImage.getHeight()/32;
		for(int i = 0; i < numberTileThirdLayer; i++) {
			tiles.add(new Tile(sourceLayerImage.getSubimage(0, i*GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE, GamePanel.TILES_DEFAULT_SIZE)));
		}
	}
	
}

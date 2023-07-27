package controller.mappa;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;
import static view.main.GamePanel.TILES_SIZE;

import view.ViewUtils;
import view.main.IView;

//IN COSTRUZIONE
public class MapManager {

	private IView view;
	private Tile[] tipiDiPiastrella;
	private int[][] mapTileNum;
	
	public MapManager() {
		initTiles();
		loadMap("/mappe/provaMappa.txt");
	}
	
	public void setup(int index, String imageName, boolean solid, int x, int y, int w, int h) {	//we scale images one time for good instead scale every time we draw
		try { 
			BufferedImage img = ImageIO.read(getClass().getResourceAsStream(imageName));
			img = ViewUtils.scaleImage(img, TILES_SIZE, TILES_SIZE);
			Rectangle collision = new Rectangle(x, y, w, h);
			tipiDiPiastrella[index] = new Tile(img, collision, solid);
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}

	private void initTiles() {
		tipiDiPiastrella = new Tile[3];
		setup(0, "/mappe/001.png", false, 0, 0, 0, 0);
		setup(1, "/mappe/002.png", false, 0, 0, 0, 0);
		setup(2, "/mappe/003.png", true, 1, 1, 1, 1);
		}
	
	public void loadMap(String path) {	
		mapTileNum = new int[4][16];
		try {
			InputStream	is = getClass().getResourceAsStream(path);				//approfondire
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			int col = 0;
			int row = 0;
			while(col < 16 && row < 4) {
				String line = br.readLine();
				while(col < 16) {
					String number[] = line.split(" ");
					int num = Integer.parseInt(number[col]);
					mapTileNum[col][row] = num;
					col++;
				}
					if(col == 16) {
						col = 0;
						row++;
					}
			}
			br.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g2) {
	//	g2.drawImage(tipiDiPiastrella[mapTileNum[1][4]].getImage(),0,0, null);
		//view.drawMap gli passiamo i parametri
	}

   
}

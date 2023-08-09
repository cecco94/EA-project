package model.mappa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TilesetModel {

	private ArrayList<Tile> tileset;
//	private ArrayList<Tile> tileStrato1;
//	private ArrayList<Tile> tileStrato2;
//	private ArrayList<Tile> tileStrato3;
	private String percorsoTileset = "/mappe/inizializzaTileset.txt";
	
	public TilesetModel() {
		inizializzaTipiDiTile();
	}
	
	private void inizializzaTipiDiTile() {		//per renderlo più funzionale, può leggere solo i tile dello strato 2
		
		tileset = new ArrayList<Tile>();
		
//		tileStrato1 = new ArrayList<Tile>();
//		tileStrato2 = new ArrayList<Tile>();
//		tileStrato3 = new ArrayList<Tile>();
//		int stratoAttuale = 1;
		
		InputStream	is = getClass().getResourceAsStream(percorsoTileset);				
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String s = null;
		String[] datiDaInserire = new String[2];
		
		try {
			while((s = br.readLine()) != null) {
//				if(!s.isEmpty() && s.contains("; ") && stratoAttuale == 1)	{	
//					datiDaInserire = s.split("; ");
//					tileStrato1.add(new Tile(datiDaInserire[0], datiDaInserire[1]));
//				}
//				
//				else if(!s.isEmpty() && s.contains("; ") && stratoAttuale == 2) {
//					datiDaInserire = s.split("; ");
//					tileStrato2.add(new Tile(datiDaInserire[0], datiDaInserire[1]));
//				}
//				
//				else if(!s.isEmpty() && s.contains("; ") && stratoAttuale == 3) {
//					datiDaInserire = s.split("; ");
//					tileStrato3.add(new Tile(datiDaInserire[0], datiDaInserire[1]));
//				}
//				
//				else if(!s.isEmpty() && s.contains("/")) {
//					stratoAttuale++;
//				}
				
				if(!s.isEmpty() && s.contains("; ")){
					datiDaInserire = s.split("; ");
					tileset.add(new Tile(datiDaInserire[0], datiDaInserire[1]));
				}
				
			}
			br.close();
	//		printTileset();
		} 
		catch (IOException e) {
			e.printStackTrace();
			}			
	}
	
	private void printTileset() {		//for debugging
		for(int i = 0; i < tileset.size(); i++)
			System.out.println(tileset.get(i).toString() + " " + (i));
		
//		for(Tile t : tileStrato1)
//			System.out.println(t.toString() + " strato 1");
//		
//		for(Tile t : tileStrato2)
//			System.out.println(t.toString() + " strato 2");
//		
//		for(Tile t : tileStrato3)
//			System.out.println(t.toString() + " strato 3");	
	}

	public Tile getTile(int index) {
		if(index >= 0 && index < tileset.size())
			return tileset.get(index);
		else return null;
	}

}

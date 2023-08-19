package model.mappa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TilesetModel {

	private ArrayList<Tile> tileset;
	private String percorsoTileset = "/mappe/inizializzaTileset.txt";
	
	public TilesetModel() {
		inizializzaTipiDiTile();
	}
	
	private void inizializzaTipiDiTile() {		
		
		tileset = new ArrayList<Tile>();
		
		InputStream	is = getClass().getResourceAsStream(percorsoTileset);				
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String s = null;
		String[] datiDaInserire = new String[3];
		
		try {
			while((s = br.readLine()) != null) {
				
				if(!s.isEmpty() && s.contains("; ")){
					datiDaInserire = s.split("; ");
					tileset.add(new Tile(datiDaInserire[0], datiDaInserire[1], datiDaInserire[2]) );
				}
				
			}
			br.close();
		//	printTileset();
		} 
		catch (IOException e) {
			e.printStackTrace();
			}			
	}
	
	@SuppressWarnings("unused")
	private void printTileset() {		//for debugging
		for(int i = 0; i < tileset.size(); i++)
			System.out.println(tileset.get(i).toString() + " " + (i));
	}

	public Tile getTile(int index) {
		if(index >= 0 && index < tileset.size())
			return tileset.get(index);
		else return null;
	}

}

package model;

import model.mappa.Map;
import model.mappa.Tileset;

public class IModel {

	private Map mappa;
	private Tileset tiles;
	
	
	
	public IModel() {
		mappa = new Map();
		tiles = new Tileset();
	}

	public Map getMappa() {
		return mappa;
	}

	public Tileset getTiles() {
		return tiles;
	}
}

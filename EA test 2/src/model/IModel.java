package model;

import model.mappa.Map;
import model.mappa.TilesetModel;

public class IModel {

	private Map mappa;
	private TilesetModel tiles;
	
	
	
	public IModel() {
		mappa = new Map();
		tiles = new TilesetModel();
	}

	public Map getMappa() {
		return mappa;
	}

	public TilesetModel getTilesetModel() {
		return tiles;
	}
}

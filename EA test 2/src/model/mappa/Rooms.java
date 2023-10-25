package model.mappa;

import view.SoundManager;

public enum Rooms {

	BIBLIOTECA(Rooms.INDEX_BIBLIOTECA, SoundManager.BIBLIOTECA), 
	DORMITORIO(Rooms.INDEX_DORMITORIO, SoundManager.DORMITORIO), 
	AULA_STUDIO(Rooms.INDEX_AULA_STUDIO, SoundManager.AULA_STUDIO),
	TENDA(Rooms.INDEX_TENDA, SoundManager.TENDA),
	LABORATORIO(Rooms.INDEX_LABORATORIO,SoundManager.LABORATORIO),
	STUDIO_PROF(Rooms.INDEX_STUDIO_PROF, SoundManager.BOSS);
	
	Rooms(int i, int music) {
		mapIndex = i;
		musicIndex = music;
	}

	public static Rooms currentRoom = STUDIO_PROF;
	public final static int numStanze = 6;
	public static final int INDEX_BIBLIOTECA = 0,  INDEX_DORMITORIO = 1, INDEX_AULA_STUDIO = 2, 
							INDEX_TENDA = 3, INDEX_LABORATORIO = 4, INDEX_STUDIO_PROF = 5;

	public int mapIndex, musicIndex;
	
	//restituisce la stanza associata al numero
	public static Rooms getRoomLinkedToNumber(int i) {
		if(i == Rooms.INDEX_BIBLIOTECA)
			return Rooms.BIBLIOTECA;
		
		else if(i == Rooms.INDEX_AULA_STUDIO)
			return Rooms.AULA_STUDIO;
		
		else if(i == Rooms.INDEX_TENDA)
			return Rooms.TENDA;
		
		else if(i == Rooms.INDEX_LABORATORIO)
			return Rooms.LABORATORIO;
		
		else if(i == Rooms.INDEX_STUDIO_PROF)
			return Rooms.STUDIO_PROF;
		else
			return Rooms.DORMITORIO;
	}
	
} 
package model.mappa;

import view.sound.SoundManager;

public enum Stanze {

	BIBLIOTECA(Stanze.INDEX_BIBLIOTECA, SoundManager.BIBLIOTECA), 
	DORMITORIO(Stanze.INDEX_DORMITORIO, SoundManager.DORMITORIO), 
	AULA_STUDIO(Stanze.INDEX_AULA_STUDIO, SoundManager.AULA_STUDIO),
	TENDA(Stanze.INDEX_TENDA, SoundManager.TENDA),
	LABORATORIO(Stanze.INDEX_LABORATORIO,SoundManager.LABORATORIO),
	STUDIO_PROF(Stanze.INDEX_STUDIO_PROF, SoundManager.BOSS);
	
	Stanze(int i, int music) {
		indiceMappa = i;
		indiceMusica = music;
	}

	public static Stanze stanzaAttuale = AULA_STUDIO;
	public final static int numStanze = 6;
	public static final int INDEX_BIBLIOTECA = 0,  INDEX_DORMITORIO = 1, INDEX_AULA_STUDIO = 2, 
							INDEX_TENDA = 3, INDEX_LABORATORIO = 4, INDEX_STUDIO_PROF = 5;

	public int indiceMappa, indiceMusica;
	
	public static Stanze getStanzaAssociataAlNumero(int i) {
		if(i == Stanze.INDEX_BIBLIOTECA)
			return Stanze.BIBLIOTECA;
		
		else if(i == Stanze.INDEX_AULA_STUDIO)
			return Stanze.AULA_STUDIO;
		
		else if(i == Stanze.INDEX_TENDA)
			return Stanze.TENDA;
		
		else if(i == Stanze.INDEX_LABORATORIO)
			return Stanze.LABORATORIO;
		
		else if(i == Stanze.INDEX_STUDIO_PROF)
			return Stanze.STUDIO_PROF;
		else
			return Stanze.DORMITORIO;
	}
	
} 
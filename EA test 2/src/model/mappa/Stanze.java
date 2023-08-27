package model.mappa;

import view.sound.SoundManager;

public enum Stanze {

	BIBLIOTECA(Map.BIBLIOTECA, SoundManager.BIBLIOTECA), 
	DORMITORIO(Map.DORMITORIO, SoundManager.DORMITORIO), 
	AULA_STUDIO(Map.AULA_STUDIO, SoundManager.AULA_STUDIO),
	TENDA(Map.TENDA, SoundManager.TENDA),
	LABORATORIO(Map.LABORATORIO,SoundManager.LABORATORIO);
	
	Stanze(int i, int music) {
		indiceMappa = i;
		indiceMusica = music;
	}

	public static Stanze stanzaAttuale = AULA_STUDIO;
	public final static int numStanze = 5;
	
	public int indiceMappa, indiceMusica;
	
	public static Stanze getStanzaAssociataAlNumero(int i) {
		if(i == Map.BIBLIOTECA)
			return Stanze.BIBLIOTECA;
		else if(i == Map.AULA_STUDIO)
			return Stanze.AULA_STUDIO;
		else if(i == Map.TENDA)
			return Stanze.TENDA;
		else if(i == Map.LABORATORIO)
			return Stanze.LABORATORIO;
		else
			return Stanze.DORMITORIO;
	}
	
} 
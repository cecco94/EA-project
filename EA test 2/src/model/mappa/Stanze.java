package model.mappa;

import view.sound.SoundManager;

public enum Stanze {

	BIBLIOTECA(Map.BIBLIOTECA, SoundManager.BIBLIOTECA), 
	DORMITORIO(Map.DORMITORIO, SoundManager.DORMITORIO), 
	AULA_STUDIO(Map.AULA_STUDIO, SoundManager.SALA_STUDIO);
	
	Stanze(int i, int music) {
		indiceNellaMappa = i;
		indiceMusica = music;
	}

	public static Stanze stanzaAttuale = AULA_STUDIO;
	
	public int indiceNellaMappa, indiceMusica;
	
	public static Stanze getStanzaAssociataAlNumero(int i) {
		if(i == Map.BIBLIOTECA)
			return Stanze.BIBLIOTECA;
		else if(i == Map.AULA_STUDIO)
			return Stanze.AULA_STUDIO;
		else
			return Stanze.DORMITORIO;
	}
	
} 
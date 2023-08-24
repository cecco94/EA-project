package controller.main;


public enum Gamestate {

	START_TITLE, MAIN_MENU, LOAD_GAME, PLAYING, SELECT_AVATAR, OPTIONS, QUIT, TRANSITION_STATE, TRANSITION_ROOM;

	public static Gamestate state = START_TITLE;

}

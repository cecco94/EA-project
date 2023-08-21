package controller.main;


public enum Gamestate {

	START_TITLE, MAIN_MENU, LOAD_GAME, PLAYING, SELECT_AVATAR, OPTIONS, QUIT, TRANSITION, TRANSITION_ROOM;

	public static Gamestate state = START_TITLE;

}

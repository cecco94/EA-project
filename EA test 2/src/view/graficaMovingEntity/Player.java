package view.graficaMovingEntity;

import java.awt.image.BufferedImage;

import view.main.IView;

//IN COSTRUZIONE
public class Player {
	private IView view;
	private BufferedImage[][] sprites;
	public static final int ANI_SPEED = 25;
	public static final int IDLE = 0;
	public static final int RUNNING = 1;
	public static final int JUMP = 2;
	public static final int FALLING = 3;
	public static final int ATTACK = 4;
	public static final int HIT = 5;
	public static final int DEAD = 6;


	
	
	public static int GetSpriteAmount(int player_action) {
		switch (player_action) {
		case DEAD:
			return 8;
		case RUNNING:
			return 6;
		case IDLE:
			return 5;
		case HIT:
			return 4;
		case JUMP:
		case ATTACK:
			return 3;
		case FALLING:
		default:
			return 1;
		}
	}
	
}

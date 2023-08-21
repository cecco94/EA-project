package view.playState.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import view.IView;
import view.playState.player.PlayerView;

public class ProjectileView { 

	public Rectangle borders;
	public int index;
	private IView view;
//	private BufferedImage[] animazione;
	
	private int counter;
	private int animationIndex;
	private final int animationSpeed = 120;
	
	public ProjectileView(Rectangle hit, int index, IView v) {
		borders = hit;
		this.index = index;
		view = v;
	}
	
	public void draw(Graphics2D g2, int playerx, int playery) {
		counter++;
		//decidiamo quando disegnare
//		if(counter >= animationSpeed) {
//			animationIndex++;
//			
//			//decidiamo cosa disegnare
//			if(animationIndex > 1) {
//				animationIndex = 0;
//			}
			
			//decidiamo dove disegnarlo
			int xposInMap = view.getController().getPlay().getPlayer().getAppunti()[index].getHitbox().x;
			int yposInMap = view.getController().getPlay().getPlayer().getAppunti()[index].getHitbox().y;
			
			int distanzax = playerx - xposInMap;
			int distanzay = playery - yposInMap;
			
			int xPosOnScreen = PlayerView.xOnScreen - distanzax + PlayerView.xOffset;	
			int yPosOnScreen = PlayerView.yOnScreen - distanzay + PlayerView.yOffset;
			
			g2.setColor(Color.red);
			g2.drawRect(xPosOnScreen, yPosOnScreen, borders.width, borders.height);
			
//		}
//		else
//			counter = 0;
			
	}
	
}

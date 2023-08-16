package view.menu.avatarSelection;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import controller.Gamestate;
import view.ViewUtils;
import view.main.IView;
import view.menu.AbstractMenuButton;
import view.playState.player.Avatar;
import view.playState.player.PlayerView;

// sarebbero le immagini con mario e peach
public class AvatarButton extends AbstractMenuButton {
	
	private int animationCounter, numSprite; 
	private final int animationSpeed = 20;
	private Avatar avatar;
	private int animationLenght = 4;
	private BufferedImage temp = null;

	
	public AvatarButton(Avatar gender, Rectangle dim, IView v) {
		avatar = gender;
		super.setBounds(dim.x, dim.y,dim. width, dim.height);	
		newState = Gamestate.TRANSITION;
		view = v;
	}
	
	// prima li disegna trasparenti, poi se ci passa sopra il mouse diventano del tutto visibili.
	//il valore alpha è un float che varia tra 0 e 1
	public void draw(Graphics2D g2) {
		//disegna personaggio con animazione da fermo semitrasparente
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		//g2.drawImage(mouseOverImage, bounds.x, bounds.y, null);
		
		if(mouseOver) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
			//disegna personaggio coon animazione mentre corre in avanti
		}
			
		drawAnimation(g2);
	}

	private void drawAnimation(Graphics2D g2) {
		animationCounter++;
				
		if (animationCounter > animationSpeed) {
			numSprite++;	
			
			if(numSprite >= animationLenght)
				numSprite = 0;	
			
			animationCounter = 0;
		}
		
		if(mouseOver) {
			temp = ViewUtils.scaleImage(PlayerView.getAnimation(avatar, PlayerView.getRun(), PlayerView.getDOWN(), numSprite), bounds.width, bounds.height);
			g2.drawImage(temp, bounds.x, bounds.y, null);
		}
		else {
			temp = ViewUtils.scaleImage(PlayerView.getAnimation(avatar, PlayerView.getIDLE(), PlayerView.getDOWN(), numSprite), bounds.width, bounds.height);
			g2.drawImage(temp, bounds.x, bounds.y, null);
		}
	}

	@Override
	public void reactToMouse(MouseEvent e) {
		view.getPlay().getPlayer().setAvatarType(avatar);
		view.getPlay().getPlayer().resetAnimation();
		view.changeGameState(newState);
	}

}

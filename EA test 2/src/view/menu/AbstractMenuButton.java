package view.menu;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import controller.Gamestate;
import view.main.IView;

//classe astratta che contiene tutti i meetodi e le risorse comuni a tutti i tipi di bottone
public abstract class AbstractMenuButton {

	protected IView view;
	protected BufferedImage mouseOverImage, mousePressedImage, mouseAwayImage;
	protected boolean mouseOver, mousePressed;
	protected Rectangle bounds;
	protected Gamestate newState;
	
	public void setXpos(int i) {
		bounds.x = i;
	}
	
	public int getWidth() {
		return bounds.width;
	}
	
	protected void setBounds(int x, int y, int width, int height) {
		bounds = new Rectangle(x, y, width, height);
	}
	
	public boolean checkIfMouseIsIn(MouseEvent e) {
		return bounds.contains(e.getX(), e.getY());
	}
	
	public void resetBooleans(){
		mouseOver = false;
		mousePressed = false;
	}
	
	public void setMouseOver(Boolean b) {
		mouseOver = b;
	}
	
	public boolean isMousePressed() {
		return mousePressed;
	}
	
	public void setMousePressed(boolean mousePressed) {
		this.mousePressed = mousePressed;
	}
	
	public Gamestate getButtonState() {
		return newState;
	}
	
	public boolean getMouseOver() {
		return mouseOver;
	}
	
	public Rectangle getBounds() {
		return bounds;
	}

	public abstract void draw(Graphics2D g2);
	
	public abstract void reactToMouse(MouseEvent e);

}

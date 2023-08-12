package view.main;

import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import view.ViewUtils;

public class GameWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GameWindow(GamePanel gp) {
		setTitle("ENGINEERING ADVENTURE");
	//	setUndecorated(true);		per togliere il sopra della finestra
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		setGameCursor(gp);
		getContentPane().add(gp);
		pack();
		setLocationRelativeTo(null);
		gestisciPerditaFocus();
	}

	//questo metodo serve per quando il gioco perde il focus
	private void gestisciPerditaFocus() {
		addWindowFocusListener(new WindowFocusListener() { 
			@Override
			public void windowLostFocus(WindowEvent e) {
				//gamePanel.getGame().windowFocusLost();	resetta i booleandella direzione del personaggio
			}
			@Override
			public void windowGainedFocus(WindowEvent e) {
				
			}
		});
	}

	//per avere quella adorabile freccetta blu
	private void setGameCursor(GamePanel gp) {	
		BufferedImage mouseIcon = null;
		try {
			mouseIcon = ImageIO.read(getClass().getResourceAsStream("/cursor.png"));
			mouseIcon = ViewUtils.scaleImage(mouseIcon, GamePanel.SCALE*15, GamePanel.SCALE*20);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		setCursor(Toolkit.getDefaultToolkit().createCustomCursor(mouseIcon,new Point(gp.getX(),gp.getY()),"custom cursor"));
	}
}

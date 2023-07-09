package view;

import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class GameWindow extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GameWindow(GamePanel gp) {
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("res/cursor.png").getImage(),new Point(0,0),"custom cursor"));
		add(gp);
		pack();
		setLocationRelativeTo(null);
		addWindowFocusListener(new WindowFocusListener() {
			
			@Override
			public void windowLostFocus(WindowEvent e) {
				//gamePanel.getGame().windowFocusLost();	resetta i booleandella direzione del personaggio
			}

			@Override
			public void windowGainedFocus(WindowEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}
}

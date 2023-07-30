package controller;

import model.IModel;
import view.gameBegin.SplashScreenGame;
import view.main.IView;

public class GameLoop implements Runnable {
	private SplashScreenGame caricamento;
	
	private Thread gameThread;
	private int FPS_SET = 120;
	private int UPS_SET = 200;
	
	private IView view;
	private IController controller;
	private IModel model;

	public GameLoop() {
		
	//	System.out.println("thread di caricamento  " + Thread.currentThread().getId());
		initClasses();
		startGameLoop();
	}

	private void initClasses() {
		
		caricamento = new SplashScreenGame();	
		controller = new IController();
		caricamento.showProgress(50);
		model = new IModel();
		controller.setModel(model);
		caricamento.showProgress(80);
		view = new IView(controller);
		view.setModel(model);
		controller.setView(view);
		caricamento.dispose();
	}

	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	// è l'istruzione che viene eseguita in un altro thread, in questo modo tutto il gioco 
	// viene eseguito in un altro thread
	@Override
	public void run() {		

		double timePerFrame = 1000000000.0 / FPS_SET;
		double timePerUpdate = 1000000000.0 / UPS_SET;

		long previousTime = System.nanoTime();

		int frames = 0;
		int updates = 0;
		long lastCheck = System.currentTimeMillis();

		double deltaU = 0;
		double deltaF = 0;

		while (gameThread != null) {
			long currentTime = System.nanoTime();

			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;

			if (deltaU >= 1) {
				update();
				updates++;
				deltaU--;
			}

			if (deltaF >= 1) {
				render();
				frames++;
				deltaF--;
			}

			if (System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
			//	System.out.println("FPS: " + frames + " | UPS: " + updates);  per debugging
				frames = 0;
				updates = 0;

			}
		}
	}

	private void render() {
		view.draw();	
	}

	private void update() {
		// TODO Auto-generated method stub		ancora non fa nulla
		
	}
}

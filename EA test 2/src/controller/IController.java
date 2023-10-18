package controller;

import controller.main.Gamestate;
import controller.playState.Hitbox;
import controller.playState.PlayStateController;
import model.IModel;
import model.mappa.Rooms;
import view.IView;
import view.main.GamePanel;

public class IController {

	private IView view;
	private IModel model;
	private PlayStateController play;	
	
	public IController() {
		play = new PlayStateController(this);
	}
	
	public void updateGame() {
		switch(Gamestate.state) {
		case QUIT:
			System.exit(0);
			break;
		case PLAYING:
			play.update();
			break;
		default:
			break;			
		}

	}
	
	public boolean isEnemyHitted(int index) {
		return play.getRoom(Rooms.currentRoom.mapIndex).getEnemy().get(index).isHitted();
	}
	
	public int getEnemyLife(int index) {
		return play.getRoom(Rooms.currentRoom.mapIndex).getEnemy().get(index).getLife();
	}
	
	public Hitbox getEnemyHitbox(int index) {
		return play.getRoom(Rooms.currentRoom.mapIndex).getEnemy().get(index).getHitbox();
	}
	
	public Hitbox getNPCHitbox(int index) {
		return play.getRoom(Rooms.currentRoom.mapIndex).getNPC().get(index).getHitbox();
	}
	
	public int getNpcDirection(int index) {
		return play.getRoom(Rooms.currentRoom.mapIndex).getNPC().get(index).getCurrentDirection();
	}
	
	public int getNpcAction(int index) {
		return play.getRoom(Rooms.currentRoom.mapIndex).getNPC().get(index).getCurrentAction();
	}
	
	public int getEnemyDirection(int index) {
		return play.getRoom(Rooms.currentRoom.mapIndex).getEnemy().get(index).getCurrentDirection();
	}
	
	public int getEnemyAction(int index) {
		return play.getRoom(Rooms.currentRoom.mapIndex).getEnemy().get(index).getCurrentAction();
	}
	
	public Hitbox getPlayerHitbox() {
		return play.getPlayer().getHitbox();
	}
	
	public int getPlayerNotes() {
		return play.getPlayer().getNotes();
	}
	
	public int getPlayerCfu() {
		return play.getPlayer().getCfu();
	}
	
	public int getNumberOfNpcInRoom(int roomIndex) {
		return play.getRoom(roomIndex).getNPC().size();
	}
	
	public int getNumberOfEnemiesInRoom(int roomIndex) {
		return play.getRoom(roomIndex).getEnemy().size();
	}
	
	public String getNpcType(int roomIndex, int npcIndex) {
		return play.getRoom(roomIndex).getNPC().get(npcIndex).getType();
	}
	
	public String getEnemyType(int roomIndex, int enemyIndex) {
		return play.getRoom(roomIndex).getEnemy().get(enemyIndex).getType();
	}
	
	public void goToPauseState() {
		setGameState(Gamestate.PAUSE);
	}
	
	public void startPlayerThrow(){
		play.startPlayerThrowing();
	}
	
	public void startPlayerInteract() {
		play.startPlayerInteract();
	}
	
	public void startPlayerAttack() {
		play.startPlayerAttacking();
	}
	
	public void startPlayerParry() {
		play.startPlayerParring();
	}
	
	public void stopPlayerParring() {
		play.stopPlayerParring();
	}
	
	public void stopPlayerThrowing() {
		play.stopPlayerThrowing();
	}
	
	public void stopPlayerInteracting() {
		play.stopPlayerInteracting();
	}
	
	public void changePlayerDirection(int dir){
		play.getPlayer().choiceDirection(dir);
	}
	
	public void resetPlayerDirection(int direction) {
		play.getPlayer().resetDirection(direction);
	}
	
	public void resetPlayerBooleans() {
		play.getPlayer().resetBooleans();
	}
	
	public void resumeGameAfterTransition() {
		play.resumeGameAfterTransition();
	}
	
	public void addEnemy(int index, int xPos, int yPos, String type) {
		play.getRoom(index).addEnemy(type, xPos, yPos);
	}
	
	public void addNPC(int index, int xPos, int yPos, String type) {
		play.getRoom(index).addNPC(type, xPos, yPos);
	}
	
	public void setGameState(Gamestate newState) {
		Gamestate.state = newState;
	}
	
	public void setView(IView v) {
		view = v;
	}
	
	public int getIndexOfInteractingEntity() {
		return play.getPlayer().getIndexOfEntityInteract();
	}
	
	public IView getView() {
		return view;
	}
	
	public void setModel(IModel m) {
		model = m;
	}
	
	public IModel getModel() {
		return model;
	}
	
	public PlayStateController getPlay() {	
		return play;
	}
	
	public int getTileSize() {
		return GamePanel.TILES_SIZE;
	}
	
	public float getGameScale() {
		return GamePanel.SCALE;
	}
	
}

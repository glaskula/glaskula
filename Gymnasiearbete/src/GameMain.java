
public class GameMain {
	
	public static void gameStart() {
		GameView gv = new GameView();
		GameLogic gl = new GameLogic(gv);
		gl.gameLoop();
	}

	public static void main(String[] args) {
		gameStart();
	}
}



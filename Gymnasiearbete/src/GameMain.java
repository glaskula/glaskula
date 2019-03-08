
public class GameMain {

	public static void main(String[] args) {
	     GameView gv = new GameView();
	   	 GameLogic gl = new GameLogic(gv);
	   	 gl.gameLoop();
	    }
}



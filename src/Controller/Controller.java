package Controller;


public abstract class Controller {
    GameController3D gameController3D;
    public String nickname = "";
    public Controller(){

    }
    public abstract void GameEnded();
    public abstract void StartGame();
    public abstract void GameStopped();
    public abstract void ShowLeaderBoard();
    public abstract void ShowMainMenu();
    public void Exit(){
        System.exit(0);
    }
}

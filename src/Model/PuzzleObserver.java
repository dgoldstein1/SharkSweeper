package Model;

/**
 * Created by Dave on 17/02/2015.
 */
public interface PuzzleObserver {
    void squarePush(int id,int surroundMines,boolean isBomb);
    void playerDied(int id);
    void notifyFlagged(int id);
    void notifyUntouched(int id);
    void notifyGameWon();
    void notifyMinePushed(int id);
}

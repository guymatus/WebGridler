/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * interface of the game so that the UI class can communicate with the logic and
 * engine of the game.
 *
 * @author Danny
 */
public interface IGameManager {

    public Player getPlayingPlayer();

    void addPlayer(Player player);

    void removePlayer(Player player);

    Player getFirstPlayer();

    LinkedList<Player> getLstPlayers();

    public void passTurn();

    public String getPlayersCount();
    int playersCount();
    public void loadXml(InputStream stream) throws Exception;

    int undoMove();

    public void executeMove(ArrayList<Position> movesArr, Board board, UserSquareStatus newStatus);

    void setGameRounds(int numberOfRounds);

    int getGameRounds();

    void setCurrentGameRound(int currentGameRound);

    int getCurrentGameRound();

    public void executeComputerMove();
    
    public String getGameTitle();
    
    public void setGameTitle(String gameTitle);

    public String getWinnerNameAndDetails();
    
    public void setOrganizer(String organizer);
    
     public int getBoardCols();
     
      public int getBoardRows();
      public Board getBoard();
 
}

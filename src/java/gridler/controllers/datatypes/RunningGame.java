/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gridler.controllers.datatypes;

/**
 *
 * @author Guy Matus
 */
public class RunningGame {

    String title;
    String organizer;
    int playerCount;
    int totalMoves;
    int rowSize;
    int colSize;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    public int getTotalMoves() {
        return totalMoves;
    }

    public void setTotalMoves(int totalMoves) {
        this.totalMoves = totalMoves;
    }

    public int getRowSize() {
        return rowSize;
    }

    public void setRowSize(int rowSize) {
        this.rowSize = rowSize;
    }

    public int getColSize() {
        return colSize;
    }

    public void setColSize(int colSize) {
        this.colSize = colSize;
    }

    public RunningGame(String title, String organizer, int playerCount, int totalMoves, int rowSize, int colSize) {
        this.title = title;
        this.organizer = organizer;
        this.playerCount = playerCount;
        this.totalMoves = totalMoves;
        this.rowSize = rowSize;
        this.colSize = colSize;
    }
}

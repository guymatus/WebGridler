/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

/**
 * 
 * @author Guy
 */
public class Board {

    private int rowLength;
    private int colLength;
    private Square squares[][]; // the board
    private Slice[] rowSlices;  //array of slices that represents the blocks for the rows
    private Slice[] colSlices; // array of slices that represents the blocks for the columns

    public void setRowSlices(Slice[] rowSlices) {
        this.rowSlices = rowSlices;
    }

    public void setColSlices(Slice[] colSlices) {
        this.colSlices = colSlices;
    }

    public Slice[] getRowSlices() {
        return rowSlices;
    }

    public Slice[] getColSlices() {
        return colSlices;
    }
   //this function calculates the slice with maximum number of blocks
    public int getMaxNumOfBlocks(Slice[] slices) {
        int maxNumOfBlocks = 0;
        for (int i = 0; i < slices.length; i++) {
            if (slices[i].getBlock().split(",").length > maxNumOfBlocks) {
                maxNumOfBlocks = slices[i].getBlock().split(",").length;
            }
        }

        return maxNumOfBlocks;
    }

    public Board(int rowLength, int colLength) {
        this.rowLength = rowLength;
        this.colLength = colLength;

        // intialize squares
        squares = new Square[rowLength][colLength];
        for (int i = 0; i < rowLength; i++) {
            for (int j = 0; j < colLength; j++) {
                squares[i][j] = new Square();
                squares[i][j].setUserState(UserSquareStatus.UNDEFINED);              
            }
        }
    }

    public int getRowLength() {
        return rowLength;
    }

    public void setRowLength(int rowLength) {
        this.rowLength = rowLength;
    }

    public int getColLength() {
        return colLength;
    }

    public void setColLength(int colLength) {
        this.colLength = colLength;
    }

    public Square[][] getSquares() {
        return squares;
    }

    public void setSquares(Square[][] squares) {
        this.squares = squares;
    }

}

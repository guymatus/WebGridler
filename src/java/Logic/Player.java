/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import java.util.ArrayList;

/**
 *
 * @author Danny
 */
public class Player {

    private final String m_name;
    private final boolean m_isHuman;
    Board m_board;
    private int m_score = 0;
    private int m_currentMoveNumber = 0;
    private boolean m_isTurn = false;
    private ArrayList<String> m_movesArr = new ArrayList();   // array list with all the moves position's(x , y ) 
    private String m_moves = ""; //  all moves including new square status for each move to use in label of  moves history
    private int m_totalMoves = 0;
    private ArrayList<String> m_CommentsArr = new ArrayList(); // array list for all moves comments  

    public String getLastComment() {
        if (m_CommentsArr.size() > 0) {
            return m_CommentsArr.get(m_CommentsArr.size() - 1);
        } else {
            String res = "";
            return res;
        }
    }

    public void removeLastComment() {
        if (this.m_CommentsArr.size() > 0) {
            this.m_CommentsArr.remove(m_CommentsArr.size() - 1);
        }
    }

    public void setComment(String Comment) {
        int index = this.m_CommentsArr.size();
        this.m_CommentsArr.add(index, Comment);

    }

    public String getMoves() {
        return m_moves;
    }

    public void setMoves(String move) {
        this.m_moves = move;
    }

    public int getTotalMoves() {
        return m_totalMoves;
    }

    public void setTotalMoves(int totalMoves) {
        this.m_totalMoves = totalMoves;
    }

    public ArrayList<String> getMovesArr() {
        return m_movesArr;
    }

    public void setMovesArr(String moves, int index) {
        this.m_movesArr.add(index, moves);
    }

    public int getCurrentMoveNumber() {
        return m_currentMoveNumber;
    }

    public void setCurrentMoveNumber(int currentMove) {
        this.m_currentMoveNumber = currentMove;
    }

    public boolean isIsTurn() {
        return m_isTurn;
    }

    public void setIsTurn(boolean isTurn) {
        this.m_isTurn = isTurn;
    }

    public Board getBoard() {
        return m_board;
    }

    public void setBoard(Board board) {
        this.m_board = board;
    }

    public Player(String someName, String type) {
        this.m_name = someName;
        if (type.toUpperCase().equals("HUMAN")) {
            this.m_isHuman = true;
        } else //(type.equals("Computer"))
        {
            this.m_isHuman = false;
        }

    }

    public String getName() {
        return (this.m_name);
    }

    public boolean isHuman() {
        return m_isHuman;
    }

    public int calculateScore() {
        Square[][] squares = m_board.getSquares();
        int row = m_board.getRowLength();
        int col = m_board.getColLength();
        double matchingSquares = 0;

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if ((squares[i][j].isIsBlack()) && (squares[i][j].getUserState() == UserSquareStatus.BLACKED) || (!(squares[i][j].isIsBlack())) && (squares[i][j].getUserState() == UserSquareStatus.EMPTY)) {
                    matchingSquares++;
                }
            }
        }
        double score = ((matchingSquares) / (row * col) * 100) + 0.5;
        m_score = (int) score;
        return m_score;
    }

}

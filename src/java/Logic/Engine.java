/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import XMLFiles.GameDescriptor;
import XMLFiles.Players;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

import java.util.LinkedList;
import java.util.Random;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

/**
 * this class implements game manager interface and responsable for the logic
 * behind commands and actions in the game.
 *
 * @author Danny
 */
public class Engine implements IGameManager {

    LinkedList<Player> m_lstPlayers = new LinkedList<Player>();
    int currentGameRound = 0;
    int m_numberOfRounds;
    String m_gameTitle;
    String m_organizer;
    String m_playerCount;
    int m_activePlayers = 0;
    int boardCols;
    int boardRows;
    Board m_board;
    
    @Override 
    public Board getBoard(){
        return m_board;
    }
    @Override
    public int getBoardCols() {
        return boardCols;
    }

    public void setBoardCols(int boardCols) {
        this.boardCols = boardCols;
    }
    @Override
    public int getBoardRows() {
        return boardRows;
    }

    public void setBoardRows(int boardRows) {
        this.boardRows = boardRows;
    }

    public int getActivePlayers() {
        return m_activePlayers;
    }

    public void setActivePlayers(int activePlayers) {
        if (activePlayers < Integer.parseInt(getPlayerCount())) {
            m_activePlayers++;
        }
    }

    public String getPlayerCount() {
        return m_playerCount;
    }

    public void setPlayerCount(String PlayerCount) {
        this.m_playerCount = PlayerCount;
    }

    public String getOrganizer() {
        return m_organizer;
    }
    @Override
    public void setOrganizer(String organizer) {
        this.m_organizer = organizer;
    }

    public String getGameTitle() {
        return m_gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.m_gameTitle = gameTitle;
    }

    public int getCurrentGameRound() {
        return currentGameRound;
    }

    public void setCurrentGameRound(int currentGameRound) {
        this.currentGameRound = currentGameRound;
    }

    public LinkedList<Player> getLstPlayers() {
        return m_lstPlayers;
    }

    @Override
    public void setGameRounds(int numberOfRounds) {
        this.m_numberOfRounds = numberOfRounds;
    }

    @Override
    public int getGameRounds() {
        return m_numberOfRounds;
    }

    @Override
    public int undoMove() {

        Player player = getPlayingPlayer();
        Square[][] squares = player.getBoard().getSquares();
        ArrayList<String> m_MovesArr = player.getMovesArr();
        int res = 0;
        int row = 0;
        String moveToUndo = m_MovesArr.get(m_MovesArr.size() - 1);
        String[] movesRowsColsArr = moveToUndo.split(" ");
        String commentToUndo = player.getLastComment();
        if (commentToUndo.equals("")) {
            res += 1;
            if (player.isHuman()) {
                player.removeLastComment();
            }
        } else {
            res += commentToUndo.length() + 1;
            player.removeLastComment();
        }
        for (int i = 0; i < movesRowsColsArr.length; i++) {

            int col = 0;
            if (i % 2 == 0) { //row
                row = 0;
                if (movesRowsColsArr[i].length() > 1) { //if row number is 2'digits
                    row += (movesRowsColsArr[i].charAt(0) - '0') * 10;
                    row += movesRowsColsArr[i].charAt(1) - '0';
                    res += 3; //add also space
                } else {
                    row += movesRowsColsArr[i].charAt(0) - '0';
                    res += 2; //add space also
                }

            } else if (i % 2 == 1) {
                if (movesRowsColsArr[i].length() > 1) {
                    col += (movesRowsColsArr[i].charAt(0) - '0') * 10;
                    col += movesRowsColsArr[i].charAt(1) - '0';
                    res += 3;
                } else {
                    col += movesRowsColsArr[i].charAt(0) - '0';

                    res += 2;
                }
                String state = squares[row][col].getUserState().toString();
                res += state.length() + 1;
                squares[row][col].undoUserState();

            }

        }
        m_MovesArr.remove(m_MovesArr.size() - 1);
        player.setTotalMoves(player.getTotalMoves() - 1);
        if (player.getCurrentMoveNumber() > 0) {
            player.setCurrentMoveNumber(player.getCurrentMoveNumber() - 1);
        }

        return res;

    }

    @Override
    public void executeMove(ArrayList<Position> movesArr, Board board, UserSquareStatus newStatus) {
        Player player = getPlayingPlayer();
        String move = "";
        String moveOrientation = "";
        player.setCurrentMoveNumber(player.getCurrentMoveNumber() + 1);
        for (int i = 0; i < movesArr.size(); i++) {
            move += movesArr.get(i).getX() + " ";
            move += movesArr.get(i).getY() + " ";
            move += String.valueOf(newStatus) + "\n";
        }
        for (int i = 0; i < movesArr.size(); i++) {
            moveOrientation += movesArr.get(i).getX() + " ";
            moveOrientation += movesArr.get(i).getY() + " ";
        }
        player.setMoves(player.getMoves() + move + player.getLastComment() + "\n");
        player.setMovesArr(moveOrientation, player.getTotalMoves());
        player.setTotalMoves(player.getTotalMoves() + 1);
        int rowSize = board.getRowLength();
        int colSize = board.getColLength();
        Square[][] squares = board.getSquares();
        for (Position position : movesArr) {
            int x = position.getX();
            int y = position.getY();
            squares[x][y].setUserState(newStatus);
        }

    }

    @Override
    public void executeComputerMove() {
        Player player = getPlayingPlayer();
        Square[][] squares = player.getBoard().getSquares();
        Random rand = new Random();
        int row = player.getBoard().getRowLength();
        int col = player.getBoard().getColLength();
        String move = "";
        String moveOrientation = "";
        int size = row * col;
        int numberOfMoves = rand.nextInt(2);
        for (int i = 0; i < numberOfMoves; i++) {
            UserSquareStatus status = UserSquareStatus.getRandom();
            int rangeOfSquaresToChange = rand.nextInt(size) + 1;  //between 1-size squuares will be changed depands also on random row and col picks.
            for (int j = 0; j < rangeOfSquaresToChange; j++) {
                int rowVal = rand.nextInt(row);
                int colVal = rand.nextInt(col);
                squares[rowVal][colVal].setUserState(status);

                move += rowVal + " " + colVal + " ";
                move += String.valueOf(status) + "\n";
                moveOrientation += rowVal + " ";
                moveOrientation += colVal + " ";
            }
            player.setCurrentMoveNumber(player.getCurrentMoveNumber() + 1);

            player.setMoves(player.getMoves() + move + player.getLastComment() + "\n");
            player.setMovesArr(moveOrientation, player.getTotalMoves());
            player.setTotalMoves(player.getTotalMoves() + 1);
        }

    }

    private UserSquareStatus fromStringToUserStateStatus(String str) {
        UserSquareStatus newStatus = null;
        switch (str) {

            case "BLACKED":
                newStatus = newStatus.BLACKED;
                break;
            case "EMPTY":
                newStatus = newStatus.EMPTY;
                break;
            case "UNDEFINED":
                newStatus = newStatus.UNDEFINED;
                break;
        }
        return newStatus;

    }

    @Override
    public void addPlayer(Player player) {
         
        if (!(m_lstPlayers.contains(player))) {
        
            m_lstPlayers.add(player);
        }
        
        m_activePlayers++;
    }

    @Override
    public void removePlayer(Player player) {
        m_lstPlayers.remove(player);
        m_activePlayers--;
    }

    @Override
    public Player getFirstPlayer() {
        return m_lstPlayers.getFirst();
    }

    @Override

    public Player getPlayingPlayer() {

        Player player = null;
        for (int i = 0; i < m_lstPlayers.size(); i++) {
            if (m_lstPlayers.get(i).isIsTurn() == true) {
                return m_lstPlayers.get(i);
            }

        }

        return player;
    }

    @Override
    public void passTurn() {
        for (int i = 0; i < m_lstPlayers.size(); i++) {
            Player player = m_lstPlayers.get(i);
            if (player.isIsTurn() == true) {

                player.setIsTurn(false);
                if (player.equals(m_lstPlayers.getLast())) {
                    m_lstPlayers.getFirst().setIsTurn(true);
                    m_lstPlayers.getFirst().setCurrentMoveNumber(0);
                    break;
                } else {
                    m_lstPlayers.get(i + 1).setIsTurn(true);
                    m_lstPlayers.get(i + 1).setCurrentMoveNumber(0);
                    break;
                }
            }

        }
    }

    @Override
    public int playersCount() {
        return m_lstPlayers.size();
    }

    @Override
    public String getWinnerNameAndDetails() {
        String res = "The winner is:";
        Player winner = m_lstPlayers.getFirst();
        ArrayList<Player> winners = new ArrayList();

        for (int i = 1; i < m_lstPlayers.size(); i++) {
            if (m_lstPlayers.get(i).calculateScore() > winner.calculateScore()) {
                winner = m_lstPlayers.get(i);
                winners.clear();
                winners.add(winner);
            } else if (m_lstPlayers.get(i).calculateScore() == winner.calculateScore()) {
                winner = m_lstPlayers.get(i);
                winners.add(winner);
            }

        }
        if (winner.calculateScore() == 0) {
            res = "All players finished with 0 points";
        } else {
            if (winners.size() == 0) {
                winners.add(winner);
            }

            for (int i = 0; i < winners.size(); i++) {
                res += "\n" + winners.get(i).getName() + winners.get(i).calculateScore() + "%";
            }
        }

        return res;
    }
    
    @Override
    public void loadXml(InputStream stream) throws Exception {
        int actualSize = 0;
        boolean tooManyBlocksForRow = false;
        boolean tooManyBlocksForCol = false;
        boolean validSolution = true;
        boolean validBoardSize = true;
        boolean validPlayerId = true;

        JAXBContext jaxbContext = JAXBContext.newInstance(GameDescriptor.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        GameDescriptor gameDescriptor = (GameDescriptor) jaxbUnmarshaller.unmarshal(stream);

        GameDescriptor.Board xmlBoard = gameDescriptor.getBoard();
        GameDescriptor.Board.Definition def = xmlBoard.getDefinition();

        //if the game is a single player add the player not from the xml file.
        
            //Todo: check that every player has a unique id.
            setGameRounds(Integer.parseInt(gameDescriptor.getDynamicMultiPlayers().getTotalmoves()));
            setPlayerCount(gameDescriptor.getDynamicMultiPlayers().getTotalPlayers());
            setGameTitle(gameDescriptor.getDynamicMultiPlayers().getGametitle());                      
            setBoardCols(def.getColumns().intValue());
            setBoardRows(def.getRows().intValue());
            m_board= new Board(def.getRows().intValue(),def.getColumns().intValue());
//            for (XMLFiles.Player player : players.getPlayer()) {
//                String name = player.getName();
//                int id = player.getId().intValue();
//                String type = player.getPlayerType();
//            Player curPlayer = new Player(name, type);
//            curPlayer.setBoard(new Board(def.getRows().intValue(), def.getColumns().intValue()));
//
//            m_lstPlayers.add(curPlayer);
//            setOrganizer(name);

        for (XMLFiles.Square xmlSquare : xmlBoard.getSolution()
                .getSquare()) {
            final int row = xmlSquare.getRow().intValue() - 1;
            final int col = xmlSquare.getColumn().intValue() - 1;
            if ((row > def.getRows().intValue() + 1) || (col > def.getColumns().intValue() + 1)) {
                validSolution = false;
            } else {

                for (int i = 0; i < m_lstPlayers.size(); i++) {
                    Player player = m_lstPlayers.get(i);
                    final Square[][] squares = player.getBoard().getSquares();
                    Square square = squares[row][col];
                    if (square.isIsBlack() == true) {
                        validSolution = false;

                    } else {
                        square.setIsBlack(true);
                    }
                }
            }

        }
        Slice[] rowSlices = new Slice[def.getRows().intValue()];
        Slice[] colSlices = new Slice[def.getColumns().intValue()];

        //load slices
        for (XMLFiles.Slice xmlSlice : xmlBoard.getDefinition().getSlices().getSlice()) {
            final String orientation = xmlSlice.getOrientation();

            final int id = xmlSlice.getId().intValue();
            final String blocks = xmlSlice.getBlocks().replaceAll("\\s", "");

            Slice slice = new Slice(orientation, id, blocks);
            if (orientation.equals("row")) {
                if (sumAllBlocks(blocks) > def.getColumns().intValue()) {
                    tooManyBlocksForRow = true;

                }
                rowSlices[id - 1] = slice;
            } else if (orientation.equals("column")) {
                if (sumAllBlocks(blocks) > def.getRows().intValue()) {
                    tooManyBlocksForCol = true;

                }
                colSlices[id - 1] = slice;
            }
            actualSize++;
        }
        if (actualSize != def.getColumns().intValue() + def.getRows().intValue()) {
            validBoardSize = false;

        }
//        for (int i = 0;
//                i < m_lstPlayers.size();
//                i++) {
//            Player player = m_lstPlayers.get(i);
//            player.getBoard().setRowSlices(rowSlices);
//            player.getBoard().setColSlices(colSlices);
//        }
           m_board.setRowSlices(rowSlices);
           m_board.setColSlices(colSlices);
        validateXML(gameDescriptor, validPlayerId, tooManyBlocksForRow, tooManyBlocksForCol, validSolution, validBoardSize);
    }

    private int sumAllBlocks(String block) {
        int res = 0;
        String[] blocks = block.split(",");
        for (int i = 0; i < blocks.length; i++) {

            res += Integer.parseInt(blocks[i]);
            if (i % 2 == 0 && blocks.length > 1) {
                res += 1;
            }
        }
        return res;
    }

    private void validateXML(GameDescriptor gameDescriptor, boolean validPlayerId, boolean tooManyBlocksForRow, boolean tooManyBlocksForCol, boolean validSolution, boolean validBoardSize) throws Exception {
        // check that the board size is valid, if there is a slice for every row\col

        if (!validPlayerId) {
            throw new Exception("Players Id are not unique numbers in the file.");
        }
        if (!validBoardSize) {
            throw new Exception("the actual board size in the file does not equal to the declered board size in the file.");
        }
        if (tooManyBlocksForRow) {
            throw new Exception("there is atleast one row with more blocks than column in the board ");
        }
        if (tooManyBlocksForCol) {
            throw new Exception("there is atleast one column with more blocks that rows in the board");
        }
        if (!validSolution) {
            throw new Exception("the Solution  in the file is not valid");
        }

        // check that there is atleast 1 square
        if (gameDescriptor.getBoard().getSolution().getSquare().size() < 1) {
            throw new Exception("There should be at least 1 square in the solution.");
        }
    }

}

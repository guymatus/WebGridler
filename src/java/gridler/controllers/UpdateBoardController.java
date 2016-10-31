/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gridler.controllers;

import Logic.Board;
import Logic.IGameManager;
import Logic.Player;
import Logic.Slice;
import Logic.Square;
import gridler.Utilities.Utilities;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Danny
 */
@WebServlet(name = "UpdateBoardController", urlPatterns = {"/UpdateBoardController"})
public class UpdateBoardController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int gameId = Integer.parseInt(request.getParameter("game_id"));
        IGameManager runningGame = Utilities.getRunningGamesList(getServletContext()).get(gameId);
        Player chosenPlayer = Utilities.getPlayingPlayer(runningGame, (String) request.getSession().getAttribute("username"));

        Board board = chosenPlayer.getBoard();
        Square[][] squares = board.getSquares();
        int row = board.getRowLength();
        int col = board.getColLength();
        Slice[] rowslices = board.getRowSlices();
        Slice[] colslices = board.getColSlices();

        String[] rowBlocks = new String[row];
        String[] colBlocks = new String[col];
        for (int i = 0; i < row; i++) {
            rowBlocks[i] = rowslices[i].getBlock();
        }
        for (int i = 0; i < col; i++) {
            colBlocks[i] = colslices[i].getBlock();
        }

        String table = "<table class='col-lg-2'>";
        table += "<tr><td>";
        for (int i = 1; i <= col; i++) {
            table += "<td>" + i + "</td>";
        }
        table += "</tr></td>";
        for (int r = 1; r < row; r++) {
            table += "<tr><td>" + r + "</td>";
            for (int j = 0; j < col; j++) {
                if (squares[r - 1][j].getUserState() != null) {
                    switch (squares[r - 1][j].getUserState()) {
                        case BLACKED:
                            table += "<td><a class='btn square square-blacked' data-col='" + j + "' data-row=" + (r - 1) + "></a></td> ";
                            break;
                        case EMPTY:
                            table += "<td><a class='btn square square-empty' data-col='" + j + "' data-row=" + (r - 1) + "></a></td> ";
                            break;
                        case UNDEFINED:
                            table += "<td><a class='btn square btn-default' data-col='" + j + "' data-row=" + (r - 1) + "></a></td> ";
                            break;
                        default:
                            break;
                    }
                }
            }
            table += "<td>" + rowBlocks[r - 1] + "</td></tr>";

        }
        table += "<tr><td></td>";
        for (int c = 0; c < col; c++) {
            table += "<td style='vertical-align:top;text-align:center;'>";
            table += colBlocks[c].replaceAll(",", "<br />");
            table += "</td>";
        }
        table += "</tr>";
        table += "</table>";
        response.getWriter().print(table);
    }

}

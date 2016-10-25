/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gridler.controllers;

import Logic.IGameManager;
import Logic.Slice;
import gridler.Utilities.Utilities;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Guy Matus
 */
@WebServlet(name = "GameRoomController", urlPatterns = {"/GameRoomController"})
public class GameRoomController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("username") == null) {
            resp.sendRedirect("Login.jsp");
            return;
        }

        IGameManager currentGame = Utilities.getRunningGamesList(getServletContext()).get(Integer.parseInt(req.getParameter("id")));
        req.setAttribute("currentGame", currentGame);
        Slice[] rowslices = currentGame.getBoard().getRowSlices();
        Slice[] colslices = currentGame.getBoard().getColSlices();
        String rowBlocks = "";
        String colBlocks = "";
        for (int i = 0; i < currentGame.getBoardRows(); i++) {
            rowBlocks += "[" + rowslices[i].getBlock() + "],";
        }
        for (int i = 0; i < currentGame.getBoardCols(); i++) {
            colBlocks += "[" + colslices[i].getBlock() + "],";
        }
        req.setAttribute("rowBlocks", rowBlocks);
        req.setAttribute("colBlocks", colBlocks);
        getServletContext().getRequestDispatcher("/Game.jsp").forward(req, resp);
    }

}

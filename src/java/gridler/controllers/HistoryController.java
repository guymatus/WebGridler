/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gridler.controllers;

import Logic.IGameManager;
import Logic.Player;
import gridler.Utilities.Utilities;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Amit Elbaz
 */
@WebServlet(name = "HistoryController", urlPatterns = {"/HistoryController"})
public class HistoryController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("username") == null) {
            resp.sendRedirect("Login.jsp");
            return;
        }

        int gameId = Integer.parseInt(req.getParameter("game_id"));
        IGameManager runningGame = Utilities.getRunningGamesList(getServletContext()).get(gameId);
        Player player = Utilities.getPlayingPlayer(runningGame, (String) req.getSession().getAttribute("username"));

        resp.getWriter().print(player.getMoves());
    }

}

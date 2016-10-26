/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gridler.controllers;

import Logic.IGameManager;
import Logic.Player;
import Logic.UserSquareStatus;
import gridler.Utilities.Utilities;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Guy Matus
 */
@WebServlet(name = "LeaveGameController", urlPatterns = {"/LeaveGameController"})
public class LeaveGameController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("username") == null) {
            resp.sendRedirect("Login.jsp");
            return;
        }

        int gameId = Integer.parseInt(req.getParameter("game_id"));
        IGameManager runningGame = Utilities.getRunningGamesList(getServletContext()).get(gameId);
        Player playerToLeave = null;
        for (Player player : runningGame.getLstPlayers()) {
            if (player.getName().equals(req.getSession().getAttribute("username"))) {
                playerToLeave = player;
                break;
            }
        }
        if (playerToLeave == null) {
            throw new ServletException("You can't leave a game you are not in.");
        }

        runningGame.removePlayer(playerToLeave);

        getServletContext().getRequestDispatcher("/Dashboard.jsp").forward(req, resp);
    }
}

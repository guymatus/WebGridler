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
@WebServlet(name = "UndoController", urlPatterns = {"/UndoController"})
public class UndoController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int gameId = Integer.parseInt(req.getParameter("game_id"));
            IGameManager runningGame = Utilities.getRunningGamesList(getServletContext()).get(gameId);
            runningGame.undoMove();

            resp.getWriter().print("success");
        } catch (Exception ex) {
            ex.printStackTrace(resp.getWriter());
        }
    }

}

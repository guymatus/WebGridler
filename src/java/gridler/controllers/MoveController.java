/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gridler.controllers;

import Logic.IGameManager;
import Logic.Position;
import Logic.UserSquareStatus;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import gridler.Utilities.Utilities;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Guy Matus
 */
@WebServlet(name = "MoveController", urlPatterns = {"/MoveController"})
public class MoveController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (req.getSession().getAttribute("username") == null) {
                resp.sendRedirect("Login.jsp");
                return;
            }

            int gameId = Integer.parseInt(req.getParameter("game_id"));
            IGameManager runningGame = Utilities.getRunningGamesList(getServletContext()).get(gameId);
            String moveList = req.getParameter("moveList");
            String moveState = req.getParameter("moveState");
            runningGame.executeMove(translateMovesArray(moveList), runningGame.getBoard(), translateMoveState(moveState));
            runningGame.passTurn();

            resp.getWriter().print("success");
        } catch (Exception ex) {
            resp.getWriter().print(ex.getMessage());
        }
    }

    static ArrayList<Position> translateMovesArray(String moveListJSON) {
        ArrayList<Position> ret = new ArrayList<>();

        JsonElement mainElem = new JsonParser().parse(moveListJSON);
        for (JsonElement positionElem : mainElem.getAsJsonArray()) {
            JsonArray positionArray = positionElem.getAsJsonArray();
            ret.add(new Position(positionArray.get(0).getAsInt(), positionArray.get(1).getAsInt()));
        }

        return ret;
    }

    static UserSquareStatus translateMoveState(String moveState) throws ServletException {
        if (moveState.equals("BLACKED")) {
            return UserSquareStatus.BLACKED;
        } else if (moveState.equals("EMPTY")) {
            return UserSquareStatus.EMPTY;
        } else if (moveState.equals("UNDEFINED")) {
            return UserSquareStatus.UNDEFINED;
        } else {
            throw new ServletException("could not find matching move state");
        }
    }
}

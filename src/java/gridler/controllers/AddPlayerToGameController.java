/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gridler.controllers;

import Logic.Board;
import Logic.IGameManager;
import Logic.Player;
import com.google.gson.Gson;
import gridler.Utilities.Utilities;
import gridler.types.UserType;
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
@WebServlet(name = "AddPlayerToGameController", urlPatterns = {"/AddPlayerToGameController"})
public class AddPlayerToGameController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int gameIndex = Integer.parseInt(req.getParameter("gameIndex"));
        String playerName = req.getParameter("playerName");
        UserType userType = Utilities.getUsersList(getServletContext()).get(playerName);

        IGameManager runningGame = Utilities.getRunningGamesList(getServletContext()).get(gameIndex);
        Player player = new Player(playerName, userType.toString());
        player.setBoard(deepCopy(runningGame.getBoard(), Board.class));
        runningGame.addPlayer(player);

        resp.getWriter().print("success");
    }

    private <T> T deepCopy(T object, Class<T> type) {
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(object, type), type);
    }
}

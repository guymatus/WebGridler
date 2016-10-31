/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gridler.controllers;

import Logic.IGameManager;
import Logic.Player;
import com.google.gson.Gson;
import gridler.Utilities.Utilities;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Danny
 */
@WebServlet(name = "UpdateGameRoomUsersList", urlPatterns = {"/UpdateGameRoomUsersList"})
public class UpdateGameRoomUsersList extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Gson gson = new Gson();
        int gameId = Integer.parseInt(req.getParameter("game_id"));
        IGameManager runningGame = Utilities.getRunningGamesList(getServletContext()).get(gameId);

        LinkedList<Player> lstPlayers = runningGame.getLstPlayers();
        Map<String, String> currentUsers = new HashMap<>();
        Map<String, String> currentUsersScore = new HashMap<>();
        String userType = "";
        int score;
        for (int i = 0; i < lstPlayers.size(); i++) {
            String userName = lstPlayers.get(i).getName();
            int Score = lstPlayers.get(i).calculateScore();
            String userScore = Integer.toString(Score);
            if (lstPlayers.get(i).isHuman()) {
                userType = "Human";
            } else {
                userType = "Computer";
            }

            currentUsers.put(userName, userType);
            currentUsersScore.put(userName, userScore);
        }
        resp.getWriter().print(currentUsers + "," + currentUsersScore);
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gridler.Utilities;

import Logic.*;
import gridler.types.UserType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 *
 * @author Guy Matus
 */
public class Utilities {

    private static IGameManager m_gameManager;

    public static Map<String, UserType> getUsersList(ServletContext context) {

        //save the guest list in the servlet context
        //The Servlet Context is shared between all servlets in the web application
        Map<String, UserType> usersList = (Map<String, UserType>) context.getAttribute("usersList");
        if (usersList == null) {
            usersList = new HashMap<>();
            context.setAttribute("usersList", usersList);
        }

        return usersList;
    }

    public static String getRowBlock(int index) {
        String res = "5";
        return res;
    }

    public static void loadXml(String filePath) throws Exception {
        m_gameManager = new Engine();

    }

    public static List<IGameManager> getRunningGamesList(ServletContext servletContext) {
        List<IGameManager> gamesList = (List<IGameManager>) servletContext.getAttribute("runningGamesList");
        if (gamesList == null) {
            gamesList = new ArrayList<>();
            servletContext.setAttribute("runningGamesList", gamesList);
        }

        return gamesList;
    }

    public static UserType getUserTypeByString(String userType) throws ServletException {
        if ("human".equals(userType)) {
            return UserType.Human;
        } else if ("computer".equals(userType)) {
            return UserType.Computer;
        } else {
            throw new ServletException("user type must be human or computer. user type given: " + userType);
        }
    }

    public static Player getPlayingPlayer(IGameManager runningGame, String username) throws ServletException {
        Player chosenPlayer = null;
        for (Player player : runningGame.getLstPlayers()) {
            if (player.getName().equals(username)) {
                chosenPlayer = player;
                break;
            }
        }
        if (chosenPlayer == null) {
            throw new ServletException("Didn't find playing player in running game");
        }
        return chosenPlayer;
    }
}

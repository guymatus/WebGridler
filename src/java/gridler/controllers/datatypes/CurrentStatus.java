/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gridler.controllers.datatypes;

import Logic.IGameManager;
import gridler.types.UserType;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Guy Matus
 */
public class CurrentStatus {

    Map<String, UserType> currentUsers;
    List<IGameManager> runningGames;

    public Map<String, UserType> getCurrentUsers() {
        return currentUsers;
    }

    public void setCurrentUsers(Map<String, UserType> currentUsers) {
        this.currentUsers = currentUsers;
    }

    public List<IGameManager> getRunningGames() {
        return runningGames;
    }

    public void setRunningGames(List<IGameManager> runningGames) {
        this.runningGames = runningGames;
    }

    public CurrentStatus(Map<String, UserType> currentUsers, List<IGameManager> runningGames) {
        this.currentUsers = currentUsers;
        this.runningGames = runningGames;
    }
}

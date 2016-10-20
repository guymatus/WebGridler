
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import java.util.ArrayList;

/**
 *
 * @author Danny
 */
public class Square {
    /**
     * User-defined state of this square
     */
   private ArrayList< UserSquareStatus> userStateArr= new ArrayList();
   

   /**
    * is this square defined as black by the solution?
    */
   private boolean isBlack;

    public UserSquareStatus getUserState() {
        return userStateArr.get(userStateArr.size()-1);
    }

    public void setUserState(UserSquareStatus userState) {
        this.userStateArr.add(userState);
    }
    
    public void undoUserState(){
        if(!userStateArr.isEmpty())
        this.userStateArr.remove(userStateArr.size()-1);
    }

    public boolean isIsBlack() {
        return isBlack;
    }

    public void setIsBlack(boolean isBlack) {
        this.isBlack = isBlack;
    }
}

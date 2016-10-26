/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

/**
 *
 * @author Danny
 */
public enum UserSquareStatus {
    UNDEFINED, BLACKED, EMPTY;

    public static UserSquareStatus getRandom() {
        return values()[(int) (Math.random() * values().length)];
    }
}

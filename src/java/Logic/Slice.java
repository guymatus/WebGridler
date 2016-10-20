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
public class Slice {
    
private String m_block;

    public String getBlock() {
        return m_block;
    }
private String m_orientation;
private int m_id;

    Slice(String orientation,int id, String blocks) {
         m_block = blocks;
         m_orientation = orientation;
         m_id = id;
    }
}

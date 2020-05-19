/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStructures;

import Clases.Bloque;
import java.io.Serializable;

/**
 *
 * @author juanp
 */
public class BlockNode implements Serializable{
    public Bloque bloque;
    public BlockNode next;
    public BlockNode prev;

    public BlockNode(Bloque bloque) {
        this.bloque = bloque;
        this.next = null;
        this.prev = null;             
    }
    
}

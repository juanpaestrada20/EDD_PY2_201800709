/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStructures;

import java.io.Serializable;

/**
 *
 * @author juanp
 */
public class Nodos implements Serializable{
    public String ip;
    public Nodos next;

    public Nodos(String ip) {
        this.ip = ip;
        this.next = null;
    }
    
    
}

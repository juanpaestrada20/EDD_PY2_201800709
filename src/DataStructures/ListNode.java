/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStructures;

import Clases.Usuario;

/**
 *
 * @author juanp
 */
public class ListNode {
    public Usuario user;
    public ListNode next;
    
    public ListNode(Usuario user){
        this.user = user;
        next = null;
    }
}

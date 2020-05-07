/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStructures;

/**
 *
 * @author juanp
 */
public class AVLNode {

    public String categoria;
    public AVLNode left;
    public AVLNode right;
    public int heigth;
    public BTree libros;

    public AVLNode(String categoria) {
        this.categoria = categoria;
        heigth = 1;
        this.libros = new BTree(3);
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Inicio;

import DataStructures.BTree;

/**
 *
 * @author juanp
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        BTree t = new BTree();

        t.insert("hugo");
        t.insert("luis");
        t.insert("andres");
        t.insert("juan");
        t.insert("jose");
        t.insert("sofi");
        t.insert("andrea");
        t.insert("veronica");
        
        t.traverse();
    }
}

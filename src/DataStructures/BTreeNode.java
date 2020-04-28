/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStructures;

import Clases.Libro;

/**
 *
 * @author juanp
 */
public class BTreeNode {

    public int n;           // numero de llaves almacenadas
    public Libro[] keys;     //llaves
    public boolean leaf;    // si es hoja o tiene hijos
    public BTreeNode[] C;   //punteros de hijos
    public int height;      // altura
    public int t;           //grado minimo

    public BTreeNode(int t, boolean leaf) {
        this.t = t;
        this.leaf = leaf;
        this.keys = new Libro[2 * t - 1];
        this.C = new BTreeNode[2 * t];
        this.height = 0;
        this.n = 0;
    }
}

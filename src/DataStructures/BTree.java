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
public class BTree {

    public BTreeNode root;
    public int t;

    public BTree() {
        this.root = null;
        this.t = 5;
    }

    public void traverse() {
        if (this.root != null) {
            this.root.traverse();
        }
        System.out.println();
    }

    public BTreeNode search(String k) {
        if (this.root == null) {
            return null;
        } else {
            return root.search(k);
        }
    }

    // INSERCION
    public void insert(String k) {
        if (root == null) {
            root = new BTreeNode(5, true);
            root.keys[0] = k;
            root.n = 1;
        } else {
            // SI LA RAIZ ESTA LLENA
            if (root.n == 2 * t - 1) {
                // SE CREA NUEVA RAIZ
                BTreeNode s = new BTreeNode(5, false);

                // LA ANTIGUA RAIZ SE VUELVE HIJO DE LA NUEVA RAIZ
                s.C[0] = root;

                // SPLITEMOS LA RAIZ ANTIGUA Y SE MUEVE UNA LLAVE A LA NUEVA RAIZ
                s.splitChild(0, root);

                // LA NUEVA RAIZ TIENE DOS HIJOS AHORA
                int i = 0;
                if (s.keys[0].compareToIgnoreCase(k) < 0) {
                    i++;
                }
                s.C[i].insertNonFull(k);

                // CAMBIAR LA RAIZ
                root = s;
            } else {
                // SI LA RAIZ NO ESTA LLENA SOLO SE INSERTA
                root.insertNonFull(k);
            }
        }
    }

}

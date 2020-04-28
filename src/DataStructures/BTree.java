/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStructures;

import Clases.Libro;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author juanp
 */
public class BTree {

    public BTreeNode root;
    public int t;

    public int contadorNodos;

    public BTree(int t) {
        root = new BTreeNode(t, true);
        this.t = t;
    }

    public BTreeNode search(BTreeNode node, long k) {
        int i = 0;
        while (i <= node.n-1 && k > node.keys[i].getISBN()) {
            i++;
        }
        if (i <= node.n-1 && k == node.keys[i].getISBN()) {
            return node;
        } else if (node.leaf) {
            return null;
        } else {
            return search(node.C[i], k);
        }

    }

    public void splitChild(BTreeNode x, int i) {
        BTreeNode z = new BTreeNode(t, true);
        BTreeNode y = x.C[i];

        z.leaf = y.leaf;
        z.n = t - 1;

        for (int j = 0; j < t - 1; j++) {
            z.keys[j] = y.keys[j+t];
        }

        if (!y.leaf) {
            for (int j = 0; j < t; j++) {
                z.C[j] = y.C[j + t];
            }
        }

        y.n = t - 1;

        for (int j = x.n+1; j > i+1; j--) {
            x.C[j + 1] = x.C[j];
        }

        x.C[i + 1] = z;

        for (int j = x.n; j > i; j--) {
            x.keys[j] = x.keys[j-1];
        }

        x.keys[i] = y.keys[t-1];
        x.n = x.n + 1;
    }

    public void insert(Libro k) {
        BTreeNode temp = root;
        if(search(root, k.getISBN())!=null){
            return;
        }
        if (temp.n == (2 * t - 1)) {
            BTreeNode s = new BTreeNode(t, true);
            root = s;
            s.leaf = false;
            s.n = 0;
            s.C[0] = temp;
            splitChild(s, 0);
            insertNonFull(s, k);
        } else {
            insertNonFull(temp, k);
        }
    }

    public void insertNonFull(BTreeNode x, Libro k) {
        int i = x.n-1;
        if (x.leaf) {
            
            while (i >= 0 && k.getISBN() < x.keys[i].getISBN()) {
                x.keys[i+1] = x.keys[i];
                i--;
            }
            x.keys[i+1] = k;
            x.n++;
        } else {
            while (i >= 0 && k.getISBN() < x.keys[i].getISBN()) {
                i--;
            }
            i++;
            BTreeNode temp = x.C[i];
            if (temp.n == 2 * t - 1) {
                splitChild(x, i);
                if (k.getISBN() > x.keys[i].getISBN()) {
                    i++;
                }
            }
            insertNonFull(x.C[i], k);
        }
    }

    private String createTree(BTreeNode n, String escritura) {
        if (n != null) {
            contadorNodos++;
            int nodo = contadorNodos;
            escritura += "node" + contadorNodos + "[label = \"";
            for (int i = 0; i < n.n; i++) {
                escritura += "<f" + i + "> |" + n.keys[i].getTitulo()+ "|";
            }
            
            escritura += "<f" + (n.n) + ">\"];\n";
            for(int i = 0; i<n.C.length; i++){
                escritura = createTree(n.C[i], escritura);
                if(n.C[i]!=null){
                    escritura += "\"node"+nodo + "\":f"+i +"->\"node"+contadorNodos+"\";\n";
                }
            }
        }
        return escritura;
    }

    public void generateDotTree() {
        String escritura = "";
        contadorNodos = 0;
        StringBuilder resultado = new StringBuilder();
        String rdot = "BTree_Libros.dot";
        BTreeNode temp = root;
        resultado.append("digraph G {\nnode [shape = record,height=.1 color=black fillcolor=salmon style=filled];\n");
        escritura = createTree(temp, escritura);
        resultado.append(escritura);
        resultado.append("\n}");
        generateDot(rdot, resultado.toString());
    }

    private void generateDot(String rdot, String grafo) {

        FileWriter writer = null;
        try {
            // CREACION DE ARCHIVO CON EXTENSION .DOT
            File fileDot = new File(rdot);
            fileDot.setWritable(true);
            String rutaPng = fileDot.getAbsolutePath().substring(0, fileDot.getAbsolutePath().length() - 4) + ".png";
            File file = new File(rdot);
            writer = new FileWriter(file);
            writer.write(grafo);
            writer.close();

            // CONSTRUCCION DEL COMANDO PARA GENERAR IMAGEN
            ProcessBuilder pBuilder;
            pBuilder = new ProcessBuilder("dot", "-Tpng", "-o", rutaPng, fileDot.getAbsolutePath());
            pBuilder.start();

        } catch (IOException ex) {
            Logger.getLogger(AVLTree.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(AVLTree.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}

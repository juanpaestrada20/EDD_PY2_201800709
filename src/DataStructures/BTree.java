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
import java.util.ArrayList;
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

    public ArrayList<Libro> buscarLibroParteNombre(String k) {
        ArrayList<Libro> libros = new ArrayList<Libro>();
        libros = searchBook2(root, k, libros);
        return libros;
    }

    private ArrayList<Libro> searchBook2(BTreeNode n, String k, ArrayList<Libro> libros) {
        int i;
        for (i = 0; i < n.n; i++) {
            if (!n.leaf) {
                if (n.C[i] != null) {
                    libros = searchBook2(n.C[i], k, libros);
                }
            }
            if (n.keys[i].getTitulo().indexOf(k) >= 0) {
                libros.add(n.keys[i]);
            }
        }
        if (!n.leaf) {
            if (n.C[i] != null) {
                libros = searchBook2(n.C[i], k, libros);
            }
        }

        return libros;

    }

    public Libro buscarLibroNombre(String k) {
        return searchBook(root, k);
    }

    private Libro searchBook(BTreeNode n, String k) {
        int i;
        Libro temp = null;
        for (i = 0; i < n.n; i++) {
            if (!n.leaf) {
                if (n.C[i] != null) {
                    temp = searchBook(n.C[i], k);
                }
                if (temp != null) {
                    return temp;
                }
            }
            if (n.keys[i].getTitulo().equalsIgnoreCase(k)) {
                return n.keys[i];
            }
        }
        if (!n.leaf) {
            if (n.C[i] != null) {
                return searchBook(n.C[i], k);
            }
        }

        return temp;

    }

    private void recorrerInOrder() {
        if (root != null) {
            InOrder(root);
        }
    }

    private void InOrder(BTreeNode n) {
        if (n != null) {
            int i;
            for (i = 0; i < n.n; i++) {
                if (!n.leaf) {
                    InOrder(n.C[i]);
                }
                System.out.println(n.keys[i].getTitulo() + " -> ");
            }
            if (!n.leaf) {
                InOrder(n.C[i]);
            }
        }
    }

    public Libro buscarLibroISBN(long k) {
        return search(root, k);
    }

    private Libro search(BTreeNode node, long k) {
        int i = 0;
        while (i <= node.n - 1 && k > node.keys[i].getISBN()) {
            i++;
        }
        if (i <= node.n - 1 && k == node.keys[i].getISBN()) {
            return node.keys[i];
        } else if (node.leaf) {
            return null;
        } else {
            return search(node.C[i], k);
        }

    }

    public void splitChild(BTreeNode x, int i) {
        BTreeNode z = new BTreeNode(t, true);
        BTreeNode y = x.C[i];

        //NODO DERECHO DEL QUE SE SEPARA
        z.leaf = y.leaf;
        z.n = t - 1;

        // SEPARAR LA PAGINA
        for (int j = 0; j < t - 1; j++) {
            z.keys[j] = y.keys[j + t];
            y.keys[j + t] = null;
        }

        if (!y.leaf) {
            // TRASPASAR HIJOS
            for (int j = 0; j < t; j++) {
                z.C[j] = y.C[j + t];
                y.C[j + t] = null;
            }
        }

        y.n = t - 1;

        //CORRER LOS HIJOS
        for (int j = x.n + 1; j > i; j--) {
            x.C[j] = x.C[j - 1];
        }
        //ENLAZAR PADRE CON HIJO DERECHO
        x.C[i + 1] = z;

        for (int j = x.n; j > i; j--) {
            x.keys[j] = x.keys[j - 1];
        }
        // VALOR DEL NODO QUE SE VUELVE PADRE
        x.keys[i] = y.keys[t - 1];
        x.n = x.n + 1;
    }

    public void insert(Libro k) {
        BTreeNode temp = root;
        if (search(root, k.getISBN()) != null) {
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

    private void insertNonFull(BTreeNode x, Libro k) {
        int i = x.n - 1;
        if (x.leaf) {

            while (i >= 0 && k.getISBN() < x.keys[i].getISBN()) {
                x.keys[i + 1] = x.keys[i];
                i--;
            }
            x.keys[i + 1] = k;
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

    // ELIMINAR
    public void eliminarLibro(long k) {
        Libro x = search(root, k);
        if (x == null) {
            return;
        }
        remove(root, k);
    }

    private void remove(BTreeNode n, long k) {

        int idx = n.findKey(k);

        // NO SE ENCONtRO EN EL NODO RECIVIDO
        if (idx != -1) {
            if (n.leaf) {
                int i = 0;
                for (i = 0; i < n.n && n.keys[i].getISBN() != k; i++) {
                }
                ;
                for (; i < n.n; i++) {
                    if (i != 2 * t - 2) {
                        n.keys[i] = n.keys[i + 1];
                    }
                }
                n.n--;
            } else {
                BTreeNode pred = n.C[idx];
                Libro predKey = null;
                if (pred.n >= t) {
                    while (true) {
                        if (pred.leaf) {
                            System.out.println(pred.n);
                            predKey = pred.keys[pred.n - 1];
                            break;
                        } else {
                            pred = pred.C[pred.n];
                        }
                    }
                    remove(pred, predKey.getISBN());
                    n.keys[idx] = predKey;
                    return;
                }

                BTreeNode next = n.C[idx + 1];
                if (next.n >= t) {
                    Libro nextKey = next.keys[0];
                    if (!next.leaf) {
                        next = next.C[0];
                        while (true) {
                            if (next.leaf) {
                                nextKey = next.keys[next.n - 1];
                                break;
                            } else {
                                next = next.C[next.n];
                            }
                        }
                    }
                    remove(next, nextKey.getISBN());
                    return;
                }

                int temp = pred.n + 1;
                pred.keys[pred.n++] = n.keys[idx];
                for (int i = 0, j = pred.n; i < next.n; i++) {
                    pred.keys[j++] = next.keys[i];
                    pred.n++;
                }
                for (int i = 0; i < next.n + 1; i++) {
                    pred.C[temp++] = next.C[i];
                    next.C[i] = null;
                }

                n.C[idx] = pred;
                for (int i = idx; i < n.n; i++) {
                    if (i != 2 * t - 2) {
                        n.keys[i] = n.keys[i + 1];
                    }
                }
                for (int i = idx + 1; i < n.n + 1; i++) {
                    if (i != 2 * t - 1) {
                        n.C[i] = n.C[i + 1];
                    }
                }
                n.n--;
                if (n.n == 0) {
                    if (n == root) {
                        root = n.C[0];
                    }
                    n = n.C[0];
                }
                remove(pred, k);
                return;
            }
        } else {
            for (idx = 0; idx < n.n; idx++) {
                if (n.keys[idx].getISBN() > k) {
                    break;
                }
            }
            BTreeNode tmp = n.C[idx];
            if (tmp.n >= t) {
                remove(tmp, k);
                return;
            }

            if (true) {
                BTreeNode nb = null;
                Libro devider = null;

                if (idx != n.n && n.C[idx + 1].n >= t) {
                    devider = n.keys[idx];
                    nb = n.C[idx + 1];
                    n.keys[idx] = nb.keys[0];
                    tmp.keys[tmp.n++] = devider;
                    tmp.C[tmp.n] = nb.C[0];
                    for (int i = 1; i < nb.n; i++) {
                        nb.keys[i - 1] = nb.keys[i];
                    }
                    for (int i = 1; i <= nb.n; i++) {
                        nb.C[i - 1] = nb.C[i];
                    }
                    nb.n--;
                    remove(tmp, k);
                    return;
                } else if (idx != 0 && n.C[idx - 1].n >= t) {

                    devider = n.keys[idx - 1];
                    nb = n.C[idx - 1];
                    n.keys[idx - 1] = nb.keys[nb.n - 1];
                    BTreeNode child = nb.C[nb.n];
                    nb.n--;

                    for (int i = tmp.n; i > 0; i--) {
                        tmp.keys[i] = tmp.keys[i - 1];
                    }
                    tmp.keys[0] = devider;
                    for (int i = tmp.n + 1; i > 0; i--) {
                        tmp.C[i] = tmp.C[i - 1];
                    }
                    tmp.C[0] = child;
                    tmp.n++;
                    remove(tmp, k);
                    return;
                } else {
                    BTreeNode lt = null;
                    BTreeNode rt = null;
                    boolean last = false;
                    if (idx != n.n) {
                        devider = n.keys[idx];
                        lt = n.C[idx];
                        rt = n.C[idx + 1];
                    } else {
                        devider = n.keys[idx - 1];
                        rt = n.C[idx];
                        lt = n.C[idx - 1];
                        last = true;
                        idx--;
                    }
                    for (int i = idx; i < n.n - 1; i++) {
                        n.keys[i] = n.keys[i + 1];
                    }
                    for (int i = idx + 1; i < n.n; i++) {
                        n.C[i] = n.C[i + 1];
                    }
                    n.n--;
                    lt.keys[lt.n++] = devider;

                    for (int i = 0, j = lt.n; i < rt.n + 1; i++, j++) {
                        if (i < rt.n) {
                            lt.keys[j] = rt.keys[i];
                        }
                        lt.C[j] = rt.C[i];
                    }
                    lt.n += rt.n;
                    if (n.n == 0) {
                        if (n == root) {
                            root = n.C[0];
                        }
                        n = n.C[0];
                    }
                    remove(lt, k);
                    return;
                }
            }
        }
    }

    // GRAFICAR
    private String createTree(BTreeNode n, String escritura) {
        if (n != null) {
            contadorNodos++;
            int nodo = contadorNodos;
            escritura += "node" + contadorNodos + "[label = \"";
            for (int i = 0; i < n.n; i++) {
                escritura += "<f" + i + "> |" + n.keys[i].getISBN() + "|";
            }

            escritura += "<f" + (n.n) + ">\"];\n";
            for (int i = 0; i < n.C.length; i++) {
                int nodoHijo = contadorNodos + 1;
                escritura = createTree(n.C[i], escritura);

                if (n.C[i] != null) {
                    escritura += "\"node" + nodo + "\":f" + i + "->\"node" + nodoHijo + "\";\n";
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
            Logger.getLogger(BTree.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(BTree.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}

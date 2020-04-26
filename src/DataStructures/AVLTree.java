/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStructures;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author juanp
 */
public class AVLTree {

    public AVLNode root;
    public String ruta;

    public AVLTree() {
        root = null;
    }

    // FUNCION PARA OBTENER ALTURA DE LOS NODOS
    private int height(AVLNode n) {
        return (n == null) ? 0 : n.heigth;
    }

    // FUNCION PARA OBTENER NUMERO MAYOR
    private int max(int a, int b) {
        return (a > b) ? a : b;
    }

    // CASO IZQUIERDA - IZQUIERDA
    // ROTACION SIMPLE DERECHA
    private AVLNode rightRotate(AVLNode n) {
        AVLNode n1 = n.left;

        // ROTACION
        n.left = n1.right;
        n1.right = n;
        //n = n1;

        // ACTUALIZAR ALTURAS
        n.heigth = max(height(n.left), height(n.right)) + 1;
        n1.heigth = max(height(n1.left), n.heigth) + 1;

        // RETORNO DE NODO EQUILIBRADO
        return n1;
    }

    // CASO DERECHA-DERECHA
    // ROTACION SIMPLE IZQUIERDA
    private AVLNode leftRotate(AVLNode n) {
        AVLNode n1 = n.right;

        // ROTACION
        n.right = n1.left;
        n1.left = n;
        //n = n1;

        // ACTUALIZAR ALTURAS
        n.heigth = max(height(n.left), height(n.right)) + 1;
        n1.heigth = max(height(n1.left), height(n1.right)) + 1;

        // RETORNO DE NODO EQUILIBRADO
        return n1;
    }

    // FACTOR DE EQUILIBRIO
    private int getBalance(AVLNode n) {
        return (n == null) ? 0 : (height(n.left) - height(n.right));
    }

    public void agregarCategoria(String cat) {
        root = insert(root, cat);
    }

    private AVLNode insert(AVLNode n, String key) {
        // INSERCION DE ARBOL BINARIO NORMAL
        if (n == null) {
            return (new AVLNode(key));
        }
        // KEY < N.CATEGORIA
        if (key.compareToIgnoreCase(n.categoria) < 0) {
            n.left = insert(n.left, key);
            // KEY > N.CATEGORIA
        } else if (key.compareToIgnoreCase(n.categoria) > 0) {
            n.right = insert(n.right, key);
        } else {
            return n;
        }

        // ACTUALIZAR ALTURA DEL NODO PADRE
        n.heigth = 1 + max(height(n.left), height(n.right));

        // FACTOR DE EQUILIBRIO DEL NODO PADRE
        int balance = getBalance(n);

        // BALANCEO 
        // CASO 1: SIMPLE IZQUIERDA - IZQUIERDA
        if (balance == 2 && (key.compareToIgnoreCase(n.left.categoria) < 0)) {
            return rightRotate(n);
        }
        // CASO 2: SIMPLE DERECHA - DERECHA
        if (balance == -2 && (key.compareToIgnoreCase(n.right.categoria) > 0)) {
            return leftRotate(n);
        }
        // CASO 3: DOBLE IZQUIERDA - DERECHA
        if (balance == 2 && (key.compareToIgnoreCase(n.left.categoria) > 0)) {
            n.left = leftRotate(n.left);
            return rightRotate(n);
        }
        // CASO 4: DOBLE DERECHA - IZQUIERDA
        if (balance == -2 && (key.compareToIgnoreCase(n.right.categoria) > 0)) {
            n.right = rightRotate(n.right);
            return leftRotate(n);
        }

        // NO SE DESBALANCEO NUNCA
        return n;
    }

    // FUNCION PARA ENCONTRARA EL VALOR MINIMO DEL SUB ARBOL
    private AVLNode minValueNode(AVLNode n) {
        AVLNode currentNode = n;

        while (currentNode.left != null) {
            currentNode = currentNode.left;
        }

        return currentNode;
    }

    // ELIMINAR
    public void eliminarCategoria(String categoria) {
        root = deleteNode(root, categoria);
    }

    private AVLNode deleteNode(AVLNode n, String key) {
        // NO SE ENCONTRO EL NODO
        if (n == null) {
            return n;
        }
        if (key.compareToIgnoreCase(n.categoria) < 0) {
            n.left = deleteNode(n.left, key);
        } else if (key.compareToIgnoreCase(n.categoria) > 0) {
            n.right = deleteNode(n.right, key);
        } // NODO ENCONTRADO PARA ELIMINAR
        else {
            if (n.left == null || n.right == null) {
                // NODO SIN HIJOS
                AVLNode temp;
                if (n.left == null) {
                    temp = n.right;
                } else {
                    temp = n.left;
                }

                // NODO SIN HIJOS
                if (temp == null) {
                    temp = n;
                    n = null;
                    // NODO CON UN HIJO
                } else {
                    n = temp;
                }
            } else {
                // SI TIENE AMBOS HIJOS
                // BUSCA EL MAS PEQUEÑO DEL SUBARBOL DERECHO
                AVLNode temp = minValueNode(n.right);
                // COLOCAMOS EL NODO MAS PEQUEÑO EN EL NODO
                temp.categoria = temp.categoria;
                // ELIMINAR EL NODO
                n.right = deleteNode(n.right, temp.categoria);
            }
        }
        // SI EL ARVOL SOLO TIENE UN NODO
        if (n == null) {
            return n;
        }

        // ACTUALIZAR LA ALTUEA DEL NODO ACTUAL
        n.heigth = max(height(n.left), height(n.right));

        // FACTOR DE EQUILIBRIO
        int balance = getBalance(n);

        // BALANCEO
        if (balance > 1 && getBalance(n.left) >= 0) {
            return rightRotate(n);
        }
        if (balance > 1 && getBalance(n.left) < 0) {
            n.left = leftRotate(n.left);
            return rightRotate(n);
        }
        if (balance < -1 && getBalance(n.right) <= 0) {
            return leftRotate(n);
        }
        if (balance < -1 && getBalance(n.right) > 0) {
            n.right = rightRotate(n.right);
            return leftRotate(n);
        }

        return n;
    }

    private String createTree(AVLNode n, String escritura) {
        if (n != null) {
            if (n.left != null) {
                escritura += n.categoria + "->" + n.left.categoria + "\n";
                escritura = createTree(n.left, escritura);
            }
            if (n.right != null) {
                escritura += n.categoria + "->" + n.right.categoria + "\n";
                escritura = createTree(n.right, escritura);
            }
        }
        return escritura;
    }

    private String createTreeInOrder(AVLNode n, String escritura) {
        if (n != null) {
            escritura = createTreeInOrder(n.left, escritura);
            escritura += n.categoria + "->";
            escritura = createTreeInOrder(n.left, escritura);

        }
        return escritura;
    }

    private String createTreePreOrder(AVLNode n, String escritura) {
        if (n != null) {
            escritura += n.categoria + "->";
            escritura = createTreePreOrder(n.left, escritura);
            escritura = createTreePreOrder(n.left, escritura);

        }
        return escritura;
    }

    private String createTreePostOrder(AVLNode n, String escritura) {
        if (n != null) {
            escritura = createTreePostOrder(n.left, escritura);
            escritura = createTreePostOrder(n.left, escritura);
            escritura += n.categoria + "->";
        }
        return escritura;
    }

    public void generateDotTree() {
        String escritura = "";
        StringBuilder resultado = new StringBuilder();
        String rdot = "AVL_Categorias.dot";
        String rpng = "AVL_Categorias.png";
        AVLNode temp = root;
        resultado.append("digraph G{\nrankdir=TB;\nnode [margin=0 shape=circle height=1.2 color=crimson fontcolor=white style=filled ];\n");
        escritura = createTree(temp, escritura);
        resultado.append(escritura);
        resultado.append("\n}");
        generateDot(rdot, rpng, resultado.toString());
    }

    private void generateDot(String rdot, String rpng, String grafo) {

        FileWriter writer = null;
        try {
            // CREACION DE ARCHIVO CON EXTENSION .DOT
            File fileDot = new File(rdot);
            System.out.println(fileDot.getAbsoluteFile());
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

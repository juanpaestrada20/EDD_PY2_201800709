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
public class AVLTree {

    AVLNode root;

    public AVLTree() {
        root = null;
    }

    // FUNCION PARA OBTENER ALTURA DE LOS NODOS
    int height(AVLNode n) {
        return (n == null) ? 0 : n.heigth;
    }

    // FUNCION PARA OBTENER NUMERO MAYOR
    int max(int a, int b) {
        return (a > b) ? a : b;
    }

    // ROTACION SIMPLE DERECHA
    AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode temp = x.right;

        // ROTACION
        x.right = y;
        y.left = temp;

        // ACTUALIZAR ALTURAS
        y.heigth = max(height(y.left), height(y.right) + 1);
        x.heigth = max(height(x.left), height(x.right) + 1);

        // RETORNO DE NODO EQUILIBRADO
        return x;
    }

    // ROTACION SIMPLE IZQUIERDA
    AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode temp = y.left;

        // ROTACION
        y.left = x;
        y.right = temp;

        // ACTUALIZAR ALTURAS
        x.heigth = max(height(x.left), height(x.right) + 1);
        y.heigth = max(height(y.left), height(y.right) + 1);

        // RETORNO DE NODO EQUILIBRADO
        return y;
    }

    // FACTOR DE EQUILIBRIO
    int getBalance(AVLNode n) {
        return (n == null) ? 0 : (height(n.left) - height(n.right));
    }

    AVLNode insert(AVLNode n, String key) {
        // INSERCION DE ARBOL BINARIO NORMAL
        if (n == null) {
            return (new AVLNode(key));
        }
        // KEY < N.CATEGORIA
        if (key.compareToIgnoreCase(n.categoria) > 0) {
            n.left = insert(n.left, key);
            // KEY > N.CATEGORIA
        } else if (key.compareToIgnoreCase(n.categoria) < 0) {
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
        if (balance > 1 && (key.compareToIgnoreCase(n.left.categoria) > 0)) {
            return rightRotate(n);
        }
        // CASO 2: SIMPLE DERECHA - DERECHA
        if (balance < -1 && (key.compareToIgnoreCase(n.right.categoria) < 0)) {
            return leftRotate(n);
        }
        // CASO 3: DOBLE IZQUIERDA - DERECHA
        if (balance > 1 && (key.compareToIgnoreCase(n.left.categoria) > 0)) {
            n.left = leftRotate(n.left);
            return rightRotate(n);
        }
        // CASO 4: DOBLE DERECHA - IZQUIERDA
        if (balance < -1 && (key.compareToIgnoreCase(n.right.categoria) < 0)) {
            n.right = rightRotate(n.right);
            return leftRotate(n);
        }
        
        // NO SE DESBALANCEO NUNCA
        return n;
    }

}

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
public class BTreeNode {

    public String[] keys;         // ARREGLO DE LLAVES
    public int t;              // RANGO MINIMO
    public BTreeNode[] C;      // ARREGLO DE HIJOS
    public int n;              // NUMERO ACTUAL DE LLAVES
    public boolean leaf;       // VERIFICAR SI ES HOJA

    public BTreeNode(int t, boolean leaf) {
        this.t = t;
        this.leaf = leaf;
        this.keys = new String[2 * t - 1];
        this.C = new BTreeNode[2 * t];
        this.n = 0;
    }

    // FUNCION PARA RECORRER TODOS LOS NODOS DEL SUBARBOL
    public void traverse() {
        // SE RECORRE CADA LLAVE Y CADA HIJO
        int i = 0;
        for (i = 0; i < this.n; i++) {

            // SI NO ES HOJA, QUE RECORRA SUS HIJOS ANTES
            if (!this.leaf) {
                C[i].traverse();
            }
            System.out.print(keys[i] + " ");
        }

        // IMPRIMIR EL ULTIMO HIJO
        if (!leaf) {
            C[i].traverse();
        }
    }

    // FUNCION PARA BUSCAR
    public BTreeNode search(String k) {
        // ENCONTRAR LLAVE
        int i = 0;
        while (i < n && keys[i].compareToIgnoreCase(k) < 0) {
            i++;
        }

        // SI SE ENCUENTRA LA LLAVE
        if (keys[i] == k) {
            return this;
        }

        // SI NO SE ENCUENTRA LA LLAVE Y ES NODO HOJA
        if (leaf) {
            return null;
        }

        // CONTINUA BUSCANDO
        return C[i].search(k);
    }

    // INSERTAR LLAVE AL NODO NO LLENO
    public void insertNonFull(String k) {
        // INICIAR INDEX EN EL ELEMENTO MAS A LA DERECHA
        int i = n - 1;

        // SI EL NODO ES HOJA 
        if (leaf) {
            // ENCONTRAR LLAVE Y COLOCARLO DARLE EL ESPACIO
            while (i >= 0 && keys[i].compareToIgnoreCase(k) > 0) {
                keys[i + 1] = keys[i];
                i--;
            }

            // INSERTAR LLAVE EN EL ESPCIO
            keys[i + 1] = k;
            n = n + 1;
        } else {
            // ENCONTRAR DONDE SE DEBE COLOCAR
            while (i >= 0 && keys[i].compareToIgnoreCase(k) > 0) {
                i--;
            }

            // VERIFICAR SI EL HIJO ESTA LLENO
            if (C[i + 1].n == 2 * t - 1) {
                // SI EL HIJO ESTA LLENO SPLITEARLO
                splitChild(i + 1, C[i + 1]);

                // DESPUES DE SPLITEAR LA LLAVE DEL MEDIO SUBE
                if (keys[i + 1].compareToIgnoreCase(k) < 0) {
                    i++;
                }
            }
            C[i + 1].insertNonFull(k);
        }
    }

    // SPLIT EL NODO CUANDO ESTA LLENO
    public void splitChild(int i, BTreeNode y) {
        // CREAR NUEVO NODO
        BTreeNode z = new BTreeNode(y.t, y.leaf);
        z.n = t - 1;

        // COMPIAR LAS ULTIMAS LLAVES DE "Y" PARA "Z"
        for (int j = 0; j < t - 1; j++) {
            z.keys[j] = y.keys[j + t];
        }

        // COPIAR ULTIMO PARA Z 
        if (!y.leaf) {
            for (int j = 0; j < t; j++) {
                z.C[j] = y.C[j + t];
            }
        }
        // REDUCIR EL NUMERO DE LLAVES DE "Y"
        y.n = t - 1;

        // CREAR ESPACIO PARA EL NUEVO HIJO
        for (int j = n; j >= i + 1; j--) {
            C[j + 1] = C[j];
        }

        // CONECTAR HIJOS CON EL NODO
        C[i + 1] = z;

        // UNA LLAVE DE "Y" SE MUEVE AL NODO SE POSICION PARA ESA LLAVE
        for (int j = n - 1; j >= i; j--) {
            keys[j + 1] = keys[j];
        }

        // COPIAR LA LLAVE DEL MEDIO
        keys[i] = y.keys[t - 1];

        // INCREMENTAR EL CONTADOR DE LLAVES
        n = n + 1;
    }

}

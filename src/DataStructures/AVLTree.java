/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStructures;

import Administrador.Categorias;
import Clases.Libro;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static Inicio.InicioSesion.CategoriasAgregadas;
/**
 *
 * @author juanp
 */
public class AVLTree {

    public AVLNode root;

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
            System.out.println("Inserto categoria" + key);
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
        if (balance > 1 && (key.compareToIgnoreCase(n.left.categoria) < 0)) {
            return rightRotate(n);
        }
        // CASO 2: SIMPLE DERECHA - DERECHA
        if (balance < -1 && (key.compareToIgnoreCase(n.right.categoria) > 0)) {
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
        // SE ENCONTRO EL NODO
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
                // NODO SIN HIJOS O UN HIJO
                AVLNode temp = null;
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
                System.out.println(temp.categoria);
                // COLOCAMOS EL NODO MAS PEQUEÑO EN EL NODO
                n.categoria = temp.categoria;
                // ELIMINAR EL NODO
                n.right = deleteNode(n.right, temp.categoria);
            }
        }
        // SI EL ARVOL SOLO TIENE UN NODO
        if (n == null) {
            return n;
        }

        // ACTUALIZAR LA ALTUEA DEL NODO ACTUAL
        n.heigth = max(height(n.left), height(n.right)) + 1;

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

    // AGREGAR LIBROS A CATEGORIAS
    public void insertBook(Libro book) {
        AVLNode temp = searchCategory(book.getCategoria());
        if (temp != null) {
            System.out.println("Se agrego libro");
            temp.libros.insert(book);
        } else {
            this.agregarCategoria(book.getCategoria());
            CategoriasAgregadas.add(book.getCategoria());
            temp = searchCategory(book.getCategoria());
            temp.libros.insert(book);
            System.out.println("Se creo categoria");
        }
    }

    // BUSQUEDAS
    public AVLNode searchCategory(String nombre) {
        if (root != null) {
            return search(root, nombre);
        }
        return null;
    }

    private AVLNode search(AVLNode n, String nombre) {
        if (n == null) {
            return null;
        } else if (n.categoria.equalsIgnoreCase(nombre)) {
            return n;
        } else if (n.categoria.compareToIgnoreCase(nombre) > 0) {
            return search(n.left, nombre);
        } else {
            return search(n.right, nombre);
        }
    }

    // OBTENER ARBOL B DE CIERTA CATEGORIA
    public void getBooks(String category, String proper) {
        AVLNode temp = searchCategory(category);
        if (temp != null) {
            temp.libros.generateDotTree(proper);
        } else {
            System.out.println("No tiene libros");
        }
    }

    // BUSCAR LIBROS
    public Libro searchBooks(long libros) {
        AVLNode nodo = searchBooks2(root, libros);
        if (nodo == null) {
            return null;
        }
        Libro book = nodo.libros.buscarLibroISBN(libros);
        return book;

    }

    private AVLNode searchBooks2(AVLNode n, long libro) {
        if (n != null) {
            AVLNode busqueda = n;
            Libro book = busqueda.libros.buscarLibroISBN(libro);
            if (book != null) {
                if (book.getISBN() == libro) {
                    return busqueda;
                }
            }
            busqueda = searchBooks2(n.left, libro);
            if (busqueda != null) {
                return busqueda;
            }
            busqueda = searchBooks2(n.right, libro);
            return busqueda;
        }
        return null;
    }

    public ArrayList<Libro> searchBooksbyName(String libro) {
        ArrayList<Libro> librosEncontrados = new ArrayList<>();
        librosEncontrados = searchBooks3(root, librosEncontrados, libro);
        return librosEncontrados;
    }

    private ArrayList<Libro> searchBooks3(AVLNode n, ArrayList<Libro> libros, String libro) {
        if (n != null) {
            ArrayList<Libro> busqueda = n.libros.buscarLibroParteNombre(libro);
            if (busqueda != null) {
                libros.addAll(n.libros.buscarLibroParteNombre(libro));
            }
            libros = searchBooks3(n.left, libros, libro);
            libros = searchBooks3(n.right, libros, libro);
        }
        return libros;
    }

    public ArrayList<Libro> getBooksFromCategory(String category) {
        AVLNode categoria = searchCategory(category);
        if (categoria == null) {
            return null;
        } else {
            return categoria.libros.getLibros();
        }
    }

    // ELIMINAR LIBRO DE CATEGORIA
    public void deleteBook(long ISBN) {
        AVLNode temp = root;
        deleteLibro(temp, ISBN);
    }

    public void deleteBookName(String name) {
        AVLNode temp = root;
        deleteLibro2(temp, name);
    }

    // POR ISBN
    private void deleteLibro(AVLNode n, long libro) {
        if (n != null) {
            deleteLibro(n.left, libro);
            Libro aux = n.libros.buscarLibroISBN(libro);
            if (aux != null) {
                n.libros.eliminarLibro(libro);
                return;
            }
            deleteLibro(n.right, libro);
        }
    }

    // POR NOMBRE
    private void deleteLibro2(AVLNode n, String libro) {
        if (n != null) {
            deleteLibro2(n.left, libro);
            Libro aux = n.libros.buscarLibroNombre(libro);
            if (aux != null) {
                deleteLibro(n, aux.getISBN());
                return;
            }
            deleteLibro2(n.right, libro);
        }
    }

    public void deleteUserBooks(long carnet) {
        deleteBooks(root, carnet);
    }

    private void deleteBooks(AVLNode n, long carnet) {
        if (n != null) {
            deleteBooks(n.left, carnet);
            n.libros.deleteUserBooks(carnet);
            deleteBooks(n.right, carnet);
        }
    }

    // GRAFICAR
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
            escritura = createTreeInOrder(n.right, escritura);

        }
        return escritura;
    }

    private String createTreePreOrder(AVLNode n, String escritura) {
        if (n != null) {
            escritura += n.categoria + "->";
            escritura = createTreePreOrder(n.left, escritura);
            escritura = createTreePreOrder(n.right, escritura);

        }
        return escritura;
    }

    private String createTreePostOrder(AVLNode n, String escritura) {
        if (n != null) {
            escritura = createTreePostOrder(n.left, escritura);
            escritura = createTreePostOrder(n.right, escritura);
            escritura += n.categoria + "->";
        }
        return escritura;
    }

    public void generateDotTree(String proper) {
        if (root != null) {
            String escritura = "";
            StringBuilder resultado = new StringBuilder();
            String rdot = "AVL_Categorias" + proper + ".dot";
            AVLNode temp = root;
            resultado.append("digraph G{\nrankdir=TB;\nnode [margin=0 shape=box width=1.2 color=crimson fontcolor=white style=filled ];\n");
            escritura = createTree(temp, escritura);
            resultado.append(escritura);
            resultado.append("\n}");
            generateDot(rdot, resultado.toString());
        }
    }

    public void generatePreOrder(String proper) {
        String escritura = "";
        StringBuilder resultado = new StringBuilder();
        String rdot = "AVL_Categorias_PreOrder" + proper + ".dot";
        AVLNode temp = root;
        resultado.append("digraph G{\nrankdir=LR;\nnode [margin=0 shape=box width=1.2 color=crimson fontcolor=white style=filled ];\n");
        escritura = createTreePreOrder(temp, escritura);
        escritura = escritura.substring(0, escritura.length() - 2);
        resultado.append(escritura);
        resultado.append("\n}");
        generateDot(rdot, resultado.toString());
    }

    public void generateInOrder(String proper) {
        String escritura = "";
        StringBuilder resultado = new StringBuilder();
        String rdot = "AVL_Categorias_InOrder" + proper + ".dot";
        AVLNode temp = root;
        resultado.append("digraph G{\nrankdir=LR;\nnode [margin=0 shape=box width=1.2 color=crimson fontcolor=white style=filled ];\n");
        escritura = createTreeInOrder(temp, escritura);
        escritura = escritura.substring(0, escritura.length() - 2);
        resultado.append(escritura);
        resultado.append("\n}");
        generateDot(rdot, resultado.toString());
    }

    public void generatePostOrder(String proper) {
        String escritura = "";
        StringBuilder resultado = new StringBuilder();
        String rdot = "AVL_Categorias_PostOrder" + proper + ".dot";
        AVLNode temp = root;
        resultado.append("digraph G{\nrankdir=LR;\nnode [margin=0 shape=box width=1.2 color=crimson fontcolor=white style=filled ];\n");
        escritura = createTreePostOrder(temp, escritura);
        escritura = escritura.substring(0, escritura.length() - 2);
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
            if (fileDot.exists()) {
                fileDot.delete();
                fileDot = new File(rdot);
                fileDot.setReadable(true, false);
                fileDot.setWritable(true);
            }
            String rutaPng = fileDot.getAbsolutePath().substring(0, fileDot.getAbsolutePath().length() - 4) + ".png";

            writer = new FileWriter(fileDot);
            writer.write(grafo);
            writer.close();

            // CONSTRUCCION DEL COMANDO PARA GENERAR IMAGEN
            ProcessBuilder pBuilder;
            pBuilder = new ProcessBuilder("dot", "-Tpng", fileDot.getAbsolutePath(), "-o", rutaPng);
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

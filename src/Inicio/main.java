/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Inicio;

import Clases.Libro;
import DataStructures.AVLTree;
import DataStructures.BTree;
import java.util.ArrayList;

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
        BTree t = new BTree(3);

        t.insert(new Libro(846564506, "Harry Potter 1", "J.K.", "", 0, 0, "", "", 0));
        t.insert(new Libro(846564511, "Harry Potter 2", "J.K.", "", 0, 0, "", "", 0));
        t.insert(new Libro(846564505, "Harry Potter 3", "J.K.", "", 0, 0, "", "", 0));
        t.insert(new Libro(846564504, "Harry Potter 4", "J.K.", "", 0, 0, "", "", 0));
        t.insert(new Libro(846564508, "Harry Potter 5", "J.K.", "", 0, 0, "", "", 0));
        t.insert(new Libro(846564509, "Harry Potter 6", "J.K.", "", 0, 0, "", "", 0));
        t.insert(new Libro(846564512, "Harry Potter 7", "J.K.", "", 0, 0, "", "", 0));
        t.insert(new Libro(846564521, "Harry Potter 8", "J.K.", "", 0, 0, "", "", 0));
        t.insert(new Libro(846564514, "Harry Potter 9", "J.K.", "", 0, 0, "", "", 0));
        t.insert(new Libro(846564510, "Harry Potter 10", "J.K.", "", 0, 0, "", "", 0));
        t.insert(new Libro(846564519, "Harry Potter 11", "J.K.", "", 0, 0, "", "", 0));
        t.insert(new Libro(846564528, "camarada", "J.K.", "", 0, 0, "", "", 0));
        t.insert(new Libro(846564503, "Harry Potter 13", "J.K.", "", 0, 0, "", "", 0));
        t.insert(new Libro(846564517, "Harry Potter 14", "J.K.", "", 0, 0, "", "", 0));
        t.insert(new Libro(846564532, "Harry cama 15", "J.K.", "", 0, 0, "", "", 0));
        t.insert(new Libro(846564515, "Harry Potter 16", "J.K.", "", 0, 0, "", "", 0));
        t.insert(new Libro(846564516, "Harry Potter 17", "J.K.", "", 0, 0, "", "", 0));
        t.insert(new Libro(846564526, "Harry Potter 18", "J.K.", "", 0, 0, "", "", 0));
        t.insert(new Libro(846564527, "camara", "J.K.", "", 0, 0, "", "", 0));
        t.insert(new Libro(846564501, "Harry Potter 20", "J.K.", "", 0, 0, "", "", 0));
        t.insert(new Libro(846564548, "Harry Potter 21", "J.K.", "", 0, 0, "", "", 0));
        t.insert(new Libro(846564502, "Harry Potter 22", "J.K.", "", 0, 0, "", "", 0));
        t.insert(new Libro(846564563, "Harry Potter 23", "J.K.", "", 0, 0, "", "", 0));
        t.insert(new Libro(846564584, "Harry Potter 24", "J.K.", "", 0, 0, "", "", 0));
        t.insert(new Libro(846564533, "Harry Potter 25", "J.K.", "", 0, 0, "", "", 0));
        t.insert(new Libro(846564555, "Harry Potter 26", "J.K.", "", 0, 0, "", "", 0));
        t.insert(new Libro(846564566, "Harry Potter 27", "J.K.", "", 0, 0, "", "", 0));
        t.insert(new Libro(846564518, "Harry Potter 28", "J.K.", "", 0, 0, "", "", 0));
        t.insert(new Libro(846564519, "Harry Potter 29", "J.K.", "", 0, 0, "", "", 0));
        t.insert(new Libro(846564524, "Harry Potter 30", "J.K.", "", 0, 0, "", "", 0));
        t.insert(new Libro(846564500, "Harry Potter 31", "J.K.", "", 0, 0, "", "", 0));
        t.insert(new Libro(846564507, "Harry Potter 32", "J.K.", "", 0, 0, "", "", 0));
        t.insert(new Libro(846564588, "Harry Potter 33", "J.K.", "", 0, 0, "", "", 0));
        t.insert(new Libro(846564599, "Harry Potter 34", "J.K.", "", 0, 0, "", "", 0));

        t.generateDotTree();
        Libro temp = t.buscarLibroNombre("cam");
        
        if(temp == null){
            System.out.println("Nose encontro el libro");
            ArrayList<Libro> libros = t.buscarLibroParteNombre("cam");
            for (Libro libro : libros) {
                System.out.println(libro.getTitulo());
            }
        }else{
            System.out.println("Libro Encontrado");
        }
        
        t.eliminarLibro(846564500);
        t.eliminarLibro(846564501);
        t.eliminarLibro(846564519);
        t.eliminarLibro(846564532);
        t.eliminarLibro(846564599);
        t.eliminarLibro(846564500);
        t.generateDotTree();
        
        AVLTree tree = new AVLTree();

        tree.agregarCategoria("Novela");
        tree.agregarCategoria("Biografia");
        tree.agregarCategoria("Sagrados");
        tree.agregarCategoria("Comics");
        tree.agregarCategoria("Terror");
        tree.agregarCategoria("Romance");
        tree.agregarCategoria("Sagas");
        tree.agregarCategoria("Musica");
        tree.agregarCategoria("Aventura");
        tree.agregarCategoria("Misterio");
        tree.agregarCategoria("Informativo");
        tree.agregarCategoria("Poesia");
        tree.agregarCategoria("Suspenso");
        tree.agregarCategoria("Cientifico");
        tree.agregarCategoria("Matematica");
        tree.agregarCategoria("Quimica");
        tree.agregarCategoria("Ficcion");
        tree.agregarCategoria("Historia");
        tree.agregarCategoria("Piratas");
        tree.agregarCategoria("Comedia");
        tree.agregarCategoria("Canciones");
        tree.agregarCategoria("Filosofia");
        tree.agregarCategoria("Ciencias");
        tree.agregarCategoria("Tecnicos");
        tree.agregarCategoria("Vijaes");
        tree.agregarCategoria("Arte");

        tree.generateDotTree();
        tree.generateInOrder();
        tree.generatePreOrder();
        tree.generatePostOrder();
    }
}

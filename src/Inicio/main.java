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
import java.util.Scanner;

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
        long isbn =846564500;
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
        tree.agregarCategoria("Viajes");
        tree.agregarCategoria("Arte");

        
        tree.generateDotTree();
        tree.generateInOrder();
        tree.generatePreOrder();
        tree.generatePostOrder();
        int o = 0;
        int contador = 1;
        String cat = "";
        Scanner sc = new Scanner(System.in);
        do {            
            System.out.println("Agregar libro:");
            System.out.println("Categoria:");
            cat = sc.next();
            tree.insertBook(new Libro(isbn, "Libro" + contador, "", "", 0, 0, cat, "", 0));
            contador++;
            isbn++;
            System.out.println("Continuar?");
            o = sc.nextInt();
            if(o == 2){
                System.out.println("¿Que categoria desea ver?");
                cat = sc.next();
                tree.getBooks(cat);
            }
        } while (o != 4);
    }
}

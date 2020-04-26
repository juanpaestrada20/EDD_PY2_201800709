/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Inicio;

import DataStructures.AVLTree;
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
        AVLTree tree = new AVLTree(); 
        int opcion;
        String categoria;
        Scanner sc = new Scanner(System.in);
        do {            
            System.out.println("1. Agregar\n2. Graficar\n3. Eliminar"); 
            opcion = sc.nextInt();
            if(opcion==1){
                System.out.println("Ingrese categoria");
                categoria = sc.next();
                tree.agregarCategoria(categoria);
            }else if (opcion==2){
                tree.generateDotTree();
            }else if(opcion==3){
                System.out.println("Ingrese categoria");
                categoria = sc.next();
                tree.eliminarCategoria(categoria);
            }else{
                opcion = 4;
            }
            
        } while (opcion != 4);
    }
}

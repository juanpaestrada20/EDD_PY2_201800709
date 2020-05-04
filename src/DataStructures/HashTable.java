/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStructures;

import Clases.Usuario;
import static Inicio.InicioSesion.library;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author juanp
 */
public class HashTable {

    private final int size = 45;
    private List[] usersList;
    private int numberItems;
    private int arrayIndex;

    public HashTable() {
        this.usersList = new List[this.size];
        Arrays.fill(usersList, null);
        numberItems = 0;
        arrayIndex = 0;
    }

    private int hash(long k) {
        int key = 0;
        key = (int) k % size;
        return key;
    }

    public void insertUser(Usuario user) {
        arrayIndex = hash(user.getCarnet());
        Usuario buscado = search(user.getCarnet());
        if (buscado != null && buscado.getCarnet() == user.getCarnet()) {
            System.out.println("El usuario ya existe");
            return;
        }
        if (usersList[arrayIndex] == null) {
            usersList[arrayIndex] = new List();
            usersList[arrayIndex].insertUser(user);
            numberItems++;
            return;
        } else {
            if (numberItems < size) {
                while (usersList[arrayIndex] != null) {
                    arrayIndex++;
                    if (arrayIndex == 45) {
                        arrayIndex = 0;
                    }
                }
                usersList[arrayIndex] = new List();
                usersList[arrayIndex].insertUser(user);
                numberItems++;
                return;
            } else {
                usersList[arrayIndex].insertUser(user);
            }
        }
    }

    public Usuario search(long carnet) {
        int arrayIndexHash = hash(carnet);
        Usuario user = null;
        while (usersList[arrayIndexHash] != null) {
            user = usersList[arrayIndexHash].searchUser(carnet);
            if (user != null) {
                if (user.getCarnet() == carnet) {
                    return user;
                }
            }
            arrayIndexHash++;
            if (arrayIndexHash == 45) {
                arrayIndexHash = 0;
            }
            if (arrayIndexHash == hash(carnet)) {
                break;
            }
        }

        return user;
    }

    public void deleteUser(long carnet) {
        int arrayIndexHash = hash(carnet);
        Usuario user = null;
        while (usersList[arrayIndexHash] != null) {
            user = usersList[arrayIndexHash].searchUser(carnet);
            if (user != null) {
                if (user.getCarnet() == carnet) {
                    library.deleteUserBooks(carnet);
                    if(usersList[arrayIndexHash].getSize() > 1){
                        usersList[arrayIndexHash].deleteUser(carnet);
                        break;
                    }else{
                        usersList[arrayIndexHash] = null;
                        break;
                    }
                }
            }
            arrayIndexHash++;
            if (arrayIndexHash == 45) {
                arrayIndexHash = 0;
            }
            if (arrayIndexHash == hash(carnet)) {
                break;
            }
        }

    }

    private void deleteUserBooks(Usuario user){
        
    }
    
    private String createTable(String escritura) {
        escritura += "node1[height=55 label=\"";
        for (int i = 0; i < size; i++) {
            escritura += "<f" + i + "> " + i + "|";
        }
        escritura = escritura.substring(0, escritura.length() - 1);
        escritura += "\"];\n";
        for (int i = 0; i < size; i++) {
            if (usersList[i] != null) {
                if (usersList[i].getSize() >= 1) {
                    ListNode aux = usersList[i].first;
                    int contador = 0;
                    escritura += "A" + i + contador + "[label=\"Carnet: " + aux.user.getCarnet() + "\\nNombre: " + aux.user.getNombre() + " " + aux.user.getApellido() + "\\nCarrera: " + aux.user.getCarrera() + "\\n" + aux.user.getMD5() + "\"];\n";
                    aux = aux.next;
                    contador++;
                    while (aux != null) {
                        escritura += "A" + i + contador + "[label=\"Carnet: " + aux.user.getCarnet() + "\\nNombre: " + aux.user.getNombre() + " " + aux.user.getApellido() + "\\nCarrera: " + aux.user.getCarrera() + "\\n" + aux.user.getMD5() + "\"];\n";
                        aux = aux.next;
                        contador++;
                    }
                    contador = 0;
                    aux = usersList[i].first;
                    escritura += "node1:f" + i + "-> A" + i + contador;
                    aux = aux.next;
                    contador++;
                    while (aux != null) {
                        escritura += "-> A" + i + contador;
                        aux = aux.next;
                        contador++;
                    }
                    escritura += ";\n";
                }
            } else {

            }
        }

        return escritura;
    }

    public void generateTable() {
        String escritura = "";
        StringBuilder resultado = new StringBuilder();
        String rdot = "HashTable_Usuarios.dot";
        resultado.append("digraph G{\nrankdir=LR;\nnode [margin=0 shape=record width=1.2 fillcolor=dodgerblue fontcolor=white style=filled ];\n");
        escritura = createTable(escritura);
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
            Logger.getLogger(HashTable.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(HashTable.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStructures;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author juanp
 */
public class RedList implements Serializable {

    public Nodos first;
    private int size;

    public RedList() {
        this.first = null;
        this.size = 0;
    }

    public boolean insetarIP(String ip) {
        if (first == null) {
            first = new Nodos(ip);
        } else {
            Nodos aux = first;
            while (aux.next != null) {
                if (aux.ip.equals(ip)) {
                    return false;
                }
                aux = aux.next;
            }
            aux.next = new Nodos(ip);
        }
        size++;
        return true;
    }

    public void eliminarIP(String ip) {
        if (first != null) {
            boolean flag = false;
            if (first.ip.equals(ip)) {
                first = first.next;
            } else {
                Nodos prev = first;
                Nodos current = first.next;
                while (current.next != null) {
                    if (current.ip.equals(ip)) {
                        flag = true;
                        break;
                    }
                    prev = current;
                    current = current.next;
                }
                if (flag) {
                    prev.next = current.next;
                } else {
                    return;
                }
            }
            size--;
        }
    }

    public void graphicList() {
        if (first != null) {
            Nodos aux = first;
            StringBuilder grafo = new StringBuilder();
            grafo.append("digraph g{\nrankdir=LR;\nnode [margin=0 shape=box fillcolor=lightsalmon fontcolor=white style=filled];\n");
            int i = 0;
            grafo.append(i + "[label=\""+aux.ip+"\"];\n");
            i++;
            aux = aux.next;
            while (aux != null) {
                grafo.append(i + "[label=\""+aux.ip+"\"];\n");
                i++;
                aux = aux.next;
            }
            aux = first;
            i = 0;
            while (i < size-1) {
                grafo.append(i + "->" + (i + 1) + ";\n");
                i++;
            }
            grafo.append("}");
            generateDot("ListaIP.dot", grafo.toString());
        }
    }
    
    private void generateDot(String rdot, String grafo) {
        FileWriter writer = null;
        try {
            // CREACION DE ARCHIVO CON EXTENSION .DOT
            File fileDot = new File(rdot);
            fileDot.setWritable(true);
            String rutaPng = fileDot.getAbsolutePath().substring(0, fileDot.getAbsolutePath().length() - 3) + "png";
            File file = new File(rdot);
            writer = new FileWriter(file);
            writer.write(grafo);
            writer.close();

            // CONSTRUCCION DEL COMANDO PARA GENERAR IMAGEN
            ProcessBuilder pBuilder;
            pBuilder = new ProcessBuilder("dot", "-Tpng", "-o", rutaPng, fileDot.getAbsolutePath());
            pBuilder.start();

        } catch (IOException ex) {
            Logger.getLogger(RedList.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(RedList.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}

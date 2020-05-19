/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStructures;

import Clases.Bloque;
import static Inicio.InicioSesion.dataE;
import static Inicio.InicioSesion.library;
import static Inicio.InicioSesion.userTable;
import static Inicio.InicioSesion.listaUsuarios;
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
public class Blockchain  implements Serializable{
    public BlockNode genesis;
    public BlockNode last;
    private int size;

    public Blockchain() {
        this.genesis = null;
        this.last = null;
        this.size = 0;
    }
    
    public void RegisterNode(String IP){
        if(listaUsuarios.insetarIP(IP)){
            this.newBlock();
        }else{
            System.out.println("La IP ya fue ingresada! Intente con otra");
        }
    }
    
    public void deleteNode(String IP){
        listaUsuarios.eliminarIP(IP);
        this.newBlock();
    }
    
    public Blockchain sync(){
        return this;
    }
    
    public void newBlock(){
        if(size == 0){
            Bloque gene = new Bloque(size, dataE, null, library, userTable, listaUsuarios);
            gene.hash = "0000";
            genesis = new BlockNode(gene);
            last = genesis;
        }else{
            Bloque bloque = new Bloque(size, dataE, last.bloque.hash, library, userTable, listaUsuarios);
            BlockNode nuevo = new BlockNode(bloque);
            last.next = nuevo;
            nuevo.prev = last;
            last = nuevo;
        }
        size++;
        
    }
    
    public void graphicBlockChain(){
        if(genesis != null){
            BlockNode aux = genesis;
            StringBuilder grafo = new StringBuilder();
            grafo.append("digraph g{\nrankdir=LR;\nnode [margin=0 shape=box fillcolor=lightsalmon fontcolor=white style=filled];\n");
            int i = 0;
            grafo.append(i+"[label=\"Genesis:\\nIndex: "+aux.bloque.index+"\\nTimestamp: "+aux.bloque.timeStamp+"\\nNonce: "+aux.bloque.nonce+"\\nPreviousHash: \\nHash: "+aux.bloque.hash+"\"];\n");
            i++;
            aux = aux.next;
            while (aux != null) {                
                 grafo.append(i+"[label=\"Genesis:\\nIndex: "+aux.bloque.index+"\\nTimestamp: "+aux.bloque.timeStamp+"\\nNonce: "+aux.bloque.nonce+"\\nPreviousHash: "+aux.bloque.prevHash+"\\nHash: "+aux.bloque.hash+"\"];\n");
                 i++;
                 aux = aux.next;
            }
            aux = genesis;
            i = 0;
            while (i < size-1) {                
                grafo.append(i+"->"+(i+1)+"[dir=both];\n");
                i++;
            }
            grafo.append("}");
            generateDot("Blockchain.dot", grafo.toString());
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
            Logger.getLogger(Blockchain.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(Blockchain.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sockets;

import Clases.Usuario;
import DataStructures.AVLTree;
import DataStructures.Blockchain;
import DataStructures.HashTable;
import DataStructures.RedList;
import java.net.*;
import java.io.*;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author juanp
 */
public class Server extends Observable implements  Runnable{
    public static Blockchain blockchain = new Blockchain();
    
    private int puerto;
    
    public Server(int puerto){
        this.puerto = puerto;
    }

    @Override
    public void run() {
        
        ServerSocket servidor = null;
        Socket socket = null;
        DataInputStream in;
        DataOutputStream out;
        
        try{
            servidor = new ServerSocket(puerto);
            System.out.println("Servidor Iniciado...");
            
            while (true) {                
                socket = servidor.accept();
                
                System.out.println("Cliente conectado...");
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());
                
                String msj = in.readUTF();
                System.out.println(msj);
                
                this.setChanged();
                this.notifyObservers(msj);
                this.clearChanged();
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
}

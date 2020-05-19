/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sockets;

import DataStructures.Blockchain;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author juanp
 */
public class Cliente extends Observable implements Runnable {
    private int puerto;
    private Object mensaje;
    private String host;

    public Cliente(String host,int puerto, Object mensaje) {
        this.host = host;
        this.puerto = puerto;
        this.mensaje = mensaje;
    }
    
    
    @Override
    public void run() {
        ObjectOutputStream out;
        ObjectInputStream in;
        
        try{
            Socket sc = new Socket(host, puerto);
            out = new ObjectOutputStream(sc.getOutputStream());
            
            out.writeObject(mensaje);
            
            sc.close();
        
        }catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

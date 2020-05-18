/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author juanp
 */
public class Cliente implements Runnable {
    private int puerto;
    private String mensaje;
    private String host;

    public Cliente(String host,int puerto, String mensaje) {
        this.host = host;
        this.puerto = puerto;
        this.mensaje = mensaje;
    }
    
    
    @Override
    public void run() {
        DataOutputStream out;
        
        try{
            Socket sc = new Socket(host, puerto);
            out = new DataOutputStream(sc.getOutputStream());
            
            out.writeUTF(mensaje);
            
            sc.close();
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

}

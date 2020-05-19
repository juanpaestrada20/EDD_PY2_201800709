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
import static Inicio.InicioSesion.listaUsuarios;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author juanp
 */
public class Server extends Observable implements Runnable {

    private ArrayList<Socket> clientes;
    private int puerto;

    public Server(int puerto) {
        this.puerto = puerto;
        clientes = new ArrayList<>();
    }

    @Override
    public void run() {

        ServerSocket servidor = null;
        Socket socket = null;
        ObjectInputStream in;

        try {
            servidor = new ServerSocket(puerto);
            System.out.println("Servidor Iniciado...");
            while(true){
                socket = servidor.accept();
                clientes.add(socket);
                System.out.println("Cliente conectado...");
                in = new ObjectInputStream(socket.getInputStream());
                Object msj = in.readObject();
                this.setChanged();
                this.notifyObservers(msj);
                this.clearChanged();
                socket.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendInfo(Blockchain blockchain) {
        
        for (Socket cliente : clientes) {
            ObjectOutputStream dos;
            try {
                dos = new ObjectOutputStream(cliente.getOutputStream());
                dos.writeObject(blockchain);
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}

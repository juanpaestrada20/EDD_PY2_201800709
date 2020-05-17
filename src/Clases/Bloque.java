/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import DataStructures.AVLTree;
import DataStructures.HashTable;
import DataStructures.RedList;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author juanp
 */
public class Bloque {

    public int index;
    public String timeStamp;
    public String data;
    public int nonce;
    public String prevHash;
    public String hash;
    public AVLTree libreria;
    public HashTable usuarios;
    public RedList listaNodos;
    public String todo;

    public Bloque(int index, String data, String prevHash, AVLTree libreria, HashTable usuarios, RedList listaNodos) {
        this.index = index;
        this.timeStamp = getDate();
        this.data = data;
        this.todo = index+timeStamp+prevHash;
        this.nonce = getNonce(todo);
        this.prevHash = prevHash;
        this.libreria = libreria;
        this.usuarios = usuarios;
        this.listaNodos = listaNodos;
    }

    private String getDate(){
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        return format.format(date);
    }
    
    private int getNonce(String base){
        int nonceTry = 0;
        String prueba = "";
        while (true) {            
            prueba = sha256(base+String.valueOf(nonceTry));
            System.out.println("Intento #"+nonceTry);
            if(prueba.substring(0,4).equals("0000")){
                break;
            }
            nonceTry++;
        }
        this.hash = prueba;
        return nonceTry;
    }

    
    private String sha256(String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Bloque.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Bloque.class.getName()).log(Level.SEVERE, null, ex);
        }
        return base;
    }
}
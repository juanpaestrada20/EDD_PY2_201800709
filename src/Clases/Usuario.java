/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import DataStructures.AVLTree;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author juanpa
 */
public class Usuario implements Serializable {

    private long carnet;
    private String nombre;
    private String apellido;
    private String carrera;
    private String contrasena;
    private String md5;
    private AVLTree librosPersonales;

    public Usuario(long carnet, String nombre, String apellido, String carrera, String contrasena) {
        this.carnet = carnet;
        this.nombre = nombre;
        this.apellido = apellido;
        this.carrera = carrera;
        this.contrasena = contrasena;
        this.md5 = encriptar(contrasena);
        this.librosPersonales = new AVLTree();
    }

    public long getCarnet() {
        return carnet;
    }

    public void setCarnet(long carnet) {
        this.carnet = carnet;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
        this.md5 = encriptar(contrasena);
    }
    
    public AVLTree getLibros() {
        return librosPersonales;
    }

    public void agregarLibro(Libro book) {
        librosPersonales.insertBook(book);
    }

    public String getMD5() {
        return md5;
    }

    public void setMD5(String md) {
        this.md5 = md5;
    }

    private String encriptar(String pass) {
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        m.reset();
        m.update(pass.getBytes());
        byte[] digest = m.digest();
        BigInteger bigInt = new BigInteger(1, digest);
        String hashtext = bigInt.toString(16);
        
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
        System.out.println(hashtext);
        return hashtext;
    }
}

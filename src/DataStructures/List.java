/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStructures;

import Clases.Usuario;

/**
 *
 * @author juanp
 */
public class List {

    public ListNode first;
    public ListNode last;
    private int size;

    public List() {
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    public int getSize() {
        return size;
    }

    public void insertUser(Usuario user) {
        ListNode nuevo = new ListNode(user);
        if (first == null) {
            first = nuevo;
            last = nuevo;
        } else {
            last.next = nuevo;
            last = nuevo;
        }
        size++;
    }

    public Usuario searchUser(long carnet) {
        ListNode aux = first;
        boolean found = false;
        while (aux != null) {
            if (aux.user.getCarnet() == carnet) {
                found = true;
                break;
            }
            aux = aux.next;
        }
        if (found) {
            return aux.user;
        } else {
            return null;
        }
    }

    public void deleteUser(long carnet) {
        if (first == null) {
            return;
        } else {
            ListNode prev = first;
            ListNode current = first.next;

            // ELIMINAR PRIMERO
            if (prev == first && prev.user.getCarnet() == carnet) {
                first = first.next;
                size--;
                return;
            } // ELIMINAR EN MEDIO
            else {
                while (current != null && current.user.getCarnet() == carnet) {
                    prev = current;
                    current = current.next;
                }
                // SI ES EL ULTIMO
                if (current == last && current.user.getCarnet() == carnet) {
                    last = prev;
                    prev.next = null;
                    size--;
                    return;
                }else{
                   prev.next= current.next;
                   size--;
                   return;
                }
            }
        }
    }
}

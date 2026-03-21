/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edd;


public class CustomQueue <T> {
  private CustomLinkedList<T> list;

    public CustomQueue() {
        this.list = new CustomLinkedList<>();
    }

    // Encolar (añadir a la espera)
    public void enqueue(T data) {
        list.addLast(data);
    }

    // Desencolar (atender al primero)
    public T dequeue() {
        return list.removeFirst();
    }
    
    // Ver quién es el primero sin sacarlo de la cola
    public T peek() {
        if (list.isEmpty()) return null;
        return list.get(0);
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public int size() {
        return list.getSize();
    }

}

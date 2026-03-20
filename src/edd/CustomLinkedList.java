/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edd;

public class CustomLinkedList <T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public CustomLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    // Añadir al final
    public void addLast(T data) {
        Node<T> newNode = new Node<>(data);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            tail.setNext(newNode);
            newNode.setPrevious(tail);
            tail = newNode;
        }
        size++;
    }

    // Remover el primero (útil para las colas)
    public T removeFirst() {
        if (isEmpty()) return null;
        
        T data = head.getData();
        if (head == tail) {
            head = tail = null;
        } else {
            head = head.getNext();
            head.setPrevious(null);
        }
        size--;
        return data;
    }

    // Obtener un elemento por su índice (para recorrer con un for clásico)
    public T get(int index) {
        if (index < 0 || index >= size) return null;
        
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        return current.getData();
    }
    
    // Eliminar un objeto específico
    public boolean remove(T data) {
        Node<T> current = head;
        while (current != null) {
            if (current.getData().equals(data)) {
                if (current == head) {
                    removeFirst();
                } else if (current == tail) {
                    tail = current.getPrevious();
                    tail.setNext(null);
                    size--;
                } else {
                    current.getPrevious().setNext(current.getNext());
                    current.getNext().setPrevious(current.getPrevious());
                    size--;
                }
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    public boolean isEmpty() { return size == 0; }
    public int getSize() { return size; }
}

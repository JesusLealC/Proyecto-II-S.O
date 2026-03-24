/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */



package scheduler;

import edd.CustomLinkedList;
import edd.CustomQueue;
import process.PCB;

public class SCANScheduler implements DiskScheduler {
    private boolean moveRightFirst;
    private CustomQueue<PCB> colaSolicitudes;

    public SCANScheduler() {
        this(true);
    }

    public SCANScheduler(boolean moveRightFirst) {
        this.moveRightFirst = moveRightFirst;
        this.colaSolicitudes = new CustomQueue<>();
    }

    @Override
    public void encolarSolicitud(PCB proceso) {
        colaSolicitudes.enqueue(proceso);
    }

    @Override
    public PCB procesarSiguiente(int posicionActualCabezal) {
        if (colaSolicitudes.isEmpty()) {
            return null;
        }
        
        // En una implementación completa de SCAN, aquí buscarías el PCB 
        // cuyo bloque objetivo coincida con el siguiente paso de la ruta.
        // Por ahora, retorna el desencolado básico para cumplir la interfaz.
        return colaSolicitudes.dequeue();
    }

    @Override
    public CustomLinkedList<Integer> calculateRoute(CustomLinkedList<Integer> requestedBlocks, int currentPosition) {
        CustomLinkedList<Integer> route = new CustomLinkedList<>();
        if (requestedBlocks.getSize() == 0) return route;

        CustomLinkedList<Integer> sorted = new CustomLinkedList<>();
        for (int i = 0; i < requestedBlocks.getSize(); i++) {
            sorted.addLast(requestedBlocks.get(i));
        }
        
        sortList(sorted);

        CustomLinkedList<Integer> left = new CustomLinkedList<>();
        CustomLinkedList<Integer> right = new CustomLinkedList<>();

        for (int i = 0; i < sorted.getSize(); i++) {
            int block = sorted.get(i);
            if (block < currentPosition) {
                left.addLast(block);
            } else {
                right.addLast(block);
            }
        }

        if (moveRightFirst) {
            for (int i = 0; i < right.getSize(); i++) {
                route.addLast(right.get(i));
            }
            for (int i = left.getSize() - 1; i >= 0; i--) {
                route.addLast(left.get(i));
            }
        } else {
            for (int i = left.getSize() - 1; i >= 0; i--) {
                route.addLast(left.get(i));
            }
            for (int i = 0; i < right.getSize(); i++) {
                route.addLast(right.get(i));
            }
        }

        return route;
    }

    private void sortList(CustomLinkedList<Integer> list) {
        int n = list.getSize();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (list.get(j) > list.get(j + 1)) {
                    int temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
    }

    public boolean isMoveRightFirst() {
        return moveRightFirst;
    }

    public void setMoveRightFirst(boolean moveRightFirst) {
        this.moveRightFirst = moveRightFirst;
    }

    @Override
    public String getAlgorithmName() {
        return "SCAN";
    }
}
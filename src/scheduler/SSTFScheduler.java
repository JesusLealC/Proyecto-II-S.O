/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
*/

package scheduler;

import edd.CustomLinkedList;
import edd.CustomQueue;
import process.PCB;

public class SSTFScheduler implements DiskScheduler {
    private CustomQueue<PCB> colaSolicitudes;

    public SSTFScheduler() {
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
        return colaSolicitudes.dequeue();
    }

    @Override
    public CustomLinkedList<Integer> calculateRoute(CustomLinkedList<Integer> requestedBlocks, int currentPosition) {
        CustomLinkedList<Integer> pending = new CustomLinkedList<>();
        for (int i = 0; i < requestedBlocks.getSize(); i++) {
            pending.addLast(requestedBlocks.get(i));
        }

        CustomLinkedList<Integer> route = new CustomLinkedList<>();
        int head = currentPosition;

        while (!pending.isEmpty()) {
            int nearestValue = pending.get(0);
            int nearestDistance = Math.abs(nearestValue - head);

            for (int i = 1; i < pending.getSize(); i++) {
                int currentValue = pending.get(i);
                int distance = Math.abs(currentValue - head);
                if (distance < nearestDistance) {
                    nearestDistance = distance;
                    nearestValue = currentValue;
                }
            }

            // Usamos el método remove(T data) de tu CustomLinkedList enviando el valor, no el índice
            pending.remove(nearestValue);
            route.addLast(nearestValue);
            head = nearestValue;
        }

        return route;
    }

    @Override
    public String getAlgorithmName() {
        return "SSTF";
    }
}
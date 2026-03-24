/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package scheduler;

import edd.CustomLinkedList;
import edd.CustomQueue;
import process.PCB;

public class CSCANScheduler implements DiskScheduler {
    private CustomQueue<PCB> colaSolicitudes;

    public CSCANScheduler() {
        this.colaSolicitudes = new CustomQueue<>();
    }

    @Override
    public void encolarSolicitud(PCB proceso) {
        colaSolicitudes.enqueue(proceso);
    }

    @Override
    public PCB procesarSiguiente(int posicionActualCabezal) {
        if (colaSolicitudes.isEmpty()) return null;
        return colaSolicitudes.dequeue();
    }

    @Override
    public CustomLinkedList<Integer> calculateRoute(CustomLinkedList<Integer> requestedBlocks, int currentPosition) {
        CustomLinkedList<Integer> route = new CustomLinkedList<>();
        if (requestedBlocks.getSize() == 0) return route;

        CustomLinkedList<Integer> sorted = sortListAndReturnNew(requestedBlocks);

        CustomLinkedList<Integer> right = new CustomLinkedList<>();
        CustomLinkedList<Integer> left = new CustomLinkedList<>();

        for (int i = 0; i < sorted.getSize(); i++) {
            int block = sorted.get(i);
            if (block >= currentPosition) {
                right.addLast(block);
            } else {
                left.addLast(block);
            }
        }

        // C-SCAN: Va hacia la derecha, salta al inicio (0) y sigue hacia la derecha.
        for (int i = 0; i < right.getSize(); i++) {
            route.addLast(right.get(i));
        }
        for (int i = 0; i < left.getSize(); i++) {
            route.addLast(left.get(i));
        }

        return route;
    }

    private CustomLinkedList<Integer> sortListAndReturnNew(CustomLinkedList<Integer> list) {
        int n = list.getSize();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = list.get(i);

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }

        CustomLinkedList<Integer> sortedList = new CustomLinkedList<>();
        for (int i = 0; i < n; i++) sortedList.addLast(arr[i]);
        return sortedList;
    }

    @Override
    public String getAlgorithmName() {
        return "C-SCAN";
    }
}

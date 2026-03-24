/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */



package scheduler;
import edd.CustomLinkedList;
import edd.CustomQueue;
import process.PCB;

public class FIFOScheduler implements DiskScheduler {
    
    private CustomQueue<PCB> colaSolicitudes;

    public FIFOScheduler() {
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
        // En FIFO, simplemente sacamos el primero que llegó a la cola
        return colaSolicitudes.dequeue();
    }

    @Override
    public CustomLinkedList<Integer> calculateRoute(CustomLinkedList<Integer> requestedBlocks, int currentPosition) {
        CustomLinkedList<Integer> route = new CustomLinkedList<>();
        
        for (int i = 0; i < requestedBlocks.getSize(); i++) {
            route.addLast(requestedBlocks.get(i));
        }
        
        return route;
    }

    @Override
    public String getAlgorithmName() {
        return "FIFO";
    }
}
package scheduler;

import edd.CustomLinkedList;
import process.PCB;

public interface DiskScheduler {
    
    void encolarSolicitud(PCB proceso);
    
    PCB procesarSiguiente(int posicionActualCabezal);
    
    CustomLinkedList<Integer> calculateRoute(CustomLinkedList<Integer> requestedBlocks, int currentPosition);
    
    String getAlgorithmName();
}
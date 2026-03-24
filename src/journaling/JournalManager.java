/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */



package journaling;
import edd.CustomLinkedList;
import filesystem.FileSystemManager;
import util.OSLogger;

public class JournalManager {
    private CustomLinkedList<JournalEntry> log;
    private boolean isCrashed;

    public JournalManager() {
        this.log = new CustomLinkedList<>();
        this.isCrashed = false;
    }

    public JournalEntry logOperation(String operation, String fileName, int firstBlockId) {
        JournalEntry entry = new JournalEntry(operation, fileName, firstBlockId);
        log.addLast(entry);
        OSLogger.log("Journal", "Registrado: " + entry.toString());
        return entry;
    }

    public void commitOperation(JournalEntry entry) {
        if (isCrashed) return; // Si hay fallo, no se confirma
        entry.commit();
        OSLogger.log("Journal", "Confirmado: " + entry.toString());
    }

    public void simulateCrash() {
        this.isCrashed = true;
        OSLogger.log("CRASH", "¡Fallo del sistema simulado!");
    }

    public void recoverSystem(FileSystemManager fsManager) {
        OSLogger.log("RECOVERY", "Iniciando recuperación ante fallos...");
        for (int i = log.getSize() - 1; i >= 0; i--) {
            JournalEntry entry = log.get(i);
            if (entry.getStatus().equals("PENDIENTE")) {
                OSLogger.log("RECOVERY", "Deshaciendo (UNDO) operación: " + entry.getOperation() + " en " + entry.getFileName());
                
                // Lógica simple de UNDO: Si se estaba creando, liberar los bloques asignados prematuramente [cite: 82]
                if (entry.getOperation().equals("CREATE") && entry.getFirstBlockId() != -1) {
                    fsManager.getDisk().freeBlocks(entry.getFirstBlockId());
                }
                // Si la operación era DELETE, en un sistema real restauraríamos la data. 
                // Para este simulador, evitamos que se borre si no hizo commit.
            }
        }
        log = new CustomLinkedList<>(); // Limpiar log tras recuperación
        isCrashed = false;
        OSLogger.log("RECOVERY", "Sistema recuperado.");
    }

    public CustomLinkedList<JournalEntry> getLog() {
        return log;
    }
    
    public boolean isSystemCrashed() {
        return isCrashed;
    }
}
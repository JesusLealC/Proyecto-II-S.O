/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author jleal
 */
package journaling;

import edd.CustomLinkedList;
import edd.CustomQueue;
import util.OSLogger;

public class JournalManager {
    private final CustomQueue<JournalEntry> pendingEntries;
    private final CustomLinkedList<JournalEntry> historyEntries;

    public JournalManager() {
        this.pendingEntries = new CustomQueue<>();
        this.historyEntries = new CustomLinkedList<>();
    }

    public JournalEntry registerPendingOperation(String operationType, String fileName) {
        return registerPendingOperation(operationType, fileName, "");
    }

    public JournalEntry registerPendingOperation(String operationType, String fileName, String details) {
        JournalEntry entry = new JournalEntry(operationType, fileName, JournalEntry.STATUS_PENDING, details);
        pendingEntries.enqueue(entry);
        historyEntries.addLast(entry);
        OSLogger.log("JournalManager", "Operación registrada en journal: " + operationType + " - " + fileName);
        return entry;
    }

    public boolean completeOperation(String operationType, String fileName) {
        JournalEntry entry = findLatestEntry(operationType, fileName, JournalEntry.STATUS_PENDING);
        if (entry == null) {
            return false;
        }

        entry.setStatus(JournalEntry.STATUS_COMPLETED);
        rebuildPendingQueue();
        OSLogger.log("JournalManager", "Operación completada: " + operationType + " - " + fileName);
        return true;
    }

    public boolean failOperation(String operationType, String fileName, String details) {
        JournalEntry entry = findLatestEntry(operationType, fileName, JournalEntry.STATUS_PENDING);
        if (entry == null) {
            return false;
        }

        entry.setStatus(JournalEntry.STATUS_FAILED);
        entry.setDetails(details);
        rebuildPendingQueue();
        OSLogger.log("JournalManager", "Operación fallida: " + operationType + " - " + fileName);
        return true;
    }

    public CustomQueue<JournalEntry> recoverPendingOperations() {
        rebuildPendingQueue();
        return pendingEntries;
    }

    public CustomLinkedList<JournalEntry> getHistoryEntries() {
        return historyEntries;
    }

    private JournalEntry findLatestEntry(String operationType, String fileName, String status) {
        for (int i = historyEntries.getSize() - 1; i >= 0; i--) {
            JournalEntry entry = historyEntries.get(i);
            if (entry != null
                    && entry.getOperationType().equalsIgnoreCase(operationType)
                    && entry.getFileName().equalsIgnoreCase(fileName)
                    && entry.getStatus().equalsIgnoreCase(status)) {
                return entry;
            }
        }
        return null;
    }

    private void rebuildPendingQueue() {
        while (!pendingEntries.isEmpty()) {
            pendingEntries.dequeue();
        }

        for (int i = 0; i < historyEntries.getSize(); i++) {
            JournalEntry entry = historyEntries.get(i);
            if (entry != null && JournalEntry.STATUS_PENDING.equals(entry.getStatus())) {
                pendingEntries.enqueue(entry);
            }
        }
    }
}
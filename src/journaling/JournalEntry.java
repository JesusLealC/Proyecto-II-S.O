/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */



package journaling;

public class JournalEntry {
    private String operation; // "CREATE", "DELETE"
    private String fileName;
    private int firstBlockId;
    private String status; // "PENDIENTE", "CONFIRMADA"

    public JournalEntry(String operation, String fileName, int firstBlockId) {
        this.operation = operation;
        this.fileName = fileName;
        this.firstBlockId = firstBlockId;
        this.status = "PENDIENTE"; // Inicia como pendiente [cite: 76]
    }

    public void commit() {
        this.status = "CONFIRMADA"; // Se marca confirmada tras el éxito [cite: 78]
    }

    public String getStatus() { return status; }
    public String getOperation() { return operation; }
    public String getFileName() { return fileName; }
    public int getFirstBlockId() { return firstBlockId; }

    @Override
    public String toString() {
        return operation + " '" + fileName + "': " + status;
    }
}
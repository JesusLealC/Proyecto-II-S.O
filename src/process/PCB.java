/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package process;


public class PCB {
    private int processId;
    private ProcessState state; 
    
    // --- NUEVOS ATRIBUTOS PARA E/S DE DISCO ---
    private String operation; // "CREATE", "READ", "UPDATE", "DELETE"
    private String fileName;  // Archivo sobre el que se hará la operación
    private int fileSizeInBlocks; // Solo útil si la operation es "CREATE"
    private String userMode; // "ADMIN" o "USER" para validar permisos

    // Constructor actualizado
    public PCB(int processId, String operation, String fileName, int fileSizeInBlocks, String userMode) {
        this.processId = processId;
        // El estado inicial según el PDF debe ser "nuevo"
        this.state = ProcessState.NEW; 
        this.operation = operation;
        this.fileName = fileName;
        this.fileSizeInBlocks = fileSizeInBlocks;
        this.userMode = userMode;
    }

    // --- GETTERS Y SETTERS ---
    public int getProcessId() {
        return processId;
    }

    public ProcessState getState() {
        return state;
    }

    public void setState(ProcessState state) {
        this.state = state;
    }

    public String getOperation() {
        return operation;
    }

    public String getFileName() {
        return fileName;
    }

    public int getFileSizeInBlocks() {
        return fileSizeInBlocks;
    }

    public String getUserMode() {
        return userMode;
    }

    @Override
    public String toString() {
        return "PID: " + processId + " | Op: " + operation + " '" + fileName + "' | Estado: " + state;
    }
}
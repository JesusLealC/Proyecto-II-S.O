/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filesystem;
 import java.awt.Color;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileMetadata {
    private String name;
    private boolean isDirectory;
    private int sizeInBlocks;
    private int firstBlockId; 
    private String owner;     
    private String creationDate;
    private Color fileColor;  

    // Atributos añadidos para el control de concurrencia
    private int activeReadLocks;
    private boolean hasWriteLock;

    public FileMetadata(String name, boolean isDirectory, int sizeInBlocks, String owner, Color fileColor) {
        this.name = name;
        this.isDirectory = isDirectory;
        this.sizeInBlocks = isDirectory ? 0 : sizeInBlocks;
        this.firstBlockId = -1; 
        this.owner = owner;
        this.fileColor = fileColor;
        
        this.activeReadLocks = 0;
        this.hasWriteLock = false;
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        this.creationDate = dtf.format(LocalDateTime.now());
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public boolean isDirectory() { return isDirectory; }
    
    public int getSizeInBlocks() { return sizeInBlocks; }
    
    public int getFirstBlockId() { return firstBlockId; }
    public void setFirstBlockId(int firstBlockId) { this.firstBlockId = firstBlockId; }

    public String getOwner() { return owner; }
    public String getCreationDate() { return creationDate; }
    public Color getFileColor() { return fileColor; }

    // --- Lógica de Concurrencia ---
    
    public synchronized boolean acquireReadLock() {
        if (hasWriteLock) {
            return false;
        }
        activeReadLocks++;
        return true;
    }

    public synchronized void releaseReadLock() {
        if (activeReadLocks > 0) {
            activeReadLocks--;
        }
    }

    public synchronized boolean acquireWriteLock() {
        if (hasWriteLock || activeReadLocks > 0) {
            return false;
        }
        hasWriteLock = true;
        return true;
    }

    public synchronized void releaseWriteLock() {
        hasWriteLock = false;
    }
    
    public boolean isLocked() {
        return hasWriteLock || activeReadLocks > 0;
    }
    
    public String getLockStatus() {
        if (hasWriteLock) return "Escritura (Exclusivo)";
        if (activeReadLocks > 0) return "Lectura (" + activeReadLocks + " compartidos)";
        return "Libre";
    }
}
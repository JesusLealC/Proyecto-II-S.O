/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filesystem;

import java.awt.Color;

public class Block {
    private int blockId;
    private boolean isOccupied;
    private int nextBlockId; // Puntero para la Asignación Encadenada
    private Color blockColor; // Color para mostrar en la interfaz
    private String data; // Simulación del contenido del bloque

    public Block(int blockId) {
        this.blockId = blockId;
        this.isOccupied = false;
        this.nextBlockId = -1; // -1 significa que es el final del archivo o está libre
        this.blockColor = Color.LIGHT_GRAY; // Gris claro = Libre
        this.data = "";
    }

    public int getBlockId() { return blockId; }
    
    public boolean isOccupied() { return isOccupied; }
    public void setOccupied(boolean occupied) { isOccupied = occupied; }
    
    public int getNextBlockId() { return nextBlockId; }
    public void setNextBlockId(int nextBlockId) { this.nextBlockId = nextBlockId; }
    
    public Color getBlockColor() { return blockColor; }
    public void setBlockColor(Color blockColor) { this.blockColor = blockColor; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }

    // Método para limpiar el bloque (útil cuando se borra un archivo)
    public void clear() {
        this.isOccupied = false;
        this.nextBlockId = -1;
        this.blockColor = Color.LIGHT_GRAY;
        this.data = "";
    }
}

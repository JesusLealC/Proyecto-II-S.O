/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filesystem;
import java.awt.Color;

/**
 * El Hardware del almacenamiento. Maneja el arreglo de bloques.
 */


public class SimulatedDisk {
      private Block[] blocks;
    private int headPosition; // Posición de la cabeza de lectura/escritura
    private int totalCapacity;

    public SimulatedDisk(int totalCapacity) {
        this.totalCapacity = totalCapacity;
        this.blocks = new Block[totalCapacity];
        this.headPosition = 0;

        // Inicializar el disco con bloques vacíos
        for (int i = 0; i < totalCapacity; i++) {
            blocks[i] = new Block(i);
        }
    }

    /**
     * Asignación Encadenada: Busca espacio y enlaza los bloques.
     * Retorna el ID del primer bloque, o -1 si no hay espacio.
     */
    public int allocateBlocks(int blocksNeeded, Color fileColor) {
        if (getFreeSpace() < blocksNeeded) return -1;

        int firstAllocatedBlock = -1;
        int previousBlock = -1;
        int blocksAllocated = 0;

        for (int i = 0; i < totalCapacity && blocksAllocated < blocksNeeded; i++) {
            if (!blocks[i].isOccupied()) {
                blocks[i].setOccupied(true);
                blocks[i].setBlockColor(fileColor);

                if (firstAllocatedBlock == -1) {
                    firstAllocatedBlock = i;
                }

                if (previousBlock != -1) {
                    blocks[previousBlock].setNextBlockId(i); // Encadenar
                }

                previousBlock = i;
                blocksAllocated++;
            }
        }
        return firstAllocatedBlock;
    }

    /**
     * Libera una cadena de bloques a partir de su bloque inicial.
     */
    public void freeBlocks(int firstBlockId) {
        int currentBlockId = firstBlockId;
        while (currentBlockId != -1) {
            Block currentBlock = blocks[currentBlockId];
            int next = currentBlock.getNextBlockId(); 
            currentBlock.clear();
            currentBlockId = next; 
        }
    }

    public int getFreeSpace() {
        int count = 0;
        for (Block b : blocks) {
            if (!b.isOccupied()) count++;
        }
        return count;
    }

    public Block[] getBlocks() { return blocks; }
    public Block getBlock(int index) { return blocks[index]; }
    public int getHeadPosition() { return headPosition; }
    public void setHeadPosition(int headPosition) { this.headPosition = headPosition; }
    public int getTotalCapacity() { return totalCapacity; }

}

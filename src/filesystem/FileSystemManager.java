/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filesystem;

import java.awt.Color;
import edd.TreeNode;

/**
 * Conecta el árbol lógico (carpetas) con el disco físico (SimulatedDisk).
 */


public class FileSystemManager {
      private TreeNode<FileMetadata> root; // La raíz ("C:\")
    private SimulatedDisk disk;

    public FileSystemManager(SimulatedDisk disk) {
        this.disk = disk;
        
        // Se crea el directorio raíz por defecto
        FileMetadata rootMeta = new FileMetadata("Raiz", true, 0, "Administrador", Color.BLACK);
        this.root = new TreeNode<>(rootMeta);
    }

    /**
     * Crea un archivo: le asigna espacio en disco y lo añade al árbol.
     */
    public boolean createFile(String name, int size, String owner, Color color, TreeNode<FileMetadata> parentFolder) {
        if (disk.getFreeSpace() < size) return false;

        // Pedir espacio al disco
        int firstBlockId = disk.allocateBlocks(size, color);
        if (firstBlockId == -1) return false;

        // Crear metadata y guardar el inicio de la cadena
        FileMetadata newFileMeta = new FileMetadata(name, false, size, owner, color);
        newFileMeta.setFirstBlockId(firstBlockId);

        // Añadir al árbol
        TreeNode<FileMetadata> newNode = new TreeNode<>(newFileMeta);
        parentFolder.addChild(newNode);

        return true;
    }

    /**
     * Crea un directorio (no ocupa espacio en el SD, solo en el árbol).
     */
    public void createDirectory(String name, String owner, TreeNode<FileMetadata> parentFolder) {
        FileMetadata newDirMeta = new FileMetadata(name, true, 0, owner, Color.DARK_GRAY);
        TreeNode<FileMetadata> newDirNode = new TreeNode<>(newDirMeta);
        parentFolder.addChild(newDirNode);
    }

    /**
     * Borra un archivo: libera el disco y lo saca del árbol.
     */
    public boolean deleteFile(TreeNode<FileMetadata> fileNode) {
        if (fileNode.getData().isDirectory()) return false; // Carpetas tienen otra lógica

        int firstBlock = fileNode.getData().getFirstBlockId();
        if (firstBlock != -1) {
            disk.freeBlocks(firstBlock);
        }

        TreeNode<FileMetadata> parent = fileNode.getParent();
        if (parent != null) {
            parent.getChildren().remove(fileNode); // Usamos el remove de nuestra CustomLinkedList
        }
        return true;
    }

    public TreeNode<FileMetadata> getRoot() { return root; }
    public SimulatedDisk getDisk() { return disk; }
}



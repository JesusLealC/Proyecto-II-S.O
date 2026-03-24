/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filesystem;

import edd.CustomLinkedList;
import edd.TreeNode;
import java.awt.Color;

public class FileSystemManager {
    private TreeNode<FileMetadata> root;
    private SimulatedDisk disk;

    public FileSystemManager(SimulatedDisk disk) {
        this.disk = disk;
        
        FileMetadata rootMeta = new FileMetadata("/", true, 0, "Administrador", Color.BLACK);
        this.root = new TreeNode<>(rootMeta);
    }

    public boolean createFile(String name, int size, String owner, Color color, TreeNode<FileMetadata> parentFolder) {
        if (disk.getFreeSpace() < size) return false;

        int firstBlockId = disk.allocateBlocks(size, color);
        if (firstBlockId == -1) return false;

        FileMetadata newFileMeta = new FileMetadata(name, false, size, owner, color);
        newFileMeta.setFirstBlockId(firstBlockId);

        TreeNode<FileMetadata> newNode = new TreeNode<>(newFileMeta);
        parentFolder.addChild(newNode);

        return true;
    }

    public void createDirectory(String name, String owner, TreeNode<FileMetadata> parentFolder) {
        FileMetadata newDirMeta = new FileMetadata(name, true, 0, owner, Color.DARK_GRAY);
        TreeNode<FileMetadata> newDirNode = new TreeNode<>(newDirMeta);
        parentFolder.addChild(newDirNode);
    }

    // Método añadido para cumplir con la operación "Actualizar" (Renombrar) solicitada en el proyecto
    public boolean renameNode(TreeNode<FileMetadata> node, String newName) {
        if (node == null || node == root) return false;
        
        node.getData().setName(newName);
        return true;
    }

    // Método modificado para soportar la eliminación recursiva de directorios y la liberación de bloques
    public boolean deleteNode(TreeNode<FileMetadata> node) {
        if (node == null || node == root) return false;

        if (node.getData().isDirectory()) {
            CustomLinkedList<TreeNode<FileMetadata>> children = node.getChildren();
            
            while (!children.isEmpty()) {
                TreeNode<FileMetadata> child = children.get(0);
                deleteNode(child); 
            }
        } else {
            int firstBlock = node.getData().getFirstBlockId();
            if (firstBlock != -1) {
                disk.freeBlocks(firstBlock);
            }
        }

        TreeNode<FileMetadata> parent = node.getParent();
        if (parent != null) {
            parent.getChildren().remove(node);
        }

        return true;
    }

    public TreeNode<FileMetadata> getRoot() { return root; }
    public SimulatedDisk getDisk() { return disk; }
}

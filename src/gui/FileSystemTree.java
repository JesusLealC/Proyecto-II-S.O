/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;
import edd.TreeNode;
import filesystem.FileMetadata;
import filesystem.FileSystemManager;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

public class FileSystemTree extends JTree {
    private final FileSystemManager fileSystemManager;

    public FileSystemTree(FileSystemManager fileSystemManager) {
        this.fileSystemManager = fileSystemManager;
        
        // Aplicar la paleta de colores del tema oscuro
        setBackground(new Color(34, 40, 49));
        setCellRenderer(new CustomTreeCellRenderer());
        
        refresh();
    }

    public void refresh() {
        if (fileSystemManager == null || fileSystemManager.getRoot() == null) return;

        DefaultMutableTreeNode rootNode = buildNode(fileSystemManager.getRoot());
        setModel(new DefaultTreeModel(rootNode));
        
        // Expandir el árbol automáticamente para mejor visualización
        for (int i = 0; i < getRowCount(); i++) {
            expandRow(i);
        }
    }

    private DefaultMutableTreeNode buildNode(TreeNode<FileMetadata> sourceNode) {
        // En lugar de guardar solo el String del nombre, guardamos el objeto completo
        FileMetadata metadata = sourceNode.getData();
        DefaultMutableTreeNode swingNode = new DefaultMutableTreeNode(metadata);

        for (int i = 0; i < sourceNode.getChildren().getSize(); i++) {
            TreeNode<FileMetadata> child = sourceNode.getChildren().get(i);
            if (child != null) {
                swingNode.add(buildNode(child));
            }
        }

        return swingNode;
    }

    /**
     * Renderizador personalizado para dar formato al texto y colores a los nodos
     * cumpliendo con el requerimiento de mostrar nombre, tamaño y dueño.
     */
    private class CustomTreeCellRenderer extends DefaultTreeCellRenderer {
        private final Color textDarkTheme = Color.WHITE;
        private final Color bgDarkTheme = new Color(34, 40, 49);
        private final Color selectionBg = new Color(57, 62, 70);

        public CustomTreeCellRenderer() {
            setBackgroundNonSelectionColor(bgDarkTheme);
            setBackgroundSelectionColor(selectionBg);
            setTextNonSelectionColor(textDarkTheme);
            setTextSelectionColor(Color.CYAN);
        }

        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value,
                boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            
            super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            Object userObject = node.getUserObject();

            if (userObject instanceof FileMetadata) {
                FileMetadata meta = (FileMetadata) userObject;
                
                // Formatear el texto a mostrar en el árbol
                if (meta.isDirectory()) {
                    setText(meta.getName() + " [Dueño: " + meta.getOwner() + "]");
                } else {
                    setText(meta.getName() + " : " + meta.getSizeInBlocks() + " bloques [Dueño: " + meta.getOwner() + "]");
                    
                }
            }

            return this;
        }
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author jleal
 */

package gui;

import edd.TreeNode;
import filesystem.FileMetadata;
import filesystem.FileSystemManager;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class FileSystemTree extends JTree {
    private final FileSystemManager fileSystemManager;

    public FileSystemTree(FileSystemManager fileSystemManager) {
        this.fileSystemManager = fileSystemManager;
        refresh();
    }

    public void refresh() {
        DefaultMutableTreeNode rootNode = buildNode(fileSystemManager.getRoot());
        setModel(new DefaultTreeModel(rootNode));
        expandRow(0);
    }

    private DefaultMutableTreeNode buildNode(TreeNode<FileMetadata> sourceNode) {
        FileMetadata metadata = sourceNode.getData();
        DefaultMutableTreeNode swingNode = new DefaultMutableTreeNode(metadata.getName());

        for (int i = 0; i < sourceNode.getChildren().getSize(); i++) {
            TreeNode<FileMetadata> child = sourceNode.getChildren().get(i);
            if (child != null) {
                swingNode.add(buildNode(child));
            }
        }

        return swingNode;
    }
}
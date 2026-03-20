/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edd;


public class TreeNode <T> {
    private T data; // Aquí guardarás la metadata del archivo/carpeta
    private TreeNode<T> parent;
    private CustomLinkedList<TreeNode<T>> children; // Usamos nuestra propia lista

    public TreeNode(T data) {
        this.data = data;
        this.parent = null;
        this.children = new CustomLinkedList<>();
    }

    public void addChild(TreeNode<T> childNode) {
        childNode.parent = this;
        this.children.addLast(childNode);
    }

    public CustomLinkedList<TreeNode<T>> getChildren() {
        return children;
    }

    public T getData() {
        return data;
    }
    
    public TreeNode<T> getParent() {
        return parent;
    }
 
}

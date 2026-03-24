/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package simulator;

import edd.TreeNode;
import filesystem.FileMetadata;
import filesystem.FileSystemManager;
import filesystem.SimulatedDisk;
import gui.MainWindow;
import java.awt.Color;
import javax.swing.SwingUtilities;
import process.PCB;
import process.Scheduler;
import util.Config;

public class Simulator {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SimulatedDisk disk = new SimulatedDisk(Config.DISK_SIZE);
            FileSystemManager fileSystemManager = new FileSystemManager(disk);
            Scheduler scheduler = new Scheduler();

            TreeNode<FileMetadata> root = fileSystemManager.getRoot();
            fileSystemManager.createDirectory("Documentos", "Administrador", root);
            fileSystemManager.createFile("tarea.txt", 4, "Usuario", Color.CYAN, root);
            fileSystemManager.createFile("kernel.sys", 6, "Administrador", Color.ORANGE, root);

            scheduler.agregarProceso(new PCB(1, 5));
            scheduler.agregarProceso(new PCB(2, 2));
            scheduler.agregarProceso(new PCB(3, 8));
            scheduler.despacharSiguiente();

            MainWindow window = new MainWindow(fileSystemManager, scheduler);
            window.setVisible(true);
            window.refreshAll();
        });
    }
}
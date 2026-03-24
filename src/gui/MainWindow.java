/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author jleal
 */
package gui;

import filesystem.FileSystemManager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import process.Scheduler;
import util.OSLogger;

public class MainWindow extends JFrame {
    private final FileSystemManager fileSystemManager;
    private final Scheduler scheduler;
    private final DiskVisualizer diskVisualizer;
    private final FileSystemTree fileSystemTree;
    private final ProcessTable processTable;
    private final JTextArea logArea;

    public MainWindow(FileSystemManager fileSystemManager, Scheduler scheduler) {
        this.fileSystemManager = fileSystemManager;
        this.scheduler = scheduler;
        this.diskVisualizer = new DiskVisualizer(fileSystemManager.getDisk());
        this.fileSystemTree = new FileSystemTree(fileSystemManager);
        this.processTable = new ProcessTable(scheduler);
        this.logArea = new JTextArea();

        initialize();
    }

    private void initialize() {
        setTitle("Proyecto II - Simulador de Sistema Operativo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);

        logArea.setEditable(false);
        OSLogger.setUiTarget(logArea);

        JPanel leftPanel = new JPanel(new GridLayout(2, 1, 8, 8));
        leftPanel.add(new JScrollPane(fileSystemTree));
        leftPanel.add(processTable);
        leftPanel.setPreferredSize(new Dimension(320, 600));

        JScrollPane diskScroll = new JScrollPane(diskVisualizer);
        JScrollPane logScroll = new JScrollPane(logArea);
        logScroll.setPreferredSize(new Dimension(600, 180));

        JSplitPane rightSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, diskScroll, logScroll);
        rightSplit.setResizeWeight(0.75);

        JSplitPane mainSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightSplit);
        mainSplit.setResizeWeight(0.28);

        add(mainSplit, BorderLayout.CENTER);
    }

    public void refreshAll() {
        fileSystemTree.refresh();
        processTable.refresh();
        diskVisualizer.repaint();
    }
}
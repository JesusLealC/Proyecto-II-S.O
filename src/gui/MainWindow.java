/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */



package gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.table.DefaultTableModel;
import simulator.Simulator;
import util.OSLogger;

public class MainWindow extends JFrame {
    private final Simulator simulator;
    private final DiskVisualizer diskVisualizer;
    private final FileSystemTree fileSystemTree;
    private final ProcessTable processTable;
    private final JTextArea logArea;
    private final JTextArea journalArea;
    private final DefaultTableModel allocationTableModel;
    private final JTable allocationTable;
    private final JLabel lblCiclo;

    // Colores del tema oscuro
    private final Color bgColor = new Color(34, 40, 49);
    private final Color panelColor = new Color(42, 48, 60);
    private final Color textColor = Color.WHITE;

    public MainWindow(Simulator simulator) {
        this.simulator = simulator;
        this.diskVisualizer = new DiskVisualizer(simulator.getFsManager().getDisk());
        this.fileSystemTree = new FileSystemTree(simulator.getFsManager());
        this.processTable = new ProcessTable(simulator.getProcessScheduler());
        
        this.logArea = new JTextArea();
        this.journalArea = new JTextArea();
        
        this.allocationTableModel = new DefaultTableModel(new Object[]{"Nombre", "Bloques", "Dir. Primer Bloque"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        this.allocationTable = new JTable(allocationTableModel);
        this.lblCiclo = new JLabel("Ciclo: 0");

        initialize();
    }

    private void initialize() {
        setTitle("Simulador Virtual de Sistema de Archivos Concurrente - SO 2526-2");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 768);
        setLocationRelativeTo(null);
        getContentPane().setBackground(bgColor);

        // --- PANEL SUPERIOR (Controles) ---
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(bgColor);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel leftControls = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        leftControls.setOpaque(false);

        JToggleButton btnMode = new JToggleButton("Administrador");
        JComboBox<String> comboScheduler = new JComboBox<>(new String[]{"FIFO", "SSTF", "SCAN", "C-SCAN"});
        
        JButton btnCrearArch = new JButton("Crear Archivo");
        JButton btnCrearDir = new JButton("Crear Directorio");
        JButton btnLeer = new JButton("Leer");
        JButton btnRenombrar = new JButton("Renombrar");
        JButton btnEliminar = new JButton("Eliminar");

        leftControls.add(btnMode);
        leftControls.add(comboScheduler);
        leftControls.add(btnCrearArch);
        leftControls.add(btnCrearDir);
        leftControls.add(btnLeer);
        leftControls.add(btnRenombrar);
        leftControls.add(btnEliminar);

        JPanel rightControls = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightControls.setOpaque(false);
        lblCiclo.setForeground(textColor);
        lblCiclo.setFont(new Font("Consolas", Font.BOLD, 14));
        rightControls.add(lblCiclo);

        topPanel.add(leftControls, BorderLayout.CENTER);
        topPanel.add(rightControls, BorderLayout.EAST);

        // --- PANEL CENTRAL (Árbol, Disco/FAT, Journal) ---
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);

        // 1. Árbol de Directorios (Izquierda)
        JScrollPane treeScroll = new JScrollPane(fileSystemTree);
        treeScroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(panelColor), "Sistema de Archivos"));
        treeScroll.setPreferredSize(new Dimension(250, 0));

        // 2. Visualización de Disco y Tabla FAT (Centro)
        JTabbedPane diskTabs = new JTabbedPane();
        diskTabs.addTab("Simulación de Disco", new JScrollPane(diskVisualizer));
        diskTabs.addTab("Tabla de Asignación", new JScrollPane(allocationTable));
        
        // 3. Journaling (Derecha)
        JPanel journalPanel = new JPanel(new BorderLayout());
        journalPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(panelColor), "Journal"));
        journalPanel.setPreferredSize(new Dimension(280, 0));
        
        journalArea.setEditable(false);
        journalArea.setBackground(panelColor);
        journalArea.setForeground(textColor);
        journalArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        
        JButton btnSimularFallo = new JButton("Simular Fallo");
        btnSimularFallo.setBackground(new Color(180, 50, 50));
        btnSimularFallo.setForeground(Color.WHITE);
        
        journalPanel.add(new JScrollPane(journalArea), BorderLayout.CENTER);
        journalPanel.add(btnSimularFallo, BorderLayout.SOUTH);

        JSplitPane splitLeftCenter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treeScroll, diskTabs);
        splitLeftCenter.setDividerSize(3);
        
        JSplitPane splitMainMiddle = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitLeftCenter, journalPanel);
        splitMainMiddle.setResizeWeight(0.85);
        splitMainMiddle.setDividerSize(3);

        centerPanel.add(splitMainMiddle, BorderLayout.CENTER);

        // --- PANEL INFERIOR (Log de Eventos y Cola de Procesos) ---
        logArea.setEditable(false);
        logArea.setBackground(panelColor);
        logArea.setForeground(new Color(150, 200, 150));
        logArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        OSLogger.setUiTarget(logArea);

        JScrollPane logScroll = new JScrollPane(logArea);
        logScroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(panelColor), "Log de Eventos"));

        processTable.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(panelColor), "Cola de Procesos"));
        processTable.setPreferredSize(new Dimension(350, 0));

        JSplitPane bottomSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, logScroll, processTable);
        bottomSplit.setResizeWeight(0.7);
        bottomSplit.setDividerSize(3);
        bottomSplit.setPreferredSize(new Dimension(0, 200));

        // --- ENSAMBLAJE FINAL ---
        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        
        JSplitPane verticalMainSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, centerPanel, bottomSplit);
        verticalMainSplit.setResizeWeight(0.7);
        verticalMainSplit.setDividerSize(5);
        
        add(verticalMainSplit, BorderLayout.CENTER);
    }

    public void refreshAll() {
        fileSystemTree.refresh();
        processTable.refresh();
        diskVisualizer.repaint();
        lblCiclo.setText("Ciclo: " + simulator.getCycleClock());
        // Aquí se actualizaría la tabla FAT leyendo el FileSystemManager
    }
}
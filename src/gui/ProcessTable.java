/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package gui;
import edd.CustomLinkedList;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import process.PCB;
import process.Scheduler;

public class ProcessTable extends JPanel {
    private final Scheduler scheduler;
    private final DefaultTableModel model;
    private final JTable table;

    public ProcessTable(Scheduler scheduler) {
        this.scheduler = scheduler;
        this.model = new DefaultTableModel(new Object[]{"PID", "Operación", "Archivo", "Estado"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.table = new JTable(model);

        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public void refresh() {
        model.setRowCount(0);
        
        PCB running = scheduler.getRunningProcess();
        if (running != null) {
            addProcess(running);
        }

        addProcesses(scheduler.getReadyProcessesSnapshot());
        addProcesses(scheduler.getBlockedProcessesSnapshot());
    }

    private void addProcesses(CustomLinkedList<PCB> processes) {
        for (int i = 0; i < processes.getSize(); i++) {
            addProcess(processes.get(i)); 
        }
    }

    private void addProcess(PCB process) {
        model.addRow(new Object[]{
            process.getProcessId(),
            process.getOperation(),
            process.getFileName(),
            process.getState()
        });
    }
}
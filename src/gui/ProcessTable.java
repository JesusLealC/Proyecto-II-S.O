/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author jleal
 */
package gui;

import java.awt.BorderLayout;
import java.util.List;
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
        this.model = new DefaultTableModel(new Object[]{"PID", "Estado", "Prioridad", "PC"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.table = new JTable(model);

        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);
        refresh();
    }

    public void refresh() {
        model.setRowCount(0);

        addProcesses(scheduler.getReadyProcessesSnapshot());
        addProcesses(scheduler.getBlockedProcessesSnapshot());

        PCB running = scheduler.getRunningProcess();
        if (running != null) {
            addProcess(running);
        }
    }

    private void addProcesses(List<PCB> processes) {
        for (PCB process : processes) {
            addProcess(process);
        }
    }

    private void addProcess(PCB process) {
        model.addRow(new Object[]{
            process.getProcessId(),
            process.getState(),
            process.getPriority(),
            process.getProgramCounter()
        });
    }
}

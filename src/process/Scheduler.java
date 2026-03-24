/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package process;

import edd.CustomQueue;
import edd.CustomLinkedList;
import util.OSLogger;
import scheduler.DiskScheduler;

public class Scheduler {
    private final CustomQueue<PCB> colaListos;
    private final CustomQueue<PCB> colaBloqueados;
    private PCB runningProcess;
    private DiskScheduler diskScheduler;

    public Scheduler(DiskScheduler diskScheduler) {
        this.colaListos = new CustomQueue<>();
        this.colaBloqueados = new CustomQueue<>();
        this.runningProcess = null;
        this.diskScheduler = diskScheduler;
    }

    public void agregarProceso(PCB proceso) {
        proceso.setState(ProcessState.READY);
        colaListos.enqueue(proceso);
        OSLogger.log("ProcessScheduler", "PID " + proceso.getProcessId() + " -> READY");
    }

    public void despacharSiguiente() {
        if (runningProcess == null && !colaListos.isEmpty()) {
            runningProcess = colaListos.dequeue();
            runningProcess.setState(ProcessState.RUNNING);
            OSLogger.log("ProcessScheduler", "PID " + runningProcess.getProcessId() + " -> RUNNING");
            
            solicitarIO(runningProcess);
        }
    }

    private void solicitarIO(PCB proceso) {
        proceso.setState(ProcessState.BLOCKED);
        colaBloqueados.enqueue(proceso);
        
        if (diskScheduler != null) {
            diskScheduler.encolarSolicitud(proceso);
        }
        
        OSLogger.log("ProcessScheduler", "PID " + proceso.getProcessId() + " -> BLOCKED (E/S Disco)");
        runningProcess = null;
    }

    public void terminarProceso(PCB proceso) {
        if (proceso == null) return;

        proceso.setState(ProcessState.TERMINATED);
        OSLogger.log("ProcessScheduler", "PID " + proceso.getProcessId() + " -> TERMINATED");
    }

    public CustomLinkedList<PCB> getReadyProcessesSnapshot() {
        return snapshotQueue(colaListos);
    }

    public CustomLinkedList<PCB> getBlockedProcessesSnapshot() {
        return snapshotQueue(colaBloqueados);
    }

    public PCB getRunningProcess() {
        return runningProcess;
    }

    private CustomLinkedList<PCB> snapshotQueue(CustomQueue<PCB> queue) {
        CustomLinkedList<PCB> snapshot = new CustomLinkedList<>();
        int size = queue.size();

        for (int i = 0; i < size; i++) {
            PCB process = queue.dequeue();
            snapshot.addLast(process);
            queue.enqueue(process);
        }
        return snapshot;
    }
}
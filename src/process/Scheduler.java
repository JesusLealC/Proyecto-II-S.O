/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package process;

import edd.CustomQueue;
import java.util.ArrayList;
import java.util.List;
import util.OSLogger;

public class Scheduler {
    private final CustomQueue<PCB> colaListos;
    private final CustomQueue<PCB> colaBloqueados;
    private PCB runningProcess;

    public Scheduler() {
        this.colaListos = new CustomQueue<>();
        this.colaBloqueados = new CustomQueue<>();
        this.runningProcess = null;
    }

    public void agregarProceso(PCB proceso) {
        proceso.setState(ProcessState.READY);
        colaListos.enqueue(proceso);
        OSLogger.log("Scheduler", "Proceso PID " + proceso.getProcessId() + " agregado a la cola de listos.");
    }

    public PCB despacharSiguiente() {
        if (colaListos.isEmpty()) {
            OSLogger.log("Scheduler", "No hay procesos listos para ejecutar.");
            runningProcess = null;
            return null;
        }

        PCB procesoAEjecutar = colaListos.dequeue();
        procesoAEjecutar.setState(ProcessState.RUNNING);
        runningProcess = procesoAEjecutar;
        OSLogger.log("Scheduler", "Despachando a CPU el proceso PID " + procesoAEjecutar.getProcessId());
        return procesoAEjecutar;
    }

    public void bloquearProceso(PCB proceso) {
        if (proceso == null) {
            return;
        }

        proceso.setState(ProcessState.BLOCKED);
        colaBloqueados.enqueue(proceso);

        if (runningProcess == proceso) {
            runningProcess = null;
        }

        OSLogger.log("Scheduler", "Proceso PID " + proceso.getProcessId() + " bloqueado.");
    }

    public void terminarProceso(PCB proceso) {
        if (proceso == null) {
            return;
        }

        proceso.setState(ProcessState.TERMINATED);

        if (runningProcess == proceso) {
            runningProcess = null;
        }

        OSLogger.log("Scheduler", "Proceso PID " + proceso.getProcessId() + " terminó su ejecución.");
    }

    public PCB desbloquearSiguiente() {
        if (colaBloqueados.isEmpty()) {
            return null;
        }

        PCB proceso = colaBloqueados.dequeue();
        agregarProceso(proceso);
        OSLogger.log("Scheduler", "Proceso PID " + proceso.getProcessId() + " movido de bloqueados a listos.");
        return proceso;
    }

    public List<PCB> getReadyProcessesSnapshot() {
        return snapshotQueue(colaListos);
    }

    public List<PCB> getBlockedProcessesSnapshot() {
        return snapshotQueue(colaBloqueados);
    }

    public PCB getRunningProcess() {
        return runningProcess;
    }

    private List<PCB> snapshotQueue(CustomQueue<PCB> queue) {
        List<PCB> snapshot = new ArrayList<>();
        int size = queue.size();

        for (int i = 0; i < size; i++) {
            PCB process = queue.dequeue();
            snapshot.add(process);
            queue.enqueue(process);
        }

        return snapshot;
    }
}
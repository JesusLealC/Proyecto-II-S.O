/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package process;

import edd.CustomQueue;

public class Scheduler {
    // Usamos tu CustomQueue y le decimos que va a guardar objetos tipo PCB
    private CustomQueue<PCB> colaListos;     
    private CustomQueue<PCB> colaBloqueados; 
    
    public Scheduler() {
        this.colaListos = new CustomQueue<>();
        this.colaBloqueados = new CustomQueue<>();
    }
    
    // 1. Meter un proceso nuevo a la fila de Listos
    public void agregarProceso(PCB proceso) {
        proceso.setState(ProcessState.READY); // Cambia a LISTO
        colaListos.enqueue(proceso);          // Usamos TU método enqueue
        System.out.println("Proceso PID " + proceso.getProcessId() + " agregado a la Cola de Listos.");
    }
    
    // 2. Sacar al siguiente proceso y mandarlo a ejecutar
    public PCB despacharSiguiente() {
        if (!colaListos.isEmpty()) { // Usamos TU método isEmpty
            // Sacamos el proceso (tu CustomQueue ya sabe que devuelve un PCB)
            PCB procesoA_Ejecutar = colaListos.dequeue(); // Usamos TU método dequeue
            
            procesoA_Ejecutar.setState(ProcessState.RUNNING);
            System.out.println("Despachando a CPU: Proceso PID " + procesoA_Ejecutar.getProcessId());
            
            return procesoA_Ejecutar;
        } else {
            System.out.println("No hay procesos listos para ejecutar.");
            return null;
        }
    }
    
    // 3. Bloquear un proceso (pasa de RUNNING a BLOCKED)
    public void bloquearProceso(PCB proceso) {
        proceso.setState(ProcessState.BLOCKED);
        colaBloqueados.enqueue(proceso);
        System.out.println("Proceso PID " + proceso.getProcessId() + " ha sido BLOQUEADO.");
    }
    
    // 4. Terminar un proceso
    public void terminarProceso(PCB proceso) {
        proceso.setState(ProcessState.TERMINATED);
        System.out.println("Proceso PID " + proceso.getProcessId() + " ha TERMINADO su ejecución.");
    }

}

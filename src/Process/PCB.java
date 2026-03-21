/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Process;


public class PCB {
    private int processId;
    private ProcessState state;
    private int programCounter;
    private int priority;
    private int[] registers;

    // Constructor
    public PCB(int processId, int priority) {
        this.processId = processId;
        this.state = ProcessState.NEW;
        this.programCounter = 0;
        this.priority = priority;
        this.registers = new int[4];
    }

    // --- GETTERS Y SETTERS que usa el Scheduler ---
    public int getProcessId() {
        return processId;
    }

    public ProcessState getState() {
        return state;
    }

    public void setState(ProcessState state) {
        this.state = state;
    }

    public int getProgramCounter() {
        return programCounter;
    }

    public void setProgramCounter(int programCounter) {
        this.programCounter = programCounter;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "PCB [PID=" + processId + ", Estado=" + state + "]";
    }
}

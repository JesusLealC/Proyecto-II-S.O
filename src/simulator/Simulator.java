/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package simulator;
import filesystem.FileSystemManager;
import filesystem.SimulatedDisk;
import journaling.JournalManager;
import process.PCB;
import process.Scheduler;
import scheduler.DiskScheduler;
import scheduler.FIFOScheduler;
import gui.MainWindow;
import javax.swing.SwingUtilities;

public class Simulator {
    private SimulatedDisk disk;
    private FileSystemManager fsManager;
    private Scheduler processScheduler;
    private DiskScheduler currentDiskScheduler;
    private JournalManager journalManager;
    private int cycleClock;

    public Simulator(int diskCapacity, DiskScheduler initialScheduler) {
        this.disk = new SimulatedDisk(diskCapacity);
        this.fsManager = new FileSystemManager(disk);
        this.currentDiskScheduler = initialScheduler;
        this.processScheduler = new Scheduler(currentDiskScheduler);
        this.journalManager = new JournalManager();
        this.cycleClock = 0;
    }

    public void setDiskScheduler(DiskScheduler newScheduler) {
        this.currentDiskScheduler = newScheduler;
    }

    public void processNextTick() {
        if (journalManager.isSystemCrashed()) {
            return; 
        }

        cycleClock++;
        
        processScheduler.despacharSiguiente();

        PCB ioRequest = currentDiskScheduler.procesarSiguiente(disk.getHeadPosition());
        
        if (ioRequest != null) {
            ejecutarOperacionIO(ioRequest);
            processScheduler.terminarProceso(ioRequest); 
        }
    }

    private void ejecutarOperacionIO(PCB proceso) {
        String op = proceso.getOperation();
        
        if (op.equals("CREATE")) {
            journalManager.logOperation("CREATE", proceso.getFileName(), -1);
        } else if (op.equals("DELETE")) {
            journalManager.logOperation("DELETE", proceso.getFileName(), -1);
        }
    }

    public FileSystemManager getFsManager() { return fsManager; }
    public Scheduler getProcessScheduler() { return processScheduler; }
    public JournalManager getJournalManager() { return journalManager; }
    public int getCycleClock() { return cycleClock; }

    /**
     * MÉTODO MAIN: Punto de entrada para NetBeans
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Inicialización según requerimientos: 100 bloques de SD [cite: 10, 31]
            Simulator sim = new Simulator(100, new FIFOScheduler());
            
            // Iniciar Interfaz Gráfica (Requisito indispensable) [cite: 185]
            MainWindow gui = new MainWindow(sim);
            gui.setVisible(true);

            // Hilo de ejecución en tiempo real para el reloj del sistema [cite: 22, 186]
            new Thread(() -> {
                try {
                    while (true) {
                        Thread.sleep(500); // Velocidad de simulación (500ms)
                        sim.processNextTick();
                        gui.refreshAll(); // Actualización visual en tiempo real [cite: 24]
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        });
    }
}
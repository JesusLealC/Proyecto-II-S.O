/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author jleal
 */

package persistence;

import edd.CustomQueue;
import filesystem.SimulatedDisk;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import process.PCB;
import util.Config;
import util.OSLogger;

public class DataParser {

    public static class ParsedData {
        private int diskSize;
        private int quantum;
        private CustomQueue<PCB> processes;
        private SimulatedDisk disk;

        public ParsedData(int diskSize, int quantum, CustomQueue<PCB> processes) {
            this.diskSize = diskSize;
            this.quantum = quantum;
            this.processes = processes;
            this.disk = new SimulatedDisk(diskSize);
        }

        public int getDiskSize() {
            return diskSize;
        }

        public int getQuantum() {
            return quantum;
        }

        public CustomQueue<PCB> getProcesses() {
            return processes;
        }

        public SimulatedDisk getDisk() {
            return disk;
        }
    }

    public ParsedData parse(String jsonText) {
        int diskSize = extractInt(jsonText, "diskSize", Config.DISK_SIZE);
        int quantum = extractInt(jsonText, "quantum", Config.DEFAULT_QUANTUM);
        CustomQueue<PCB> processes = parseProcesses(jsonText);

        OSLogger.log("DataParser", "Datos parseados desde JSON. DiskSize=" + diskSize + ", Quantum=" + quantum);
        return new ParsedData(diskSize, quantum, processes);
    }

    private CustomQueue<PCB> parseProcesses(String jsonText) {
        CustomQueue<PCB> processes = new CustomQueue<>();
        Pattern objectPattern = Pattern.compile("\\{(.*?)\\}", Pattern.DOTALL);
        Matcher matcher = objectPattern.matcher(extractProcessesSection(jsonText));

        while (matcher.find()) {
            String processObject = matcher.group(1);
            int pid = extractInt(processObject, "pid", extractInt(processObject, "processId", -1));
            int priority = extractInt(processObject, "priority", 0);

            if (pid != -1) {
                processes.enqueue(new PCB(pid, priority));
            }
        }

        return processes;
    }

    private String extractProcessesSection(String jsonText) {
        Pattern pattern = Pattern.compile("\"processes\"\\s*:\\s*\\[(.*?)]", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(jsonText);

        if (matcher.find()) {
            return matcher.group(1);
        }

        return "";
    }

    private int extractInt(String source, String key, int defaultValue) {
        Pattern pattern = Pattern.compile("\"" + Pattern.quote(key) + "\"\\s*:\\s*(\\d+)");
        Matcher matcher = pattern.matcher(source);

        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }

        return defaultValue;
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author jleal
 */

package persistence;

import edd.CustomLinkedList;
import edd.CustomQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import process.PCB;
import util.OSLogger;

public class DataParser {

    // Clase auxiliar para guardar los metadatos de los archivos del JSON
    public static class SystemFile {
        public int startPos;
        public String name;
        public int blocks;

        public SystemFile(int startPos, String name, int blocks) {
            this.startPos = startPos;
            this.name = name;
            this.blocks = blocks;
        }
    }

    public static class ParsedData {
        private String testId;
        private int initialHead;
        private CustomLinkedList<SystemFile> systemFiles;
        private CustomQueue<PCB> processes;

        public ParsedData(String testId, int initialHead, CustomLinkedList<SystemFile> systemFiles, CustomQueue<PCB> processes) {
            this.testId = testId;
            this.initialHead = initialHead;
            this.systemFiles = systemFiles;
            this.processes = processes;
        }

        public String getTestId() { return testId; }
        public int getInitialHead() { return initialHead; }
        public CustomLinkedList<SystemFile> getSystemFiles() { return systemFiles; }
        public CustomQueue<PCB> getProcesses() { return processes; }
    }

    public ParsedData parse(String jsonText) {
        String testId = extractString(jsonText, "test_id", "DefaultTest");
        int initialHead = extractInt(jsonText, "initial_head", 0);

        CustomLinkedList<SystemFile> sysFiles = parseSystemFiles(jsonText);
        CustomQueue<PCB> processes = parseRequests(jsonText, sysFiles);

        OSLogger.log("DataParser", "JSON cargado. TestID: " + testId + ", InitialHead: " + initialHead);
        return new ParsedData(testId, initialHead, sysFiles, processes);
    }

    private CustomLinkedList<SystemFile> parseSystemFiles(String jsonText) {
        CustomLinkedList<SystemFile> files = new CustomLinkedList<>();
        
        // Expresión regular ajustada al formato del PDF ("11": { "name": "boot_sect.bin", "blocks": 2 }) [cite: 120-154]
        Pattern filePattern = Pattern.compile("\"(\\d+)\"\\s*:\\s*\\{\\s*\"name\"\\s*:\\s*\"([^\"]+)\"\\s*,\\s*\"blocks\"\\s*:\\s*(\\d+)");
        Matcher matcher = filePattern.matcher(jsonText);

        while (matcher.find()) {
            int pos = Integer.parseInt(matcher.group(1));
            String name = matcher.group(2);
            int blocks = Integer.parseInt(matcher.group(3));
            files.addLast(new SystemFile(pos, name, blocks));
        }
        
        return files;
    }

    private CustomQueue<PCB> parseRequests(String jsonText, CustomLinkedList<SystemFile> sysFiles) {
        CustomQueue<PCB> processes = new CustomQueue<>();
        String requestsSection = extractSection(jsonText, "\"requests\"\\s*:\\s*\\[(.*?)\\]");

        // Expresión regular para: {"pos": 11, "op": "READ"} [cite: 110-119]
        Pattern reqPattern = Pattern.compile("\\{\\s*\"pos\"\\s*:\\s*(\\d+)\\s*,\\s*\"op\"\\s*:\\s*\"([^\"]+)\"\\s*\\}");
        Matcher matcher = reqPattern.matcher(requestsSection);

        int pidCounter = 1;
        while (matcher.find()) {
            int pos = Integer.parseInt(matcher.group(1));
            String op = matcher.group(2);

            // Relacionar la posición de la solicitud con el archivo en system_files para armar el PCB
            String fileName = "Desconocido_Pos_" + pos;
            int blocks = 0;
            
            for (int i = 0; i < sysFiles.getSize(); i++) {
                SystemFile sf = sysFiles.get(i);
                if (sf.startPos == pos) {
                    fileName = sf.name;
                    blocks = sf.blocks;
                    break;
                }
            }

            // PCB(int processId, String operation, String fileName, int fileSizeInBlocks, String userMode)
            processes.enqueue(new PCB(pidCounter++, op, fileName, blocks, "ADMIN"));
        }
        
        return processes;
    }

    private String extractSection(String source, String regex) {
        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(source);
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

    private String extractString(String source, String key, String defaultValue) {
        Pattern pattern = Pattern.compile("\"" + Pattern.quote(key) + "\"\\s*:\\s*\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(source);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return defaultValue;
    }
}
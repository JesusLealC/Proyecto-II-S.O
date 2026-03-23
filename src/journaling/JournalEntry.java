/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author jleal
 */
package journaling;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JournalEntry {
    public static final String STATUS_PENDING = "PENDIENTE";
    public static final String STATUS_COMPLETED = "COMPLETADO";
    public static final String STATUS_FAILED = "FALLIDO";

    private String operationType;
    private String fileName;
    private String status;
    private String details;
    private final String timestamp;

    public JournalEntry(String operationType, String fileName, String status) {
        this(operationType, fileName, status, "");
    }

    public JournalEntry(String operationType, String fileName, String status, String details) {
        this.operationType = operationType;
        this.fileName = fileName;
        this.status = status;
        this.details = details;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

    public String getOperationType() {
        return operationType;
    }

    public String getFileName() {
        return fileName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "JournalEntry{" +
                "operationType='" + operationType + '\'' +
                ", fileName='" + fileName + '\'' +
                ", status='" + status + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
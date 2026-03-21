/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OSLogger {
// La "ventana de texto" de la GUI donde escribiremos
    private static JTextArea uiTarget;
    // Bandera para asegurar que solo mostramos en la GUI
    private static boolean guiEnabled = false;

    // --- ESTE MÉTODO ES EL MÁS IMPORTANTE PARA LA GUI ---
    //  paquete 'gui', al iniciar, debe llamar a este método
    // pasándole el JTextArea donde quiere que aparezcan los logs.
    public static void setUiTarget(JTextArea textArea) {
        uiTarget = textArea;
        guiEnabled = (textArea != null);
        
        if (guiEnabled) {
            // Un mensaje de bienvenida visual
            log("Sistema", "Registrador de eventos (Log) conectado a la interfaz gráfica.");
        }
    }

    // --- EL MÉTODO LOG NORMAL (Ahora redirigido) ---
    public static void log(String component, String message) {
        if (!guiEnabled || uiTarget == null) {
            // Si la GUI no está lista, simplemente no hacemos nada.
            // Cero System.out.println. Cumplimos la regla.
            return; 
        }

        // Preparamos el mensaje igual que antes
        int tick = GlobalClock.getInstance().getTime();
        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        String fullLog = String.format("[%s] [TICK: %d] [%s] -> %s\n", 
                         timeStamp, tick, component, message);

        // --- HILO SEGURO PARA SWING ---
        // Le pedimos a Java que ejecute esto en el hilo de la interfaz gráfica
        // para evitar errores y congelamientos.
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Escribimos el mensaje al final de la ventana de texto
                uiTarget.append(fullLog);
                
                // Hace auto-scroll para mostrar siempre la última línea
                uiTarget.setCaretPosition(uiTarget.getDocument().getLength());
            }
        });
    }

    public static void clear() {
        if (guiEnabled && uiTarget != null) {
            SwingUtilities.invokeLater(() -> uiTarget.setText(""));
        }
    }
}

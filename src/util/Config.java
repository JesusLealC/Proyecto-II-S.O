/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.awt.Color;

public class Config {
    // --- Configuración del Sistema de Archivos ---
    public static final int DISK_SIZE = 100;          // Cantidad de bloques
    public static final int CHARS_PER_BLOCK = 10;     // Cuántos caracteres caben por bloque
    public static final Color UNUSED_BLOCK_COLOR = Color.LIGHT_GRAY;

    // --- Configuración de Procesos ---
    public static final int DEFAULT_QUANTUM = 3;      // Para el Round Robin
    public static final int MAX_PROCESSES = 20;       // Límite del sistema

    // --- Configuración de Interfaz ---
    public static final int REFRESH_RATE_MS = 1000;   // Cada cuánto se actualiza la GUI (1 seg)
}


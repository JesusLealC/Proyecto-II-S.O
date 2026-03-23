/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author jleal
 */

package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import util.OSLogger;

public class JSONLoader {
    public String loadFromFile(String filePath) throws IOException {
        String json = Files.readString(Path.of(filePath), StandardCharsets.UTF_8);
        OSLogger.log("JSONLoader", "Archivo JSON cargado desde: " + filePath);
        return json;
    }
}
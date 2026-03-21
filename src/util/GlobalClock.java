/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;


public class GlobalClock {
    private int currentTime;
    private static GlobalClock instance;

    private GlobalClock() {
        this.currentTime = 0;
    }

    public static GlobalClock getInstance() {
        if (instance == null) {
            instance = new GlobalClock();
        }
        return instance;
    }

    public int getTime() { return currentTime; }
    
    public void tick() { 
        currentTime++; 
        // Aquí podríamos disparar eventos automáticos en el futuro
    }

    public void reset() { currentTime = 0; }
}

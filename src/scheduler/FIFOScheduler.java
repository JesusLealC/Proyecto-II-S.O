/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author jleal
 */
package scheduler;

import java.util.ArrayList;
import java.util.List;

public class FIFOScheduler implements DiskScheduler {
    @Override
    public List<Integer> calculateRoute(List<Integer> requestedBlocks, int currentPosition) {
        return new ArrayList<>(requestedBlocks);
    }

    @Override
    public String getAlgorithmName() {
        return "FIFO";
    }
}
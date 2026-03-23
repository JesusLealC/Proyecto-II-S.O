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

public class SSTFScheduler implements DiskScheduler {
    @Override
    public List<Integer> calculateRoute(List<Integer> requestedBlocks, int currentPosition) {
        List<Integer> pending = new ArrayList<>(requestedBlocks);
        List<Integer> route = new ArrayList<>();
        int head = currentPosition;

        while (!pending.isEmpty()) {
            int nearestIndex = 0;
            int nearestDistance = Math.abs(pending.get(0) - head);

            for (int i = 1; i < pending.size(); i++) {
                int distance = Math.abs(pending.get(i) - head);
                if (distance < nearestDistance) {
                    nearestDistance = distance;
                    nearestIndex = i;
                }
            }

            int nextBlock = pending.remove(nearestIndex);
            route.add(nextBlock);
            head = nextBlock;
        }

        return route;
    }

    @Override
    public String getAlgorithmName() {
        return "SSTF";
    }
}
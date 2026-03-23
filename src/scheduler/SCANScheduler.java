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
import java.util.Collections;
import java.util.List;

public class SCANScheduler implements DiskScheduler {
    private boolean moveRightFirst;

    public SCANScheduler() {
        this(true);
    }

    public SCANScheduler(boolean moveRightFirst) {
        this.moveRightFirst = moveRightFirst;
    }

    @Override
    public List<Integer> calculateRoute(List<Integer> requestedBlocks, int currentPosition) {
        List<Integer> sorted = new ArrayList<>(requestedBlocks);
        Collections.sort(sorted);

        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();

        for (Integer block : sorted) {
            if (block < currentPosition) {
                left.add(block);
            } else {
                right.add(block);
            }
        }

        List<Integer> route = new ArrayList<>();
        if (moveRightFirst) {
            route.addAll(right);
            Collections.reverse(left);
            route.addAll(left);
        } else {
            Collections.reverse(left);
            route.addAll(left);
            route.addAll(right);
        }

        return route;
    }

    public boolean isMoveRightFirst() {
        return moveRightFirst;
    }

    public void setMoveRightFirst(boolean moveRightFirst) {
        this.moveRightFirst = moveRightFirst;
    }

    @Override
    public String getAlgorithmName() {
        return "SCAN";
    }
}
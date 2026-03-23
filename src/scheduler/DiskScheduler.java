package scheduler;

import java.util.List;

public interface DiskScheduler {
    List<Integer> calculateRoute(List<Integer> requestedBlocks, int currentPosition);
    String getAlgorithmName();
}
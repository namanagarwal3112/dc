import java.util.concurrent.Semaphore;

public class SuzukiKasami {
    private static final int NUM_NODES = 5; // Number of nodes in the distributed system
    private static final int NUM_REQUESTS = 10; // Number of requests for each node
    private static int[] request = new int[NUM_NODES];
    private static int[] token = new int[NUM_NODES];
    private static int[][] quorum = {
            {1, 2, 4}, // Quorum for node 0
            {0, 2, 3}, // Quorum for node 1
            {0, 1, 3}, // Quorum for node 2
            {1, 2, 4}, // Quorum for node 3
            {0, 3, 4}  // Quorum for node 4
    };
    private static Semaphore mutex = new Semaphore(1);

    public static void main(String[] args) {
        for (int i = 0; i < NUM_NODES; i++) {
            final int nodeId = i;
            new Thread(() -> {
                for (int j = 0; j < NUM_REQUESTS; j++) {
                    requestCriticalSection(nodeId);
                    // Simulate critical section execution
                    System.out.println("Node " + nodeId + " is in critical section");
                    // Sleep for a random duration up to 100 milliseconds
                    try {
                        Thread.sleep((long) (Math.random() * 100));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    releaseCriticalSection(nodeId);
                }
            }).start();
        }
    }

    private static void requestCriticalSection(int nodeId) {
        try {
            mutex.acquire();
            request[nodeId] = 1;
            for (int i : quorum[nodeId]) {
                if (i != nodeId && request[i] == 1) {
                    mutex.release();
                    Thread.sleep(10); // Adjust as needed
                    mutex.acquire();
                }
            }
            token[nodeId] = 1;
            mutex.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void releaseCriticalSection(int nodeId) {
        try {
            mutex.acquire();
            token[nodeId] = 0;
            request[nodeId] = 0;
            mutex.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

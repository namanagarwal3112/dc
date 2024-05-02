import java.util.ArrayList;
public class RoundRobinLoadBalancer {
// Simulating servers with ArrayLists
private ArrayList<Integer>[] servers;
private int numServers;
public RoundRobinLoadBalancer(int numServers) {
this.numServers = numServers;
servers = new ArrayList[numServers]; 
for (int i = 0; i < numServers; i++) {
servers[i] = new ArrayList<>();
}
}
// Add processes to the servers
public void addProcesses(int[] processes) {
int currentIndex = 0;
for (int process : processes) {
servers[currentIndex].add(process);
currentIndex = (currentIndex + 1) % numServers; // Round robin distribution
}
}
// Print processes present in each server
public void printProcesses() {
for (int i = 0; i < numServers; i++) {
System.out.println("Server " + (i + 1) + " Processes: " + servers[i]);
}
}
public static void main(String[] args) {
// Initial processes in the servers
int[] initialProcesses = {1, 2, 3, 4, 5, 6, 7};
// Number of servers
int numServers = 4;
RoundRobinLoadBalancer loadBalancer = new
RoundRobinLoadBalancer(numServers);
System.out.println("Processes before balancing:");
for (int process : initialProcesses) {
System.out.print(process + " ");
}
System.out.println();
loadBalancer.addProcesses(initialProcesses);
System.out.println("\nProcesses after balancing:");
loadBalancer.printProcesses();
}
} 
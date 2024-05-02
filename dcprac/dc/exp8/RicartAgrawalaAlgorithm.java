import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Message {
    String messageType;
    int timestamp;
    int siteId;

    Message(String messageType, int timestamp, int siteId) {
        this.messageType = messageType;
        this.timestamp = timestamp;
        this.siteId = siteId;
    }
}

class Site {
    int siteId;
    boolean requesting;
    boolean executing;
    int timestamp;
    List<Message> deferredQueue;

    Site(int siteId) {
        this.siteId = siteId;
        this.requesting = false;
        this.executing = false;
        this.timestamp = 0;
        this.deferredQueue = new ArrayList<>();
    }

    void requestCriticalSection(List<Site> sites) {
        this.requesting = true;
        this.timestamp++;
        for (Site site : sites) {
            if (site.siteId != this.siteId) {
                Message requestMessage = new Message("REQUEST", this.timestamp, this.siteId);
                sendMessage(requestMessage, site);
            }
        }
        waitForReplies(sites);
    }

    void sendMessage(Message message, Site destination) {
        System.out.println("Site " + this.siteId + " sends " + message.messageType + " message to Site " + destination.siteId);
        destination.receiveMessage(message, this);
    }

    void receiveMessage(Message message, Site sender) {
        System.out.println("Site " + this.siteId + " receives " + message.messageType + " message from Site " + sender.siteId);
        if (message.messageType.equals("REQUEST")) {
            if (!this.requesting && !this.executing) {
                this.sendMessage(new Message("REPLY", 0, this.siteId), sender);
            } else if (this.requesting && message.timestamp < this.timestamp) {
                this.deferredQueue.add(message);
            }
        } else if (message.messageType.equals("REPLY")) {
            if (this.requesting) {
                this.deferredQueue.removeIf(m -> m.siteId == sender.siteId);
                if (this.deferredQueue.isEmpty()) {
                    this.executing = true;
                    System.out.println("Site " + this.siteId + " enters critical section.");
                }
            }
        }
    }

    void waitForReplies(List<Site> sites) {
        int repliesExpected = sites.size() - 1;
        int repliesReceived = 0;
        while (repliesReceived < repliesExpected) {
            // Wait for replies
        }
    }

    void releaseCriticalSection(List<Site> sites) {
        this.requesting = false;
        this.executing = false;
        for (Site site : sites) {
            if (site.siteId != this.siteId) {
                for (Message message : this.deferredQueue) {
                    this.sendMessage(new Message("REPLY", 0, this.siteId), site);
                }
            }
        }
        this.deferredQueue.clear();
        System.out.println("Site " + this.siteId + " releases critical section.");
    }
}

public class RicartAgrawalaAlgorithm {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of sites: ");
        int numberOfSites = scanner.nextInt();
        List<Site> sites = new ArrayList<>();
        for (int i = 0; i < numberOfSites; i++) {
            sites.add(new Site(i+1));
        }
        for (Site site : sites) {
            site.requestCriticalSection(sites);
            site.releaseCriticalSection(sites);
        }
    }
}

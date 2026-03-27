package assignments.commsystem;

public class CommSystem {
    private boolean online;

    public CommSystem() {
        online = true;
    }

    public void shutdown() {
        online = false;
    }

    public void restart() {
        online = true;
    }

    public boolean isOnline() {
        return online;
    }
}
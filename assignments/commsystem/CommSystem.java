package assignments.commsystem;

import java.util.ArrayList;

public class CommSystem {

    private String stationNer;
    private ArrayList<String> log;
    private int signalHvch;
    private boolean offline;

    public CommSystem(String stationNer) {
        this.stationNer = stationNer;
        this.log = new ArrayList<>();
        this.signalHvch = 100;
        this.offline = false;
    }

    public String ilgeeh(String hvleenAvagch, String mesg) {
        if (offline) {
            return "📡 Офлайн!";
        }
        signalHvch -= 5;
        if (signalHvch < 10) {
            offline = true;
        }
        log.add("→ " + hvleenAvagch + ": " + mesg);
        return "Илгээлээ: " + hvleenAvagch;
    }

    public void hvleenAvah(String ilgeegch, String mesg) {
        log.add("← " + ilgeegch + ": " + mesg);
    }

    public void signalSergemjuuleh() {
        signalHvch = 100;
        offline = false;
    }

    public String logHarah(int n) {
        StringBuilder sb = new StringBuilder();
        int start = Math.max(0, log.size() - n);
        for (int i = start; i < log.size(); i++) {
            sb.append(log.get(i));
            if (i != log.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        String status = offline ? "OFFLINE" : "ONLINE";
        return "📡 " + stationNer + " [" + status + "] Signal: " + signalHvch + "% | Лог: " + log.size() + " мессеж";
    }
}
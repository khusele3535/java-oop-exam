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

        // эхлээд log-д нэмнэ (шаардлагаар энэ мессеж орно)
        log.add("→ " + hvleenAvagch + ": " + mesg);

        // дараа нь offline болгох check
        if (signalHvch < 10) {
            offline = true;
        }

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

        int size = log.size();
        int start = (n > size) ? 0 : size - n;

        for (int i = start; i < size; i++) {
            sb.append(log.get(i));
            if (i < size - 1) {
                sb.append("\n");
            }
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        String status = offline ? "OFFLINE" : "ONLINE";

        return "📡 " + stationNer + " [" + status + "] Signal: "
                + signalHvch + "% | Лог: " + log.size() + " мессеж";
    }
}
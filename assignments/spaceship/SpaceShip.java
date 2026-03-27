package assignments.spaceship;

public class SpaceShip {

    private String ner;
    private int tulsh;
    private int zorchigch;
    private double km;

    public SpaceShip(String ner) {
        this.ner = ner;
        this.tulsh = 100;
        this.zorchigch = 0;
        this.km = 0.0;
    }

    public String zorchigchAvah(int too) {
        if (zorchigch + too > 10) {
            return "Багтаамж хэтэрлээ!";
        }
        zorchigch += too;
        return too + " зорчигч нэмэгдлээ";
    }

    public String nisleg(double zai) {
        double requiredTulsh = zai * 0.5;
        if (tulsh < requiredTulsh) {
            return "Түлш дутуу!";
        }
        tulsh -= requiredTulsh;
        km += zai;
        return "Нислэг амжилттай! " + zai + " км нисэв";
    }

    public void tulshTsenegleh(int hemjee) {
        tulsh = Math.min(100, tulsh + hemjee);
    }

    @Override
    public String toString() {
        return "🚀 " + ner + " | Түлш: " + tulsh + "% | Зорчигч: " + zorchigch + " | Нийт: " + km + " км";
    }
}
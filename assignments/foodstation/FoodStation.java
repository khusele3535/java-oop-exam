package assignments.foodstation;

import java.util.ArrayList;

public class FoodStation {

    private String ner;
    private ArrayList<String> menu;
    private ArrayList<Integer> uneNuud;
    private int niitOrlogo;

    public FoodStation(String ner) {
        this.ner = ner;
        this.menu = new ArrayList<>();
        this.uneNuud = new ArrayList<>();
        this.niitOrlogo = 0;
    }

    public void tsesNemeh(String hool, int une) {
        menu.add(hool);
        uneNuud.add(une);
    }

    public String zahialga(String hool) {
        int index = menu.indexOf(hool);
        if (index == -1) {
            return "❌ Цэсэнд байхгүй";
        }
        niitOrlogo += uneNuud.get(index);
        return "✅ " + hool + " бэлтгэж байна";
    }

    public String hamgiinHvnstei() {
        if (menu.isEmpty()) return "Цэс хоосон";
        int maxIndex = 0;
        for (int i = 1; i < uneNuud.size(); i++) {
            if (uneNuud.get(i) > uneNuud.get(maxIndex)) {
                maxIndex = i;
            }
        }
        return menu.get(maxIndex);
    }

    @Override
    public String toString() {
        return "🍜 " + ner + " | Цэс: " + menu.size() + " хоол | Орлого: " + niitOrlogo + "₮";
    }
}
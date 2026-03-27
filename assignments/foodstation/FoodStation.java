package assignments.foodstation;

public class FoodStation {
    private int food;

    public FoodStation(int initialFood) {
        this.food = initialFood;
    }

    public void addFood(int amount) {
        food += amount;
    }

    public boolean consumeFood(int amount) {
        if (food >= amount) {
            food -= amount;
            return true;
        }
        return false;
    }

    public int getFood() {
        return food;
    }
}
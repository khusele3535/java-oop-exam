package assignments.spaceship;

public class SpaceShip {
    private String name;

    public SpaceShip(String name) {
        this.name = name;
    }

    public void launch() {
        System.out.println("Launching spaceship " + name);
    }

    public void land() {
        System.out.println("Spaceship " + name + " has landed");
    }
}
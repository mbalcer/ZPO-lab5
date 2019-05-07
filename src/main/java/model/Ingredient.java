package model;

public class Ingredient {

    private String name;
    private double price;
    private boolean meat;

    public Ingredient() {
    }

    public Ingredient(String name, double price, boolean meat) {
        this.name = name;
        this.price = price;
        this.meat = meat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isMeat() {
        return meat;
    }

    public void setMeat(boolean meat) {
        this.meat = meat;
    }
}

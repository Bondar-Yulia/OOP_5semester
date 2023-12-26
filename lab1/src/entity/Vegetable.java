package entity;

// Основной абстрактный класс для овощей
public abstract class Vegetable {
    private String name;
    private double calories;  // Калорийность на 100 грамм продукта
    private double weight;    // Вес в граммах
    private String color;

    public Vegetable(String name, double calories, double weight, String color) {
        this.name = name;
        this.calories = calories;
        this.weight = weight;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public double getCalories() {
        return calories;
    }

    public double getWeight() {
        return weight;
    }

    public String getColor() {
        return color;
    }

    // Рассчитывает общую калорийность овоща на основе его веса
    public double getTotalCalories() {
        return (calories * weight) / 100;
    }
}

// Конкретные классы овощей
class Carrot extends Vegetable {
    public Carrot(double weight) {
        super("Carrot", 41, weight, "Orange");
    }
}

class Tomato extends Vegetable {
    public Tomato(double weight) {
        super("Tomato", 18, weight, "Red");
    }
}

class Cucumber extends Vegetable {
    public Cucumber(double weight) {
        super("Cucumber", 16, weight, "Green");
    }
}

class Onion extends Vegetable {
    public Onion(double weight) {
        super("Onion", 40, weight, "White");
    }
}

class BellPepper extends Vegetable {
    public BellPepper(double weight) {
        super("Bell Pepper", 20, weight, "Red");
    }
}

class Lettuce extends Vegetable {
    public Lettuce(double weight) {
        super("Lettuce", 15, weight, "Green");
    }
}

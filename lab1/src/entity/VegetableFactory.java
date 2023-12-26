package entity;

// Фабричний клас для створення овочів
public class VegetableFactory {

    // Методи для створення екземплярів овочів
    public static Vegetable createCarrot(double weight) {
        return new Carrot(weight);
    }

    public static Vegetable createTomato(double weight) {
        return new Tomato(weight);
    }

    public static Vegetable createCucumber(double weight) {
        return new Cucumber(weight);
    }

    public static Vegetable createOnion(double weight) {
        return new Onion(weight);
    }

    public static Vegetable createBellPepper(double weight) {
        return new BellPepper(weight);
    }

    public static Vegetable createLettuce(double weight) {
        return new Lettuce(weight);
    }

}

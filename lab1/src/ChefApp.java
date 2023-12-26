import entity.*;
import utility.SaladMaker;
import java.util.Scanner;

public class ChefApp {
    public static void main(String[] args) {
        SaladMaker saladMaker = new SaladMaker();
        Salad salad = saladMaker.createSalad();

        // Створення об'єктів овочів
        Vegetable carrot = VegetableFactory.createCarrot(100);
        Vegetable tomato = VegetableFactory.createTomato(50);
        Vegetable cucumber = VegetableFactory.createCucumber(75);
        Vegetable onion = VegetableFactory.createOnion(30);
        Vegetable bellPepper = VegetableFactory.createBellPepper(60);
        Vegetable lettuce = VegetableFactory.createLettuce(200);

        // Додавання овочів до салату
        saladMaker.addVegetableToSalad(salad, carrot);
        saladMaker.addVegetableToSalad(salad, tomato);
        saladMaker.addVegetableToSalad(salad, cucumber);
        saladMaker.addVegetableToSalad(salad, onion);
        saladMaker.addVegetableToSalad(salad, bellPepper);
        saladMaker.addVegetableToSalad(salad, lettuce);

        // Виведення загальної калорійності салату
        System.out.println("Загальна калорійність салату: " + salad.getTotalCalories() + " ккал");

        // Сортування овочів за вагою і їх виведення
        System.out.println("Овочі в салаті, відсортовані за вагою:");
        saladMaker.printIngredientsSortedByWeight(salad);

        // Запит у користувача діапазону калорійності
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введіть мінімальну калорійність:");
        double minCalories = scanner.nextDouble();
        System.out.println("Введіть максимальну калорійність:");
        double maxCalories = scanner.nextDouble();

        // Виведення овочів, що відповідають заданому діапазону калорійності
        System.out.println("Овочі в салаті з калорійністю від " + minCalories + " до " + maxCalories + " ккал/100г:");
        saladMaker.printVegetablesByCalorieRange(salad, minCalories, maxCalories);
    }
}

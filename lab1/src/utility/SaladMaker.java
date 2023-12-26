package utility;

import entity.Salad;
import entity.Vegetable;

// Вспомогательный класс для создания и управления салатами
public class SaladMaker {

    // Создание нового салата
    public Salad createSalad() {
        return new Salad();
    }

    // Добавление овоща в салат
    public void addVegetableToSalad(Salad salad, Vegetable veg) {
        salad.addVegetable(veg);
    }

    // Вывод ингредиентов салата, отсортированных по весу
    public void printIngredientsSortedByWeight(Salad salad) {
        salad.getIngredientsSortedByWeight()
                .forEach(v -> System.out.println(v.getName() + " - " + v.getWeight() + "г"));
    }

    // Вывод овощей в салате, попадающих в заданный диапазон калорийности
    public void printVegetablesByCalorieRange(Salad salad, double minCalories, double maxCalories) {
        salad.getVegetablesByCalorieRange(minCalories, maxCalories)
                .forEach(v -> System.out.println(v.getName() + " - " + v.getCalories() + " ккал/100г"));
    }
}

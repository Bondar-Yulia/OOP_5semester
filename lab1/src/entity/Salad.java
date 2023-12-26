package entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Класс, представляющий салат, состоящий из различных овощей
public class Salad {
    private List<Vegetable> ingredients;

    public Salad() {
        this.ingredients = new ArrayList<>();
    }

    // Добавление овоща в салат
    public void addVegetable(Vegetable veg) {
        ingredients.add(veg);
    }

    // Расчёт общей калорийности салата
    public double getTotalCalories() {
        return ingredients.stream()
                .mapToDouble(Vegetable::getTotalCalories)
                .sum();
    }

    // Получение списка ингредиентов, отсортированных по весу
    public List<Vegetable> getIngredientsSortedByWeight() {
        return ingredients.stream()
                .sorted((v1, v2) -> Double.compare(v1.getWeight(), v2.getWeight()))
                .collect(Collectors.toList());
    }

    // Получение списка овощей, попадающих в заданный диапазон калорийности
    public List<Vegetable> getVegetablesByCalorieRange(double minCalories, double maxCalories) {
        return ingredients.stream()
                .filter(v -> v.getCalories() >= minCalories && v.getCalories() <= maxCalories)
                .collect(Collectors.toList());
    }
}

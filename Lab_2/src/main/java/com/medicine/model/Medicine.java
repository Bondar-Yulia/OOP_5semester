package com.medicine.model;

import java.util.List;
import java.util.ArrayList;

public class Medicine {
    private List<Drug> drugs;

    // Конструкторы, геттеры и сеттеры
    public Medicine() {
        this.drugs = new ArrayList<>(); // Инициализация списка
    }

    public List<Drug> getDrugs() {
        return drugs;
    }

    public void setDrugs(List<Drug> drugs) {
        this.drugs = drugs;
    }
}

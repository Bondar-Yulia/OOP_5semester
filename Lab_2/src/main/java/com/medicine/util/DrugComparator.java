package com.medicine.util;

import com.medicine.model.Drug;
import java.util.Comparator;

public class DrugComparator implements Comparator<Drug> {
    @Override
    public int compare(Drug drug1, Drug drug2) {
        // Реализация сравнения, например, по имени
        return drug1.getName().compareTo(drug2.getName());
    }
}

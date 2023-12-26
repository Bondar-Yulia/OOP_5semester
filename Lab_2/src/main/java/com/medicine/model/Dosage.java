package com.medicine.model;

public class Dosage {
    private String amount;
    private String frequency;

    // Конструкторы, геттеры и сеттеры
    public Dosage() {
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
}

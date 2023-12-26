package com.medicine.model;

public class MedicinePackage {
    private String type;
    private String quantity;
    private double price;

    // Конструкторы, геттеры и сеттеры
    public MedicinePackage() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

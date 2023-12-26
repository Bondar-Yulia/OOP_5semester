package com.medicine.model;
import java.util.ArrayList;
import java.util.List;
public class Version {
    private String type;
    private Certificate certificate;
    private MedicinePackage pack;
    private Dosage dosage;
    private List<Certificate> certificates = new ArrayList<>();

    // Конструкторы, геттеры и сеттеры
    public Version() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }

    public MedicinePackage getPackage() {
        return pack;
    }

    public void setPackage(MedicinePackage pack) {
        this.pack = pack;
    }

    public Dosage getDosage() {
        return dosage;
    }

    public void setDosage(Dosage dosage) {
        this.dosage = dosage;
    }
}

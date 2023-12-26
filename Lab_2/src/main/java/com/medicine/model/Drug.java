package com.medicine.model;

import java.util.List;
import java.util.ArrayList;

public class Drug {
    private String name;
    private String pharm;
    private String group;
    private List<Analog> analogs;
    private List<Version> versions;

    // Конструкторы, геттеры и сеттеры
    public Drug() {
        this.analogs = new ArrayList<>(); // Инициализация списка аналогов
        this.versions = new ArrayList<>(); // Инициализация списка версий
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPharm() {
        return pharm;
    }

    public void setPharm(String pharm) {
        this.pharm = pharm;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<Analog> getAnalogs() {
        return analogs;
    }

    public void setAnalogs(List<Analog> analogs) {
        this.analogs = analogs;
    }

    public List<Version> getVersions() {
        return versions;
    }

    public void setVersions(List<Version> versions) {
        this.versions = versions;
    }
}

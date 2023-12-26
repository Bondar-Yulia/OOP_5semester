package com.medicine.parser;

import com.medicine.model.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.text.SimpleDateFormat;

public class MedicineDOMParser {

    public Medicine parseDocument(Document doc) {
        Medicine medicine = new Medicine();

        // Получаем корневой элемент
        Element root = doc.getDocumentElement();

        // Обрабатываем каждый элемент Drug
        NodeList drugNodes = root.getElementsByTagName("Drug");
        for (int i = 0; i < drugNodes.getLength(); i++) {
            Element drugElement = (Element) drugNodes.item(i);
            Drug drug = new Drug();
            drug.setName(drugElement.getAttribute("name"));
            drug.setPharm(drugElement.getAttribute("pharm"));
            drug.setGroup(drugElement.getAttribute("group"));

            // Обработка Analogs
            NodeList analogNodes = drugElement.getElementsByTagName("Analog");
            for (int j = 0; j < analogNodes.getLength(); j++) {
                Element analogElement = (Element) analogNodes.item(j);
                Analog analog = new Analog();
                analog.setName(analogElement.getAttribute("name"));
                drug.getAnalogs().add(analog);
            }

            // Обработка Versions
            NodeList versionNodes = drugElement.getElementsByTagName("Version");
            for (int j = 0; j < versionNodes.getLength(); j++) {
                Element versionElement = (Element) versionNodes.item(j);
                Version version = new Version();
                version.setType(versionElement.getAttribute("type"));

                // Заполнение данных о Certificate, Package и Dosage
                version.setCertificate(getCertificate(versionElement));
                version.setPackage(getPackage(versionElement));
                version.setDosage(getDosage(versionElement));

                drug.getVersions().add(version);
            }

            medicine.getDrugs().add(drug);
        }

        return medicine;
    }

    private Certificate getCertificate(Element versionElement) {
        Element certElement = (Element) versionElement.getElementsByTagName("Certificate").item(0);
        Certificate certificate = new Certificate();
        certificate.setNumber(certElement.getAttribute("number"));
        certificate.setIssueDate(parseDate(certElement.getAttribute("issueDate")));
        certificate.setExpiryDate(parseDate(certElement.getAttribute("expiryDate")));
        certificate.setOrganization(certElement.getAttribute("organization"));
        return certificate;
    }

    private MedicinePackage getPackage(Element versionElement) {
        Element packageElement = (Element) versionElement.getElementsByTagName("Package").item(0);
        MedicinePackage pack = new MedicinePackage();
        pack.setType(packageElement.getAttribute("type"));
        pack.setQuantity(packageElement.getAttribute("quantity"));
        pack.setPrice(Double.parseDouble(packageElement.getAttribute("price")));
        return pack;
    }

    private Dosage getDosage(Element versionElement) {
        Element dosageElement = (Element) versionElement.getElementsByTagName("Dosage").item(0);
        Dosage dosage = new Dosage();
        dosage.setAmount(dosageElement.getAttribute("amount"));
        dosage.setFrequency(dosageElement.getAttribute("frequency"));
        return dosage;
    }

    private java.util.Date parseDate(String dateString) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse date: " + dateString, e);
        }
    }
}

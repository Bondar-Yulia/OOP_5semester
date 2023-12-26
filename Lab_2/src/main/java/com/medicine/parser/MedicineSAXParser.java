package com.medicine.parser;

import com.medicine.model.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;

public class MedicineSAXParser extends DefaultHandler {
    private Medicine medicine;
    private Stack<String> elementStack = new Stack<>();
    private Stack<Object> objectStack = new Stack<>();

    public Medicine getMedicine() {
        return medicine;
    }

    @Override
    public void startDocument() throws SAXException {
        medicine = new Medicine();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        elementStack.push(qName);

        switch (qName) {
            case "Drug":
                Drug drug = new Drug();
                drug.setName(attributes.getValue("name"));
                drug.setPharm(attributes.getValue("pharm"));
                drug.setGroup(attributes.getValue("group"));
                if (medicine.getDrugs() == null) {
                    medicine.setDrugs(new ArrayList<>());
                }
                medicine.getDrugs().add(drug);
                objectStack.push(drug);
                break;
            case "Analog":
                Analog analog = new Analog();
                analog.setName(attributes.getValue("name"));
                Drug currentDrug = (Drug) objectStack.peek();
                if (currentDrug.getAnalogs() == null) {
                    currentDrug.setAnalogs(new ArrayList<>());
                }
                currentDrug.getAnalogs().add(analog);
                break;
            case "Version":
                Version version = new Version();
                version.setType(attributes.getValue("type"));
                currentDrug = (Drug) objectStack.peek();
                if (currentDrug.getVersions() == null) {
                    currentDrug.setVersions(new ArrayList<>());
                }
                currentDrug.getVersions().add(version);
                objectStack.push(version);
                break;
            case "Certificate":
                Certificate certificate = new Certificate();
                certificate.setNumber(attributes.getValue("number"));
                certificate.setIssueDate(parseDate(attributes.getValue("issueDate")));
                certificate.setExpiryDate(parseDate(attributes.getValue("expiryDate")));
                certificate.setOrganization(attributes.getValue("organization"));
                Version currentVersion = (Version) objectStack.peek();
                currentVersion.setCertificate(certificate);
                break;
            case "Package":
                MedicinePackage pack = new MedicinePackage();
                pack.setType(attributes.getValue("type"));
                pack.setQuantity(attributes.getValue("quantity"));
                pack.setPrice(Double.parseDouble(attributes.getValue("price")));
                currentVersion = (Version) objectStack.peek();
                currentVersion.setPackage(pack);
                break;
            case "Dosage":
                Dosage dosage = new Dosage();
                dosage.setAmount(attributes.getValue("amount"));
                dosage.setFrequency(attributes.getValue("frequency"));
                currentVersion = (Version) objectStack.peek();
                currentVersion.setDosage(dosage);
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        elementStack.pop();

        if ("Version".equals(qName)) {
            objectStack.pop();
        }
    }

    private Date parseDate(String dateString) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse date: " + dateString, e);
        }
    }
}

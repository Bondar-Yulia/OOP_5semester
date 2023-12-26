package com.medicine.parser;

import com.medicine.model.*;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.Attribute;
import javax.xml.namespace.QName;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Date;

public class MedicineStAXParser {

    public Medicine parseDocument(XMLEventReader eventReader) {
        Medicine medicine = new Medicine();
        Drug drug = null;
        Version version = null;
        Certificate certificate = null;
        MedicinePackage pack = null;
        Dosage dosage = null;

        try {
            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    String qName = startElement.getName().getLocalPart();

                    switch (qName) {
                        case "Drug":
                            drug = new Drug();
                            medicine.getDrugs().add(drug);
                            Iterator<Attribute> attributes = startElement.getAttributes();
                            while (attributes.hasNext()) {
                                Attribute attribute = attributes.next();
                                if (attribute.getName().toString().equals("name")) {
                                    drug.setName(attribute.getValue());
                                } else if (attribute.getName().toString().equals("pharm")) {
                                    drug.setPharm(attribute.getValue());
                                } else if (attribute.getName().toString().equals("group")) {
                                    drug.setGroup(attribute.getValue());
                                }
                            }
                            break;
                        case "Analog":
                            Analog analog = new Analog();
                            analog.setName(startElement.getAttributeByName(new QName("name")).getValue());
                            drug.getAnalogs().add(analog);
                            break;
                        case "Version":
                            version = new Version();
                            version.setType(getAttribute(startElement, "type"));
                            if (drug != null) {
                                drug.getVersions().add(version);
                            }
                            break;
                        case "Certificate":
                            certificate = new Certificate();
                            certificate.setNumber(getAttribute(startElement, "number"));
                            certificate.setIssueDate(parseDate(getAttribute(startElement, "issueDate")));
                            certificate.setExpiryDate(parseDate(getAttribute(startElement, "expiryDate")));
                            certificate.setOrganization(getAttribute(startElement, "organization"));
                            if (version != null) {
                                version.setCertificate(certificate);
                            }
                            break;
                        case "Package":
                            pack = new MedicinePackage();
                            pack.setType(getAttribute(startElement, "type"));
                            pack.setQuantity(getAttribute(startElement, "quantity"));
                            pack.setPrice(Double.parseDouble(getAttribute(startElement, "price")));
                            if (version != null) {
                                version.setPackage(pack);
                            }
                            break;
                        case "Dosage":
                            dosage = new Dosage();
                            dosage.setAmount(getAttribute(startElement, "amount"));
                            dosage.setFrequency(getAttribute(startElement, "frequency"));
                            if (version != null) {
                                version.setDosage(dosage);
                            }
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return medicine;
    }

    private String getAttribute(StartElement startElement, String attributeName) {
        Attribute attribute = startElement.getAttributeByName(new QName(attributeName));
        return attribute != null ? attribute.getValue() : null;
    }

    private Date parseDate(String dateString) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse date: " + dateString, e);
        }
    }
}
package com.medicine;

import com.medicine.model.Medicine;
import com.medicine.parser.MedicineDOMParser;
import com.medicine.parser.MedicineSAXParser;
import com.medicine.parser.MedicineStAXParser;
import com.medicine.util.DrugComparator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamReader;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        try {
            String xmlFile = "C:\\Users\\38068\\OneDrive\\Рабочий стол\\OOP\\Lab_2\\src\\main\\resources\\Medicine.xml";
            String xsdFile = "C:\\Users\\38068\\OneDrive\\Рабочий стол\\OOP\\Lab_2\\src\\main\\resources\\Medicine.xsd";
//            String xmlFile = "C:/Users/38068/OneDrive/Рабочий стол/OOP/Lab_2/src/main\\resources\\Medicine.xml";
//            String xsdFile = "C:/Users/38068/OneDrive/Рабочий стол/OOP/Lab_2/resources/Medicine.xsd";

            validateXMLSchema(xsdFile, xmlFile);

            // DOM Parser
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            MedicineDOMParser domParser = new MedicineDOMParser();
            Medicine medicineDOM = domParser.parseDocument(dBuilder.parse(new File(xmlFile)));
            System.out.println("DOM Parser Output:");
            printMedicine(medicineDOM);

            // SAX Parser
            SAXParserFactory saxFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxFactory.newSAXParser();
            MedicineSAXParser medicineSAXParser = new MedicineSAXParser();
            saxParser.parse(new File(xmlFile), medicineSAXParser);
            Medicine medicineSAX = medicineSAXParser.getMedicine();
            System.out.println("SAX Parser Output:");
            printMedicine(medicineSAX); // вывод результата

            // StAX Parser
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader streamReader = factory.createXMLStreamReader(new FileInputStream(xmlFile));
            XMLEventReader eventReader = factory.createXMLEventReader(streamReader);

            MedicineStAXParser staxParser = new MedicineStAXParser();
            Medicine medicineStAX = staxParser.parseDocument(eventReader);
            System.out.println("StAX Parser Output:");
            printMedicine(medicineStAX);

            // Сортировка
            Collections.sort(medicineDOM.getDrugs(), new DrugComparator());

            // Вывод результатов после сортировки
            System.out.println("Sorted Medicine:");
            printMedicine(medicineDOM);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void validateXMLSchema(String xsdPath, String xmlPath) throws Exception {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = factory.newSchema(new File(xsdPath));
        Validator validator = schema.newValidator();
        validator.validate(new StreamSource(new File(xmlPath)));
    }

    private static void printMedicine(Medicine medicine) {
        // ваш код для вывода информации
    }
}

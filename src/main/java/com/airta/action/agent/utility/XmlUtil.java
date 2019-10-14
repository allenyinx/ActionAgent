package com.airta.action.agent.utility;

import com.airta.action.agent.entity.DriverConfig;
import com.airta.action.agent.entity.html.Element;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;

public class XmlUtil {

    public static void main(String[] args) throws IOException {

        Element customer = new Element();

        // 1. Write Object to XML String
        String xml = write2XMLString(customer);

        System.out.println(xml);

        // 2. Write Object to XML File
        write2XMLFile(customer, DriverConfig.SITEMAP_PATH);
    }

    public static void storeElement(Element element) {

        String xml = null;
        try {
            xml = write2XMLString(element);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println(xml);

        // 2. Write Object to XML File

        try {
            write2XMLFile(element, DriverConfig.SITEMAP_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * Convert Object to XML String
     */
    public static String write2XMLString(Object object)
            throws JsonProcessingException {

        XmlMapper xmlMapper = new XmlMapper();
        // use the line of code for pretty-print XML on console. We should remove it in production.
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);

        return xmlMapper.writeValueAsString(object);
    }

    /*
     * Write Object to XML file
     */
    public static void write2XMLFile(Object object, String pathFile)
            throws JsonGenerationException, JsonMappingException, IOException {

        XmlMapper xmlMapper = new XmlMapper();
        // use the line of code for pretty-print XML on console. We should remove it in production.
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);

        File xmlFile = new File(pathFile);
        if (xmlFile.exists()) {
            xmlFile.delete();
        }

        xmlMapper.writeValue(new File(pathFile), object);
    }
}

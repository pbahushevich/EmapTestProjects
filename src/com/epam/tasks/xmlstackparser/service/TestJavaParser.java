package com.epam.tasks.xmlstackparser.service;

import com.epam.tasks.xmlstackparser.entity.XMLDocument;
import com.epam.tasks.xmlstackparser.logic.impl.XMLRegexParser;
import com.epam.tasks.xmlstackparser.logic.XMLReader;
import com.epam.tasks.xmlstackparser.logic.impl.XMLBasicReader;

import java.io.FileReader;

/**
 * Created by Belarus on 07.12.2015.
 */
public class TestJavaParser {

    public static void main(String[] args) {
        XMLReader reader = null;
        try {
            reader = new XMLBasicReader(new FileReader("students_big_list.xml"));
            XMLDocument document = new XMLRegexParser().parseData(reader);
            System.out.println(document);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }finally {
            if (reader!= null){
                try{
                    reader.close();
                }catch (Exception ie){
                    System.out.println(ie.getMessage());
                }
            }
        }
    }


}

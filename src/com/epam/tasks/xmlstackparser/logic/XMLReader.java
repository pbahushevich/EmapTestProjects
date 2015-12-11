package com.epam.tasks.xmlstackparser.logic;

import com.epam.tasks.xmlstackparser.exception.XMLParseException;

import java.io.IOException;

/**
 * Created by Belarus on 07.12.2015.
 */
public interface XMLReader {

    String readXMlPhrase() throws XMLParseException;

    boolean ready() throws IOException;

    void close() throws IOException;


}

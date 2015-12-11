package com.epam.tasks.xmlstackparser.logic;

import com.epam.tasks.xmlstackparser.entity.XMLDocument;
import com.epam.tasks.xmlstackparser.exception.XMLParseException;

/**
 * Created by Belarus on 07.12.2015.
 */
public interface XMLParser {

    public XMLDocument parseData(XMLReader reader)throws XMLParseException;

}

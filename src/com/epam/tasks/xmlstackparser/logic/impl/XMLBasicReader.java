package com.epam.tasks.xmlstackparser.logic.impl;

import com.epam.tasks.xmlstackparser.exception.XMLParseException;
import com.epam.tasks.xmlstackparser.logic.XMLReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Belarus on 07.12.2015.
 */
public class XMLBasicReader extends BufferedReader implements XMLReader {

    StringBuffer buffer = new StringBuffer();

    public XMLBasicReader(InputStreamReader is){
        super(is);
    }

    @Override
    public String readXMlPhrase() throws XMLParseException {

        String result = "";

        try{

           while (ready()){
               result = getXMLPhraseFromBuffer();
               if (result!= null) {
                   return result;
               }else {
                   readNextLineToBuffer();
               }
             }

        }catch (IOException ie){
            throw new XMLParseException(ie.getMessage());
        }
        return  buffer.toString();
    }

    private void readNextLineToBuffer() throws IOException{

        buffer.append("\n").append(readLine());

    }

    private String getXMLPhraseFromBuffer() {

        String result=null;
        int FORTHCOMING_CONTENT_GROUP = 1;
        int TAG_GROUP = 2;
        int AFTER_TAG_TEXT_GROUP = 3;

        Pattern phrasePattern = Pattern.compile("([^<]+)?(<.+?>)?(.*)?");
        Matcher phraseMatcher = phrasePattern.matcher(buffer.toString().trim());

        if(phraseMatcher.find()){

            if (phraseMatcher.group(FORTHCOMING_CONTENT_GROUP)!=null && phraseMatcher.group(TAG_GROUP)!=null){

                buffer = new StringBuffer(phraseMatcher.group(TAG_GROUP) + phraseMatcher.group(AFTER_TAG_TEXT_GROUP));
                result = phraseMatcher.group(FORTHCOMING_CONTENT_GROUP);

            }else if(phraseMatcher.group(TAG_GROUP)!=null){

                buffer = new StringBuffer(String.valueOf(phraseMatcher.group(AFTER_TAG_TEXT_GROUP)));
                result = phraseMatcher.group(TAG_GROUP);
            }
        }
        return result;
    }

    @Override
    public boolean ready() throws IOException {
        return super.ready();
    }

    @Override
    public void close() throws IOException {
        super.close();
    }
}

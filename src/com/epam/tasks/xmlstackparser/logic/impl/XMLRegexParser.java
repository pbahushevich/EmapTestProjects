package com.epam.tasks.xmlstackparser.logic.impl;

import com.epam.tasks.xmlstackparser.exception.XMLParseException;
import com.epam.tasks.xmlstackparser.entity.Attribute;
import com.epam.tasks.xmlstackparser.entity.Node;
import com.epam.tasks.xmlstackparser.entity.XMLDocument;
import com.epam.tasks.xmlstackparser.logic.XMLParser;
import com.epam.tasks.xmlstackparser.logic.Pair;
import com.epam.tasks.xmlstackparser.logic.XMLReader;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Belarus on 26.11.2015.
 */
public class XMLRegexParser implements XMLParser {
    private Stack<Pair<Node,Integer>> nodeStack = new Stack<>();
    private Stack<Pair<Node,Integer>> elementStack = new Stack<>();
    private int currentLevel = 0;                                                                                       //the depth of the current in the reader
    private static final Pattern attributePattern = Pattern.compile("([a-zA-Z]{1}[a-z|A-Z|\\d|\\-]+)=\"([^\"]+)");      // here we search for the pattern like blablabla="blablabla
    private static final Pattern nodeCatchingPattern = Pattern.compile("(<(?<tagName>[a-zA-Z][\\w\\-]*)(?<emptyTag>/?)(?<attributes>[^>]*)>)?" +
            "(?<tagContent>[^<]+)?(</(?<closingTag>[^>]+)>)?");

    private final String TAG_NAME_GROUP = "tagName";
    private final String ATTRIBURTES_GROUP = "attributes";
    private final String CONTENT_GROUP = "tagContent";
    private final String EMPTY_TAG_GROUP = "emptyTag";
    private final String CLOSING_TAG_GROUP = "closingTag";
    private final int ATTRIBUTE_NAME_GROUP = 1;
    private final int ATTRIBUTE_VALUE_GROUP = 2;

    public XMLDocument parseData(XMLReader reader) throws XMLParseException {
        XMLDocument result = null;
        Integer currentLevel = 0;
          try {
              while (reader.ready()) {
                  String xMlPhrase = reader.readXMlPhrase();
                  Matcher matcher = nodeCatchingPattern.matcher(xMlPhrase.trim());
                  if (matcher.find()) {
                      parseXMLPhrase(matcher);
                  }
              }
              result = getXMLDocumentFromStack();
        }catch (Exception ex){
            throw new XMLParseException(ex.getMessage());
        }
        return result;
    }

    private void parseXMLPhrase(Matcher matcher) throws XMLParseException {

        if (matcher.group(TAG_NAME_GROUP)!=null){
            parseTagName(matcher);
        }

        if(matcher.group(CONTENT_GROUP)!=null ){

            if(nodeStack.size()==0){
                throw new XMLParseException("Found text content in an unexpected place!No current opened nodes....");
            }
            parseTagContent(matcher);
        }


        if((matcher.group(CLOSING_TAG_GROUP)!=null) || (matcher.group(EMPTY_TAG_GROUP)!=null && !("").equals(matcher.group(EMPTY_TAG_GROUP)))){
            if(nodeStack.size()==0){
                throw new XMLParseException("Found closing tag in an unexpected place!No current opened nodes....");
            }
            processEndOfTag();
        }
    }

    private void processEndOfTag() {

        Node currentNode  = nodeStack.pop().getKey();
        while (elementStack.size()>0 && elementStack.get(elementStack.size() - 1).getValue()>=currentLevel){
            currentNode.addElement(elementStack.pop().getKey());
        }
        elementStack.push(new Pair<>(currentNode,currentLevel-1));
        currentLevel--;
    }

    private void parseTagContent(Matcher matcher) {

        Pair<Node,Integer> currentPair = nodeStack.pop();
        currentPair.getKey().setValue(matcher.group(CONTENT_GROUP));
        nodeStack.push(currentPair);

    }

    private void parseTagName(Matcher matcher) {


        Node newNode = new Node(matcher.group(TAG_NAME_GROUP));
        parseAttributes(newNode, matcher.group(ATTRIBURTES_GROUP));
        nodeStack.push(new Pair<>(newNode, currentLevel++));
    }

    private void parseAttributes(Node node, String nodeAttributes) {


        if("".equals(nodeAttributes)){
            return;
        }

        Matcher matcher = attributePattern.matcher(nodeAttributes);
        while(matcher.find()){
            node.addAttribute(new Attribute(matcher.group(ATTRIBUTE_NAME_GROUP), matcher.group(ATTRIBUTE_VALUE_GROUP)));
        }

    }

    private XMLDocument getXMLDocumentFromStack() throws XMLParseException{
        if (elementStack.size()==0){
            throw new XMLParseException("Error processing root node for XMLDocument after successful parsing the file. No root element found in stack.");
        }else if(elementStack.size()>1){
            throw new XMLParseException("Error processing root node for XMLDocument after successful parsing the file."+elementStack.size()+" root elements found in stack. Should be one.");
        }
        return new XMLDocument(elementStack.pop().getKey());
    }

 }

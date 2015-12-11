package com.epam.tasks.xmlstackparser.entity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Belarus on 25.11.2015.
 */
public class Node {

    private List<Node> elements;
    private List<Attribute> attributes;
    private String value  = "";
    private final String name;

    public Node(){
        this.name = "Node created without a name.... one lonely nameless node :(";
    }

    public Node(String name) {

        this.name = name;

    }

    public Node(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public Node(String value, String name, List<Attribute> attributes,List<Node> elements ) {
        this.name = name;
        this.value = value;
        this.attributes = attributes;
        this.elements = elements;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void addElement(Node element){
        if(elements == null){
            elements = new LinkedList<>();
        }
        elements.add(element);
    }

    public List<Node> getElements(){
        return elements;
    }

    public void addAttribute(Attribute at){
        if(attributes == null){
            attributes = new LinkedList<>();
        }
        attributes.add(at);
    }

    public List<Attribute> getAttributes(){
        return attributes;
    }

    @Override
    public String toString(){

        return this.getStringView("");

    }

    private String getStringView(String border){

        StringBuffer result = new StringBuffer();

        String attributesString = getAttributeString();

        result.append(border).append(getTagNamePresentation(name, attributesString));

        if(!"".equals(value)){
            result.append("\t=\t\"").append(value).append("\"");
        }

        result.append("\r\n");

        if(elements!=null){
            for (Node node: elements){
                result.append(node.getStringView("|"+"\t"+border)).append("\r\n");
            }
            result.append(border).append(getTagNamePresentation("/"+name,"")).append("\r\n");
        }

        return result.toString().trim();
    }

    private String getAttributeString() {
        String attributesString ="";
        if (attributes != null) {
            for (Attribute attribute : attributes){
                attributesString = attributesString+attribute.getName()+"="+attribute.getValue()+";";
            }
            attributesString = " ("+attributesString.substring(0,attributesString.length()-1)+")";
        }
        return attributesString;
    }

    private String getTagNamePresentation(String name, String attributes){

        String nameAndAttributes = name+attributes;;
        StringBuffer result = new StringBuffer("<");
        int leftHyphenCount =0,rightHyphenCount = 0;

        leftHyphenCount= (20 - nameAndAttributes.length())/2;
        rightHyphenCount = leftHyphenCount+ (20-nameAndAttributes.length())%2;

        for (int i = 0; i < leftHyphenCount; i++) {
            result.append("-");
        }

        result.append(nameAndAttributes);

        for (int i = 0; i < rightHyphenCount; i++) {
            result.append("-");
        }

        result.append(">");

        return result.toString();

    }

    @Override
    public int hashCode() {
        return getRecursiveHashCode();
    }

    private int getRecursiveHashCode(){
        int currentHash = 0;
        currentHash = currentHash+23*name.hashCode()+17*value.hashCode();
        for(Attribute attributet: attributes){
            currentHash = currentHash+29*attributet.hashCode();
        }
        for(Node node: elements){
            currentHash = currentHash+31*node.hashCode();
        }
        return currentHash;

    }

    @Override
    public boolean equals(Object obj) {
        if(!super.equals(obj)){
            return false;
        }

        if(obj.getClass()!= this.getClass()) {
            return false;
        }

        Node node = (Node)obj;
        if (!name.equals(node.getName()) || !value.equals(node.getValue())){
            return false;
        }
        if(!attributes.equals(node.attributes)) {
            return false;
        }
        if(!elements.equals(node.elements)) {
            return false;
        }

        return true;
    }

}

package com.epam.tasks.xmlstackparser.entity;


/**
 * Created by Belarus on 25.11.2015.
 */
public class XMLDocument {
    private Node rootNode;
    public XMLDocument(Node rootNode) {
        this.rootNode = rootNode;
    }

    @Override
    public boolean equals(Object obj){
        if(obj.getClass()!= this.getClass()) {
            return false;
        }
        XMLDocument xmlDoc = (XMLDocument)obj;

        if (!rootNode.equals(xmlDoc.rootNode)){
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "XMLDocument: "+"\n"+this.rootNode.toString();
    }
}
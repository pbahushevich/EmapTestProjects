package com.epam.tasks.xmlstackparser.entity;

/**
 * Created by Belarus on 25.11.2015.
 */
public class Attribute {
    private final String name;
    private String value;
    public Attribute(String name, String value) {
        this.name  = name;
        this.value = value;

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

    @Override
    public int hashCode() {
        return 17*name.hashCode()+29*value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if(obj.getClass()!= Attribute.class) {
            return false;
        }
        Attribute attribute = (Attribute)obj;
        if (name.equals(attribute.getName()) && value.equals(attribute.getValue())){
            return true;
        }else{
            return false;
        }
    }
}

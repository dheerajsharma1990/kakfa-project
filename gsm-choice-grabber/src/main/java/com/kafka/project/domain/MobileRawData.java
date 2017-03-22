package com.kafka.project.domain;

import java.io.Serializable;
import java.util.Map;

public class MobileRawData implements Serializable {

    private String name;

    private Map<String, String> attributes;

    public MobileRawData(String name, Map<String, String> attributes) {
        this.name = name;
        this.attributes = attributes;
    }

    public String getName() {
        return name;
    }

    public int getAttributesSize() {
        return attributes.size();
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    @Override
    public String toString() {
        return "MobileRawData{" +
                "name='" + name + '\'' +
                ", attributes=" + attributes +
                '}';
    }
}

package com.kafka.project.gsm.domain;

import java.io.Serializable;
import java.util.Map;

public class RawMobileData implements Serializable {

    private String name;

    private Map<String, String> attributes;

    public RawMobileData(String name, Map<String, String> attributes) {
        this.name = name;
        this.attributes = attributes;
    }

    public String getName() {
        return name;
    }


    @Override
    public String toString() {
        return "MobileRawData{" +
                "name='" + name + '\'' +
                ", attributes=" + attributes +
                '}';
    }

}

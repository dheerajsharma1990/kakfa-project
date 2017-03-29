package com.kafka.project.gsm.domain;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

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

    public List<RawMobileData> splitIntoThree() {
        int totalSize = attributes.size();
        int oneThird = totalSize / 3;
        List<Map.Entry<String, String>> entryList = attributes.entrySet().stream().collect(Collectors.toList());
        List<Map.Entry<String, String>> entryList1 = entryList.subList(0, oneThird);
        List<Map.Entry<String, String>> entryList2 = entryList.subList(oneThird, oneThird + oneThird);
        List<Map.Entry<String, String>> entryList3 = entryList.subList(oneThird + oneThird, entryList.size());
        Map<String, String> attributes1 = entryList1.stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        Map<String, String> attributes2 = entryList2.stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        Map<String, String> attributes3 = entryList3.stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return Arrays.asList(new RawMobileData(name, attributes1), new RawMobileData(name, attributes2), new RawMobileData(name, attributes3));
    }


    @Override
    public String toString() {
        return "MobileRawData{" +
                "name='" + name + '\'' +
                ", attributes=" + attributes +
                '}';
    }

}

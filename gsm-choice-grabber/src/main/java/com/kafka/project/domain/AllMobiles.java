package com.kafka.project.domain;

import java.io.Serializable;
import java.util.List;

public class AllMobiles implements Serializable {

    private List<MobileRawData> allMobiles;

    public AllMobiles(List<MobileRawData> allMobiles) {
        this.allMobiles = allMobiles;
    }

    @Override
    public String toString() {
        return "AllMobiles{" +
                "allMobiles=" + allMobiles +
                '}';
    }
}

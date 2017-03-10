package com.dheeraj.kafka.producer.domain;

import java.io.Serializable;

public class Mobile implements Serializable {

    private final MobileId mobileId;

    private final String productName;

    public Mobile(MobileId mobileId, String productName) {
        this.mobileId = mobileId;
        this.productName = productName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Mobile mobile = (Mobile) o;

        return mobileId != null ? mobileId.equals(mobile.mobileId) : mobile.mobileId == null;

    }

    @Override
    public int hashCode() {
        return mobileId != null ? mobileId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Mobile{" +
                "mobileId=" + mobileId +
                ", productName='" + productName + '\'' +
                '}';
    }
}

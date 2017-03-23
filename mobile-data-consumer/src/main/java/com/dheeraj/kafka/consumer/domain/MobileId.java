package com.dheeraj.kafka.consumer.domain;

public class MobileId  {

    private final String mobileId;

    public MobileId(String mobileId) {
        this.mobileId = mobileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MobileId mobileId1 = (MobileId) o;

        return mobileId != null ? mobileId.equals(mobileId1.mobileId) : mobileId1.mobileId == null;

    }

    @Override
    public int hashCode() {
        return mobileId != null ? mobileId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "MobileId{" +
                "mobileId='" + mobileId + '\'' +
                '}';
    }
}

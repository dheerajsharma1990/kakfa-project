package com.dheeraj.kafka.producer.domain;

public class Mobile  {

    private final MobileId mobileId;

    private final String mobileName;

    public Mobile(MobileId mobileId, String mobileName) {
        this.mobileId = mobileId;
        this.mobileName = mobileName;
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
                ", mobileName='" + mobileName + '\'' +
                '}';
    }
}

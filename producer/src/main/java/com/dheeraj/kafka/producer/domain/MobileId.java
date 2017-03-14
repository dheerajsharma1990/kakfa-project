package com.dheeraj.kafka.producer.domain;

public class MobileId implements DomainId<MobileId> {

    private final String productId;

    public MobileId(String productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MobileId mobileId1 = (MobileId) o;

        return productId != null ? productId.equals(mobileId1.productId) : mobileId1.productId == null;

    }

    @Override
    public int hashCode() {
        return productId != null ? productId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "MobileId{" +
                "productId='" + productId + '\'' +
                '}';
    }

    @Override
    public MobileId getId() {
        return this;
    }
}

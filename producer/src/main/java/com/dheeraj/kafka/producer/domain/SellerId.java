package com.dheeraj.kafka.producer.domain;

public class SellerId implements DomainId<SellerId> {

    private final String sellerCode;

    public SellerId(String sellerCode) {
        this.sellerCode = sellerCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SellerId sellerId = (SellerId) o;

        return sellerCode != null ? sellerCode.equals(sellerId.sellerCode) : sellerId.sellerCode == null;

    }

    @Override
    public int hashCode() {
        return sellerCode != null ? sellerCode.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "SellerId{" +
                "sellerCode='" + sellerCode + '\'' +
                '}';
    }

    @Override
    public SellerId getId() {
        return this;
    }
}

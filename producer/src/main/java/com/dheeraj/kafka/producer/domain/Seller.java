package com.dheeraj.kafka.producer.domain;

public class Seller implements Domain<Seller> {

    private final SellerId sellerId;

    private final String email;

    public Seller(SellerId sellerId, String email) {
        this.sellerId = sellerId;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Seller seller = (Seller) o;

        return sellerId != null ? sellerId.equals(seller.sellerId) : seller.sellerId == null;

    }

    @Override
    public int hashCode() {
        return sellerId != null ? sellerId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Seller{" +
                "sellerId=" + sellerId +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public Seller getDomainObject() {
        return this;
    }
}

package com.dheeraj.kafka.producer.domain;

import java.io.Serializable;

public class SellerProduct implements Serializable {

    private final SellerProductId sellerProductId;

    private final int price;

    public SellerProduct(SellerProductId sellerProductId, int price) {
        this.sellerProductId = sellerProductId;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SellerProduct that = (SellerProduct) o;

        return sellerProductId != null ? sellerProductId.equals(that.sellerProductId) : that.sellerProductId == null;

    }

    @Override
    public int hashCode() {
        return sellerProductId != null ? sellerProductId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "SellerProduct{" +
                "sellerProductId=" + sellerProductId +
                ", price=" + price +
                '}';
    }
}

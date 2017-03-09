package com.dheeraj.kafka.producer.domain;

import java.io.Serializable;

public class ProductId implements Serializable {

    private final String productId;

    public ProductId(String productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductId productId1 = (ProductId) o;

        return productId != null ? productId.equals(productId1.productId) : productId1.productId == null;

    }

    @Override
    public int hashCode() {
        return productId != null ? productId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ProductId{" +
                "productId='" + productId + '\'' +
                '}';
    }
}

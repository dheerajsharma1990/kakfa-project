package com.dheeraj.kafka.producer.domain;

public class SellerProductId implements DomainId<SellerProductId> {

    private final SellerId sellerId;

    private final MobileId mobileId;

    public SellerProductId(SellerId sellerId, MobileId mobileId) {
        this.sellerId = sellerId;
        this.mobileId = mobileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SellerProductId that = (SellerProductId) o;

        if (sellerId != null ? !sellerId.equals(that.sellerId) : that.sellerId != null) return false;
        return mobileId != null ? mobileId.equals(that.mobileId) : that.mobileId == null;

    }

    @Override
    public int hashCode() {
        int result = sellerId != null ? sellerId.hashCode() : 0;
        result = 31 * result + (mobileId != null ? mobileId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SellerProductId{" +
                "sellerId=" + sellerId +
                ", mobileId=" + mobileId +
                '}';
    }

    @Override
    public SellerProductId getId() {
        return this;
    }
}

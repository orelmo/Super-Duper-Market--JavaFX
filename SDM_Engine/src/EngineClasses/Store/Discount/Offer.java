package EngineClasses.Store.Discount;

import jaxbClasses.SDMOffer;

public class Offer {
    private double quantity;
    private int itemId;
    private int additionalPrice;

    public Offer(double quantity, int itemId, int additionalPrice){
        this.quantity = quantity;
        this.itemId = itemId;
        this.additionalPrice = additionalPrice;
    }

    public Offer(SDMOffer offer){
        this.quantity = offer.getQuantity();
        this.itemId = offer.getItemId();
        this.additionalPrice = offer.getForAdditional();
    }

    public double getQuantity() {
        return this.quantity;
    }

    public int getItemId() {
        return this.itemId;
    }

    public int getAdditionalPrice() {
        return this.additionalPrice;
    }
}

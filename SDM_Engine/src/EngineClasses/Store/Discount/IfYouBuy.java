package EngineClasses.Store.Discount;

public class IfYouBuy {
    private int itemIdNeedToBeBought;
    private double quantityNeedToBeBought;

    public IfYouBuy(){}

    public IfYouBuy(jaxbClasses.IfYouBuy ifYouBuy) {
        this.itemIdNeedToBeBought = ifYouBuy.getItemId();
        this.quantityNeedToBeBought = ifYouBuy.getQuantity();
    }

    public int getItemIdNeedToBeBought() {
        return this.itemIdNeedToBeBought;
    }

    public double getQuantityNeedToBeBought() {
        return this.quantityNeedToBeBought;
    }

    public void setItemIdNeedToBeBought(int itemIdNeedToBeBought) {
        this.itemIdNeedToBeBought = itemIdNeedToBeBought;
    }

    public void setQuantityNeedToBeBought(double quantityNeedToBeBought) {
        this.quantityNeedToBeBought = quantityNeedToBeBought;
    }
}
package SDMSystem;

import EngineClasses.Item.Item;

public class SDMSystemItem extends Item {
    private int numberOfStoresSellingTheItem;
    private float avgPrice;
    private float soldCounter;

    public SDMSystemItem(Item item) {
        super(item);
        this.numberOfStoresSellingTheItem = 0;
        this.avgPrice = 0;
        this.soldCounter = 0;
    }

    public int getNumberOfStoresSellingTheItem() {
        return this.numberOfStoresSellingTheItem;
    }

    public float getAvgPrice() {
        return this.avgPrice;
    }

    public float getSoldCounter() {
        return this.soldCounter;
    }

    public void updateAvgPriceAfterAdding(int itemNewPrice) {
        this.avgPrice = (this.getAvgPrice() * this.numberOfStoresSellingTheItem + itemNewPrice) / (this.numberOfStoresSellingTheItem + 1);
    }

    public void increaseSellersCounter() {
        ++this.numberOfStoresSellingTheItem;
    }

    public void updateSoldCounter(float amountSold) {
        this.soldCounter += amountSold;
    }

    public void setAvgPrice(float newAvgPrice) {
        this.avgPrice = newAvgPrice;
    }

    public void decreaseNumberOfStoresSellingTheItem() {
        --this.numberOfStoresSellingTheItem;
    }
}
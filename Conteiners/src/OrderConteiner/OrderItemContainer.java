package OrderConteiner;

import EngineClasses.Item.Item;
import EngineClasses.Store.Store;

public class OrderItemContainer {
    private Item item;
    private Number amount;
    private int price;
    private float priceForAmount;
    private Store seller;

    public Item getItem() {
        return this.item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Number getAmount() {
        return this.amount;
    }

    public void setAmount(Number amount) {
        this.amount = amount;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public float getPriceForAmount() {
        return this.priceForAmount;
    }

    public void setPriceForAmount(float priceForAmount) {
        this.priceForAmount = priceForAmount;
    }

    public Store getSeller() {
        return this.seller;
    }

    public void setSeller(Store seller) {
        this.seller = seller;
    }
}

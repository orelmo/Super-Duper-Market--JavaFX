package EngineClasses.Store;

import jaxbClasses.SDMSell;

public class Sell {
    private int itemId;
    private int price;
    private float sold;

    public int getItemId() {
        return this.itemId;
    }

    public Sell(int itemId, int itemPrice){
        this.itemId = itemId;
        this.price = itemPrice;
        this.sold = 0;
    }

    public Sell(SDMSell sdmSell) {
        this.itemId = sdmSell.getItemId();
        this.price = sdmSell.getPrice();
        this.sold = 0;
    }

    public int getPrice() {
        return this.price;
    }

    public float getSold() {
        return this.sold;
    }

    public void increaseSold(float amount){
        this.sold += amount;
    }

    @Override
    public String toString() {
        char index = 'd';
        StringBuilder sb = new StringBuilder();
        sb.append('\t').append(index++).append(". ").append("Item Price: ").append(this.price).append('\n');
        sb.append('\t').append(index).append(". ").append("Items Sold So Far: ").append(this.sold).append('\n');

        return sb.toString();
    }
}
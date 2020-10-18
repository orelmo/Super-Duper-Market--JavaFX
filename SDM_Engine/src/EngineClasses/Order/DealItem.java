package EngineClasses.Order;


import EngineClasses.Item.Item;
import EngineClasses.Item.ePurchaseCategory;
import ItemsDetailsContainer.ItemDetailsContainer;

public class DealItem {

    private String name;
    private ePurchaseCategory purchaseCategory;
    private int id;
    private int price;
    private float amount;

    public DealItem(ItemDetailsContainer otherItem) {
        this.name = otherItem.getName();
        this.purchaseCategory = otherItem.getPurchaseCategory();
        this.id = otherItem.getId();
        this.price = otherItem.getPriceAtStore();
        this.amount = otherItem.getAmount();
    }

    public String getName() {
        return this.name;
    }

    public ePurchaseCategory getPurchaseCategory() {
        return this.purchaseCategory;
    }

    public int getId() {
        return this.id;
    }

    public int getPrice() {
        return this.price;
    }

    public float getAmount() {
        return this.amount;
    }
}

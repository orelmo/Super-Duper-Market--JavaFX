package ItemsDetailsContainer;

import EngineClasses.Item.ePurchaseCategory;
import SDMSystem.SDMSystemItem;
import javafx.beans.property.FloatProperty;
import javafx.scene.control.TextField;

public class ItemDetailsContainer {
    private int id;
    private String name;
    private ePurchaseCategory purchaseCategory;
    private int storesSellingTheItem;
    private float avgPrice;
    private float soldCounter;
    private int priceAtStore;
    private float amount;

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public ItemDetailsContainer(){
    }

    public ItemDetailsContainer(SDMSystemItem item){
        this.id = item.getId();
        this.name = item.getName();
        this.purchaseCategory = item.getPurchaseCategory();
        this.storesSellingTheItem = item.getNumberOfStoresSellingTheItem();
        this.avgPrice = item.getAvgPrice();
        this.soldCounter = item.getSoldCounter();
    }

    public int getPriceAtStore() {
        return this.priceAtStore;
    }

    public void setPriceAtStore(int priceAtStore) {
        this.priceAtStore = priceAtStore;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ePurchaseCategory getPurchaseCategory() {
        return this.purchaseCategory;
    }

    public void setPurchaseCategory(ePurchaseCategory purchaseCategory) {
        this.purchaseCategory = purchaseCategory;
    }

    public int getStoresSellingTheItem() {
        return this.storesSellingTheItem;
    }

    public void setStoresSellingTheItem(int storesSellingTheItem) {
        this.storesSellingTheItem = storesSellingTheItem;
    }

    public float getAvgPrice() {
        return this.avgPrice;
    }

    public void setAvgPrice(float avgPrice) {
        this.avgPrice = avgPrice;
    }

    public float getSoldCounter() {
        return this.soldCounter;
    }

    public void setSoldCounter(float soldCounter) {
        this.soldCounter = soldCounter;
    }
}

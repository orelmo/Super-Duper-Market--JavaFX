package OrderConteiner;

import EngineClasses.Item.ePurchaseCategory;
import ItemsDetailsContainer.*;

import java.util.List;

public class ItemsPerStoreContainer {
    private ItemsDetailsContainer itemsDetailsContainer = new ItemsDetailsContainer();
    private float deliveryPrice;
    private float distanceFromClient;
    private float itemsPrice;

    public void setItemsPrice(float itemsPrice) {
        this.itemsPrice = itemsPrice;
    }

    public void updateItemsPrice(float itemsPrice) {
        this.itemsPrice += itemsPrice;
    }

    public float getDeliveryPrice() {
        return this.deliveryPrice;
    }

    public void setDeliveryPrice(float deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public float getDistanceFromClient() {
        return this.distanceFromClient;
    }

    public void setDistanceFromClient(float distanceFromClient) {
        this.distanceFromClient = distanceFromClient;
    }

    public void addItem(ItemDetailsContainer itemDetailsContainer) {
        itemsDetailsContainer.add(itemDetailsContainer);
    }

    public List<ItemDetailsContainer> getItems() {
        return this.itemsDetailsContainer.getIteratable();
    }

    public float getItemsPrice() {
        return this.itemsPrice;
    }

    public int getNumberOfUnits() {
        int numberOfUnits = 0;
        for (ItemDetailsContainer itemDetailsContainer : this.itemsDetailsContainer.getIteratable()) {
            if (itemDetailsContainer.getPurchaseCategory().equals(ePurchaseCategory.Quantity)) {
                numberOfUnits += itemDetailsContainer.getAmount();
            } else {
                ++numberOfUnits;
            }
        }

        return numberOfUnits;
    }

    public void addItems(ItemsPerStoreContainer dealsItems) {
        for(ItemDetailsContainer itemDetailsContainer : dealsItems.getItems()){
            getItems().add(itemDetailsContainer);
        }

        this.deliveryPrice = dealsItems.getDeliveryPrice();
        this.distanceFromClient = dealsItems.getDistanceFromClient();
        this.itemsPrice += dealsItems.getItemsPrice();
    }
}
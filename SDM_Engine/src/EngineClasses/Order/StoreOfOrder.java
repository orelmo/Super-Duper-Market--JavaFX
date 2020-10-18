package EngineClasses.Order;

import EngineClasses.Item.ePurchaseCategory;
import Exceptions.UnexistItemException;
import ItemsDetailsContainer.ItemDetailsContainer;
import OrderConteiner.ItemsPerStoreContainer;
import SDMSystem.SDMSystemItems;

import java.util.Collection;
import java.util.List;

public class StoreOfOrder {
    private AmountForItem amountForItem = new AmountForItem();
    private DealItems dealItems = new DealItems();
    private float deliveryPrice;
    private float distanceFromClient;
    private float itemsPrice;

    public float getItemsPrice() {
        return this.itemsPrice;
    }

    public void setItemsPrice(float itemsPrice) {
        this.itemsPrice = itemsPrice;
    }

    public float getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(float deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public float getDistanceFromClient() {
        return distanceFromClient;
    }

    public void setDistanceFromClient(float distanceFromClient) {
        this.distanceFromClient = distanceFromClient;
    }

    public void addItem(ItemDetailsContainer itemDetailsContainer) {
        this.amountForItem.add(itemDetailsContainer.getId(), itemDetailsContainer.getAmount());
    }

    public boolean isSellingItemInOrder(int itemId) {
        for (Integer itemIdInStore : this.amountForItem.getItemsId()) {
            if (itemIdInStore == itemId) {
                return true;
            }
        }

        return false;
    }

    public float getAmount(int itemId) {
        for (Integer itemIdInStore : this.amountForItem.getItemsId()) {
            if (itemIdInStore == itemId) {
                return this.amountForItem.get(itemIdInStore).floatValue();
            }
        }

        throw new UnexistItemException("Item with id " + itemId + " was not found");
    }

    public Collection<Integer> getItemsIds() {
        return this.amountForItem.getItemsId();
    }

    public int getNumberOfUnits(SDMSystemItems systemItems) {
        int numberOfUnits = 0;
        for (Integer itemId : this.amountForItem.getItemsId()) {
            if (systemItems.get(itemId).getPurchaseCategory().equals(ePurchaseCategory.Quantity)) {
                numberOfUnits += this.amountForItem.get(itemId).intValue();
            } else {
                ++numberOfUnits;
            }
        }
        numberOfUnits += this.dealItems.getNumberOfUnits();

        return numberOfUnits;
    }

    public AmountForItem getAmountForItem() {
        return this.amountForItem;
    }

    public void addDeal(ItemsPerStoreContainer itemsPerStoreContainer) {
        this.dealItems.addDeal(itemsPerStoreContainer);
    }

    public List<DealItem> getDealItems(){
        return this.dealItems.getDealItems();
    }
}
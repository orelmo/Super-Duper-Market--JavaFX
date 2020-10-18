package EngineClasses.Store;

import EngineClasses.Interfaces.Containable;

import java.util.HashMap;
import java.util.Map;

public class StoreItems implements Containable<Integer, Sell> {
    private final Map<Integer, Sell> itemIdToItemDetails;

    public StoreItems() {
        this.itemIdToItemDetails = new HashMap<>();
    }

    public boolean isExist(Integer itemId){
        return this.itemIdToItemDetails.containsKey(itemId);
    }

    @Override
    public Sell get(Integer id) {
        return this.itemIdToItemDetails.get(id);
    }

    @Override
    public void add(Integer id, Sell newObj) {
        this.itemIdToItemDetails.put(id, newObj);
    }

    @Override
    public Iterable<Sell> getIterable() {
        return this.itemIdToItemDetails.values();
    }

    public void deleteItem(int itemId) {
        this.itemIdToItemDetails.remove(itemId);
    }

    public void updateItemPrice(int itemId, int itemPrice) {
        this.itemIdToItemDetails.replace(itemId, new Sell(itemId, itemPrice));
    }

    public void updateItemSoldCounter(int itemId, float amount) {
        this.itemIdToItemDetails.get(itemId).increaseSold(amount);
    }
}
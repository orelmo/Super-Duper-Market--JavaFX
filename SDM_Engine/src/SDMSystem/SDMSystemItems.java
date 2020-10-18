package SDMSystem;

import EngineClasses.Interfaces.Containable;

import java.util.HashMap;
import java.util.Map;

public class SDMSystemItems implements Containable<Integer, SDMSystemItem> {
    private final Map<Integer, SDMSystemItem> idToItem;

    public SDMSystemItems() {
        this.idToItem = new HashMap<>();
    }

    public SDMSystemItem get(Integer itemId) {
        return this.idToItem.get(itemId);
    }

    public boolean isExist(Integer itemId) {
        return this.idToItem.containsKey(itemId);
    }

    public void add(Integer itemId, SDMSystemItem newItem) {
        this.idToItem.put(itemId, newItem);

    }

    public Iterable<SDMSystemItem> getIterable() {
        return this.idToItem.values();
    }

    public void updateSDMItemDetails(int itemId, int newPrice) {
        this.idToItem.get(itemId).updateAvgPriceAfterAdding(newPrice);
        this.idToItem.get(itemId).increaseSellersCounter();
    }
}
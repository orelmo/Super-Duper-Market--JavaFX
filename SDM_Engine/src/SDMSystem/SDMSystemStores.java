package SDMSystem;

import EngineClasses.Interfaces.Containable;
import EngineClasses.Store.Store;

import java.util.HashMap;
import java.util.Map;

public class SDMSystemStores implements Containable<Integer, Store> {
    private final Map<Integer, Store> storeIdToStore;

    public SDMSystemStores() {
        this.storeIdToStore = new HashMap<>();
    }

    public boolean isStoreSellingItem(int storeId, int itemId) {
        return this.get(storeId).isSellingItem(itemId);
    }

    @Override
    public boolean isExist(Integer id) {
        return this.storeIdToStore.containsKey(id);
    }

    @Override
    public Store get(Integer id) {
        return this.storeIdToStore.get(id);
    }

    @Override
    public void add(Integer id, Store newObj) {
        this.storeIdToStore.put(id, newObj);
    }

    @Override
    public Iterable<Store> getIterable() {
        return this.storeIdToStore.values();
    }

    public int getItemPriceFromStore(int storeId, int itemId) {
        return this.storeIdToStore.get(storeId).getItemPrice(itemId);
    }
}
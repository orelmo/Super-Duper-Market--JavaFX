package StoresDetailsConteiner;

import java.util.ArrayList;
import java.util.List;

public class StoresDetailsContainer {
    private List<StoreDetailsContainer> storesDetailsContainer = new ArrayList<>();

    public List<StoreDetailsContainer> getStoresDetailsContainer() {
        return this.storesDetailsContainer;
    }

    public void setStoresDetailsContainer(List<StoreDetailsContainer> storesDetailsContainer) {
        this.storesDetailsContainer = storesDetailsContainer;
    }

    public void add(StoreDetailsContainer storeDetailsContainer) {
        storesDetailsContainer.add((storeDetailsContainer));
    }

    public StoreDetailsContainer getStore(int storeId) {
        for (StoreDetailsContainer storeDetailsContainer : this.storesDetailsContainer) {
            if (storeDetailsContainer.getId() == storeId) {
                return storeDetailsContainer;
            }
        }

        return null;
    }
}
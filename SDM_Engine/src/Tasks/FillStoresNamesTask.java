package Tasks;

import Interfaces.StoresAdder;
import StoresDetailsConteiner.StoreDetailsContainer;
import StoresDetailsConteiner.StoresDetailsContainer;
import javafx.concurrent.Task;

public class FillStoresNamesTask extends Task<Boolean> {
    private StoresAdder uiAdapter;
    private StoresDetailsContainer storesDetailsContainer;

    public FillStoresNamesTask(StoresAdder uiAdapter, StoresDetailsContainer storesDetailsContainer) {
        this.uiAdapter = uiAdapter;
        this.storesDetailsContainer = storesDetailsContainer;
    }

    @Override
    protected Boolean call() throws Exception {
        if (storesDetailsContainer.getStoresDetailsContainer() != null) {
            for (StoreDetailsContainer store : storesDetailsContainer.getStoresDetailsContainer()) {
                this.uiAdapter.addStoreToShow(store.getName(), store.getId());
            }
        }

        return Boolean.TRUE;
    }
}